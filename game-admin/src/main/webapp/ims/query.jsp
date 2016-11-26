<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>流程管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/queryConditionController/conditionManager')}">
	<script type="text/javascript">
		$.canSearchCondition = true;
	</script>
</c:if>

<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/queryController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			rownumbers : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'createdatetime',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 100,
				hidden : true
			} ] ],
			columns : [ [ {
				field : 'name',
				title : '名称',
				width : 70
			},{
				field : 'groupName',
				title : '分组名称',
				width : 70
			},{
				field : 'projectName',
				title : '项目名称',
				width : 150
			},{
				field : 'processId',
				title : '流程ID',
				width : 150,
				hidden : true
			},{
				field : 'processName',
				title : '流程名称',
				width : 150
			},{
				field : 'taskId',
				title : '任务节点ID',
				width : 150,
				hidden : true
			},{
				field : 'taskName',
				title : '任务节点名称',
				width : 150
			},{
				field : 'actionId',
				title : '动作ID',
				width : 150,
				hidden : true
			},{
				field : 'actionName',
				title : '动作名称',
				width : 150
			},{
				field : 'formStat',
				title : '表单状态',
				width : 150,
				formatter:function(value,row,index){  
                     if(value==0){
                  	   return '未执行';
                     }else if(value==1){
                  	   return '已执行';
                     }
                }
			},{
				field : 'action',
				title : '操作',
				width : 100,
				formatter : function(value, row, index) {
					var str = '';
					str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					str += '&nbsp;&nbsp;&nbsp;';
					str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					str += '&nbsp;&nbsp;&nbsp;';		
					if($.canSearchCondition){
						   str += $.formatString('<img onclick="searchConditionFun(\'{0}\');" src="{1}" title="查询过滤字段"/>', row.id,'${pageContext.request.contextPath}/style/images/extjs_icons/zoom/zoom.png');
				    }
					str += '&nbsp;&nbsp;&nbsp;';
					str += $.formatString('<img onclick="searchResultFun(\'{0}\');" src="{1}" title="查询返回结果"/>', row.id,'${pageContext.request.contextPath}/style/images/extjs_icons/zoom/zoom.png');
					str += '&nbsp;&nbsp;&nbsp;';
					str += $.formatString('<img onclick="searchActionFun(\'{0}\');" src="{1}" title="查询动作"/>', row.id,'${pageContext.request.contextPath}/style/images/extjs_icons/zoom/zoom.png');
					
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');

				//$(this).datagrid('tooltip');
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

	
function searchConditionFun(id){
	if (id == undefined) {
		var rows = dataGrid.datagrid('getSelections');
		id = rows[0].id;
	} else {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
	}
	parent.$.modalDialog({
		title : '过滤条件信息',
		width : 710,
		height :400,
		draggable : true, //拖拽操作
		closable : true,
		href : '${pageContext.request.contextPath}/queryConditionController/conditionManager?queryId='+id
	
	});
	
}

//  查询返回结果
function searchResultFun(id){
	if (id == undefined) {
		var rows = dataGrid.datagrid('getSelections');
		id = rows[0].id;
	} else {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
	}
	parent.$.modalDialog({
		title : '查询返回结果信息',
		width : 810,
		height :500,
		draggable : true, //拖拽操作
		closable : true,
		href : '${pageContext.request.contextPath}/queryResultController/manager?queryId='+id
	});
}

//查询自定义动作
function searchActionFun(id){
	if (id == undefined) {
		var rows = dataGrid.datagrid('getSelections');
		id = rows[0].id;
	} else {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
	}
	parent.$.modalDialog({
		title : '查询返回动作信息',
		width : 810,
		height :500,
		draggable : true, //拖拽操作
		closable : true,
		href : '${pageContext.request.contextPath}/queryActionController/manager?queryId='+id
	});
}

	function addFun() {
		parent.$.modalDialog({
			title : '添加查询',
			width : 810,
			height : 500,
			href : '${pageContext.request.contextPath}/queryController/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}
	
	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑查询',
			width : 700,
			height : 530,
			href : '${pageContext.request.contextPath}/queryController/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}
	
	function deleteFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前查询？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
				if (currentUserId != id) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/queryController/deleteQuery', {
						id : id
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							dataGrid.datagrid('reload');
						}else {
							parent.$.messager.alert('错误', result.msg, 'error');
						}
						parent.$.messager.progress('close');
					}, 'JSON');
				} 
			}
		});
	}
	
	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		
		$('#searchForm input').val('');
	  
	/*     var url = '${pageContext.request.contextPath}/ProjectController/findAll';
	    $('#projectId').combobox('reload',url);
	  
	    $('#projectId').combobox({  
		    valueField:'id',  
		    textField:'projName'  
		})
		
		$('#projectId').combobox('setValue','请选择项目');
	    $('#processId').combobox('setValue','请选择流程');
	    $('#taskId').combobox('setValue','请选择节点');
	    $('#actionId').combobox('setValue','请选择节点动作');  */ 

		dataGrid.datagrid('load', {});
	
		
		

	}
	

	

