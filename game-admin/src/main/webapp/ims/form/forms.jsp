<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>表单管理</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/formsController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/formsController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>

<c:if
	test="${fn:contains(sessionInfo.resourceList, '/fieldsController/formFieldManager')}">
	<script type="text/javascript">
		$.canSearchFormField = true;
	</script>
</c:if>
<script type="text/javascript">
	var formDataGrid;
	//parent.$.messager.progress('close');
	$(function() {
	
		formDataGrid = $('#formDataGrid').datagrid({
			url : '${pageContext.request.contextPath}/formsController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			//sortName : 'deptName',
			//sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect:true,
			nowrap : false,
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				checkbox : true
			} ] ],
			columns : [ [{
				field : 'name',
				title : '表单名称',
				width : 130
			}, {
				field : 'projId',
				title : '项目ID',
				width : 150,
				sortable : true,
				hidden:true
			
			}, {
				field : 'procId',
				title : '流程ID',
				width : 150,
				sortable : true,
				hidden:true
			
			},{
				field : 'createTime',
				title : '创建时间',
				width : 150,
		        formatter:function(value,row,index){  
                      var unixTimestamp = new Date(value);  
                      return unixTimestamp.toLocaleString();  
                      }  
				
			}, {
				field : 'lastUpdateTime',
				title : '修改时间',
				width : 150,
		        formatter:function(value,row,index){  
		        	if(value!=null){
                      var unixTimestamp = new Date(value);  
                      return unixTimestamp.toLocaleString();  
                      } else{
                    	  return "";
                      }
		        	}
				
			},{
				field : 'projName',
				title : '所属项目',
				width : 130
			},{
				field : 'procName',
				title : '所属流程',
				width : 130
			},{
				field : 'taskName',
				title : '所属任务节点',
				width : 130
			},{
				field : 'action',
				title : '操作',
				width : 130,
				formatter : function(value, row, index) {
					var str = '';
			/* 		if ($.canEdit) {
						str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					} */
					str += '&nbsp;&nbsp;&nbsp;';
					
					if ($.canDelete) {
						str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					}
					str += '&nbsp;&nbsp;&nbsp;';
					
				    if($.canSearchFormField){
					str += $.formatString('<img onclick="searchFieldFun(\'{0}\',\'{1}\',\'{2}\');" src="{3}" title="查看所属字段"/>', row.id,row.projId,row.procId, '${pageContext.request.contextPath}/style/images/extjs_icons/zoom/zoom.png');
				    }
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
			
				
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');
			},
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll').datagrid('uncheckAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
	});



	function deleteFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = formDataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			formDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前表单？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
				if (currentUserId != id) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/formsController/delete', {
						id : id
					}, function(result) {
						if (result.success) {
							   parent.$.messager.show({
									title : '提示',
									msg : result.msg
								});
							   formDataGrid.datagrid('reload');
						}else{
							   parent.$.messager.show({
									title : '提示',
									msg : result.msg
								});
						}
						parent.$.messager.progress('close');
					}, 'JSON');
				} else {
					parent.$.messager.show({
						title : '提示',
						msg : '不可以删除自己！'
					});
				}
			}
		});
	}



	function editFun(id) {
		if (id == undefined) {
			var rows = formDataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			formDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑表单',
			width : 400,
			height : 180,
			href : '${pageContext.request.contextPath}/formsController/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
				    parent.$.modalDialog.openner_dataGrid = formDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	
	function searchFieldFun(formId,projId,procId){
		if (formId == undefined) {
			var rows = formDataGrid.datagrid('getSelections');
			formId = rows[0].id;
		} else {
			formDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '表单字段信息',
			width : 910,
			height :400,
			draggable : true, //拖拽操作
			closable : true,
			href : '${pageContext.request.contextPath}/fieldsController/formFieldManager?formId='+formId+'&projId='+projId+'&procId='+procId+''
		
		});
		
	}





	function searchFun() {
		formDataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		formDataGrid.datagrid('load', {});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false"
			style="height: 125px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed"
					style="display: none;">
					<tr>
						<th>表单名</th>
						<td><input name="name" placeholder="输入表单名称"
							class="span2" /></td>
						 <th>所属项目</th>
					   <td>
					 <select id="projId" name="projId" class="easyui-combobox" data-options="width:170,height:29,editable:false,panelHeight:120"  >
					
						<c:forEach items="${projectList}" var="de">
								<option value="${de.id}" >${de.projName}</option>
							</c:forEach>
					</select>
					   </td>
					</tr>
					<tr>
					
					   	 <th>所属流程</th>
					   <td>
					 <select id="procId" name="procId" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:120"  >
					
						<c:forEach items="${processList}" var="de">
								<option value="${de.id}" >${de.procName}</option>
							</c:forEach>
					</select>
					   </td>
					   	 <th>所属任务节点</th>
					   <td>
					 <select id="taskId" name="taskId" class="easyui-combobox" data-options="width:170,height:29,editable:false,panelHeight:120"  >
					
						<c:forEach items="${taskList}" var="de">
								<option value="${de.id}" >${de.name}</option>
							</c:forEach>
					</select>
					   </td>
					</tr>
						
	            <!--   	<tr>
						<th>创建时间</th>
						<td><input class="span2" name="createTimeStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />至<input class="span2" name="createTimeEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" /></td>
					</tr>
					<tr>
						<th>最后修改时间</th>
						<td><input class="span2" name="lastUpdateTimeStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />至<input class="span2" name="lastUpdateTimeEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" /></td>
					</tr> -->

				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="formDataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
	

		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a><a
			href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'brick_delete',plain:true"
			onclick="cleanFun();">清空条件</a>
	</div>

	<div id="menu" class="easyui-menu" style="width: 120px; display: none;">
		
		<c:if
			test="${fn:contains(sessionInfo.resourceList, '/formsController/delete')}">
			<div onclick="deleteFun();" data-options="iconCls:'pencil_delete'">删除</div>
		</c:if>
		<c:if
			test="${fn:contains(sessionInfo.resourceList, '/formsController/editPage')}">
			<div onclick="editFun();" data-options="iconCls:'pencil'">编辑</div>
		</c:if>
		<c:if
	        test="${fn:contains(sessionInfo.resourceList, '/fieldsController/formFieldManager')}">
		<div onclick="searchFieldFun();" data-options="iconCls:'pencil'">查看所属字段</div>
        </c:if>
	</div>
</body>
</html>