<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>流程管理</title>
<jsp:include page="../inc.jsp"></jsp:include>


<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/tImsProcessController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			rownumbers : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'name',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 244,
				checkbox : true,
				hidden : true
			}, {
				field : 'projId',
				title : '项目ID',
				width : 143,
				hidden : true
			}  ] ],
			columns : [ [ {
				field : 'procName',
				title : '流程名称',
				width : 150
			},{
				field : 'describes',
				title : '描述',
				width : 150
			}, {
				field : 'projName',
				title : '所属项目',
				width : 160
			},{
				field : 'userName',
				title : '所属用户',
				width : 160
			},{
				field : 'isdefine',
				title : '是否有效',
				width : 110,
				 formatter:function(value,row,index){  
                       if(value==0){
                    	   return '无效';
                       }else if(value==1){
                    	   return '有效';
                       }
                  } 
			},{
				field : 'startDate',
				title : '开始时间',
				width : 150,
			      formatter:function(value,row,index){  
                      var unixTimestamp = new Date(value);  
                      return unixTimestamp.toLocaleString();  
                      } 
			},{
				field : 'endDate',
				title : '结束时间',
				width : 150,
				 formatter:function(value,row,index){  
                     var unixTimestamp = new Date(value);  
                     return unixTimestamp.toLocaleString();  
                     } 
			},{
				field : 'state',
				title : '流程状态',
				width : 150,
				 formatter:function(value,row,index){  
                     if(value==0){
                  	   return '未定义';
                     }else if(value==1){
                  	   return '未开始';
                     }else if(value==2){
                       return '赛中进行';
                     }else if(value==3){
                       return '已结束'; 
                     }
                }
			},{
				field : 'imageUrl',
				title : '图片地址',
				width : 150
			},
			{
				field : 'action',
				title : '操作',
				width : 150,
				formatter : function(value, row, index) {
					var str = '';
						str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					str += '&nbsp;';
					str += $.formatString('<img onclick="findInstanceFun(\'{0}\',\'{1}\');" src="{2}" title="查看流程实例"/>', row.id,row.procName, '${pageContext.request.contextPath}/style/images/extjs_icons/zoom/zoom.png');
					str += '&nbsp;';
							str += $.formatString('<img onclick="grantFun(\'{0}\');" src="{1}" title="添加节点"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil_add.png');
				    str += '&nbsp;';
							str += $.formatString('<img onclick="adduserFun(\'{0}\',\'{1}\');" src="{2}" title="用户授权"/>', row.id,row.projId, '${pageContext.request.contextPath}/style/images/extjs_icons/key.png');
					str += '&nbsp;';
							str += $.formatString('<img onclick="upLoadFun(\'{0}\');" src="{1}" title="上传图片"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/image_add.png');
							
				
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
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前任务节点？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
				if (currentUserId != id) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/tImsProcessController/deletePro', {
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



	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑任务节点',
			width : 700,
			height : 300,
			href : '${pageContext.request.contextPath}/tImsProcessController/editProcess?id=' + id,
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

	
	function findInstanceFun(id,procName) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title :"<font color='black'>["+procName+ ']</font>下的流程实例',
			width : 700,
			height : 300,
			href : '${pageContext.request.contextPath}/procInstanceController/manager?id=' + id
		 
		});
	}
	
	function upLoadFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '上传图片',
			width : 700,
			height : 300,
			href : '${pageContext.request.contextPath}/tImsProcessController/upLoadImage?id=' + id,
			buttons : [ {
				text : '上传',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加任务节点',
			width : 700,
			height : 500,
			href : '${pageContext.request.contextPath}/tImsProcessController/addPage',
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




	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
	
	  
		
		dataGrid.datagrid('load', {});
	}
	
	
	function grantFun(id) {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		parent.$.modalDialog({
			title : '添加任务节点',
			width : 700,
			height : 200,
			href : '${pageContext.request.contextPath}/tImsTasksController/addPage?id=' + id,
			buttons : [ {
				text : '添加节点',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}
	
	function adduserFun(id,projId) {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		parent.$.modalDialog({
			title : '用户授权',
			width : 680,
			height : 800,
			href : '${pageContext.request.contextPath}/tImsProcessController/grantUserPage?ids=' + id + '&projId='+projId,
			buttons : [ {
				text : '授权',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}
	

</script>
</head>
<body>
	<div  class="easyui-layout" data-options="fit : true,border : false">
		<div   data-options="region:'north',title:'查询条件',border:false" style="height: 120px; overflow: hidden;">
			<form id="searchForm">
			
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
					    <th>项目名称</th>
					    <td>
					        <select id="projId" name="projId" class="easyui-combobox"  data-options="width:283,height:29,editable:false, onSelect:function(record){
			                  var url = '${pageContext.request.contextPath}/tImsProcessController/findProcess?projId='+record.value;    
                              $('#id').combobox('reload', url); 
                              $('#id').combobox('setValue','请选择流程');
			              }">
			                <option value="">请选择项目</option>
			                   <c:forEach items="${listproject}" var="de">
						        <option value="${de.id}"  >${de.projName}</option>
					           </c:forEach>
			              </select>
					    </td>
					  <th>所属项目流程</th>
					    <td>
					      <select id="id" name="id" class="easyui-combobox"  data-options="width:283,height:29,editable:false,panelHeight:'auto',valueField:'id',textField:'procName' ">
					       <option value="">请选择流程</option>
					      </select>
					    </td>
					</tr>
					<tr>
					    <th>流程描述</th>
					    <td><input name="describes" placeholder="可以查询描述" class="span2" style="width:279px;"/></td>
					    <th>&nbsp;</th>
					    <td>&nbsp;</td>
					</tr>
				</table>
			</form>
		</div>
		<div   data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		
		<!-- 	<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>   -->
		
		<!--<c:if test="${fn:contains(sessionInfo.resourceList, '/deptLevelController/grantPage')}">
			<a onclick="batchGrantFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'tux'">批量授权</a>
		</c:if>-->
	
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	</div>

	
</body>
</html>