</script>
</head>
<body>
	<div  class="easyui-layout" data-options="fit : true,border : false">
		<div   data-options="region:'north',title:'查询条件',border:false" style="height: 170px; overflow: hidden;">
			<form id="searchForm">
			
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
					    <th>名称</th>
					    <td><input  name="name" placeholder="可以查询名称" class="easyui-validatebox" style="width:279px;"/></td>
					    <th>分组名称</th>
					    <td>
					       <input  name="groupName" placeholder="可以查询分组名称" class="easyui-validatebox" style="width:279px;"/>
					    </td>
					</tr>
					<tr>
			            <th>项目名称</th>
			            <td>
			              <select id="projectId" name="projectId" class="easyui-combobox"  data-options="width:283,height:29,editable:false, onSelect:function(record){
			                  var url = '${pageContext.request.contextPath}/tImsProcessController/findProcess?projId='+record.value;    
                              $('#processId').combobox('reload', url); 
                              $('#processId').combobox('setValue','请选择流程');
			              }">
			                <option value="">请选择项目</option>
			                   <c:forEach items="${listproject}" var="de">
						        <option value="${de.id}"  >${de.projName}</option>
					           </c:forEach>
			              </select>
			            </td>
			    	    <th>所属项目流程</th>
					    <td>
					      <select id="processId" name="processId" class="easyui-combobox"  data-options="width:283,height:29,editable:false,panelHeight:'auto',valueField:'id',textField:'procName', onSelect:function(record){    
                              var url = '${pageContext.request.contextPath}/tImsTasksController/findAll?procId='+record.id;    
                              $('#taskId').combobox('reload', url); 
                              $('#taskId').combobox('setValue','请选择节点');
                         } ">
					        <option value="">请选择流程</option>
					      </select>
					    </td>
			        </tr>
			          <tr>
			       <th>任务节点</th>
			       <td>
			          <select id="taskId" name="taskId" class="easyui-combobox"  data-options="width:283,height:29,editable:false,panelHeight:'auto',valueField:'id',textField:'name',  onSelect:function(record){    
                            var url = '${pageContext.request.contextPath}/queryController/findAction?taskId='+record.id;    
                            $('#actionId').combobox('reload', url); 
                            $('#actionId').combobox('setValue','请选择节点动作');

                         }  ">
			           <option value="">请选择节点</option>
			          </select>
			       </td>
			    	<th>节点动作</th>
					<td>
					  <select id="actionId" name="actionId" class="easyui-combobox"  data-options="width:283,height:29,editable:false,panelHeight:'auto',valueField:'id',textField:'name' " >
					    <option value="">请选择节点动作</option>
					  </select>
					</td>
			    </tr>
				 
				</table>
			</form>
		</div>
		<div   data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
	    <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>   
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	</div>

	
</body>
</html>