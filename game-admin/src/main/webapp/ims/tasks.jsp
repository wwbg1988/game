<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>部门等级管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/formsController/addPage')}">
	<script type="text/javascript">
		$.canAddForm = true;
	</script>
</c:if>

<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/tImsTasksController/dataGrid',
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
				width : 44,
				hidden:true
			}, {
				field : 'name',
				title : '任务节点名称',
				width : 140
			} ] ],
			columns : [ [ {
				field : 'projId',
				title : '项目ID',
				width : 150,
				hidden:true
			},{
				field : 'projName',
				title : '所属项目',
				width : 150
			},{
				field : 'procId',
				title : '流程ID',
				width : 150,
				hidden:true
			
			},{
				field : 'procName',
				title : '所属流程',
				width : 150
			},{
				field : 'formId',
				title : '表单ID',
				width : 150,
				hidden:true
			},{
				field : 'formName',
				title : '节点表单名称',
				width : 150
			},{
				field : 'type',
				title : '类型',
				width : 110,
				 formatter:function(value,row,index){  
                     if(value==1){
                  	   return '开始节点';
                     }else if(value==2){
                  	   return '任务节点';
                     }else if(value==3){
                       return '结束';
                     }
                }
			},{
				field : 'state',
				title : '状态',
				width : 100,
				 formatter:function(value,row,index){  
                     if(value==0){
                  	   return '未激活';
                     }else if(value==1){
                  	   return '办理中(激活)';
                     }else if(value==2){
                    	 return '已办';
                     }else if(value==3){
                    	 return '否决';
                     }else if(value==4){
                    	 return '退回';
                     }
                },
                hidden : true
			},{
				field : 'preTaskId',
				title : '前驱节点ID',
				width : 170,
				hidden:true
			},{
				field : 'countersign',
				title : '是否会签',
				width : 110,
				 formatter:function(value,row,index){  
                     if(value==0){
                  	   return '否';
                     }else if(value==1){
                  	   return '是';
                     }
                }
			},
			{
				field : 'action',
				title : '操作',
				width : 170,
				formatter : function(value, row, index) {
					var str = '';
					
						str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					
				
					    str += '&nbsp;&nbsp;';
				
						str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					
						str += '&nbsp;&nbsp;';
						
						if($.canAddForm){
						str += $.formatString('<img onclick="addFormFun(\'{0}\',\'{1}\',\'{2}\',\'{3}\');" src="{4}" title="添加表单"/>', row.id,row.projId,row.procId,row.formId,'${pageContext.request.contextPath}/style/images/extjs_icons/table/table_add.png');
						}
						str += '&nbsp;&nbsp;';
						str += $.formatString('<img onclick="addActionFun(\'{0}\',\'{1}\',\'{2}\');" src="{3}" title="添加动作"/>', row.id,row.projId,row.procId, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil_add.png');
						
						str += '&nbsp;&nbsp;';
						str += $.formatString('<img onclick="grantRoleFun(\'{0}\',\'{1}\',\'{2}\');" src="{3}" title="任务节点角色授权"/>', row.id,row.projId,row.procId, '${pageContext.request.contextPath}/style/images/extjs_icons/key.png');
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

	function grantRoleFun(id) {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		parent.$.modalDialog({
			title : '任务节点角色授权',
			width : 500,
			height : 300,
			href : '${pageContext.request.contextPath}/tImsTasksController/grantRolePage?ids=' + id,
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
	
	

	function addFormFun(taskId,projId,procId,formId) {
	
	   if(formId=='null'||formId==''){
	    	 parent.$.modalDialog({
				title : '添加表单',
				width : 350,
				height : 180,
				href : '${pageContext.request.contextPath}/formsController/addPage?taskId='+taskId+'&projId='+projId+'&procId='+procId+'',
				buttons : [ {
					text : '添加',
					handler : function() {
						//parent.$.modalDialog.openner_dataGrid = $('#formDataGrid');//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
						parent.$.modalDialog.openner_dataGrid = dataGrid;
						var f = parent.$.modalDialog.handler.find('#form');
						f.submit();
					}
				} ]
			 });
	     }else{
	    	 
	    	 parent.$.messager.alert('提示',"该任务节点已存在表单，不可重复添加哦!", 'info');
		}
	}
	function addActionFun(taskId,projId,procId){
		parent.$.modalDialog({
			title : '添加动作',
			width : 390,
			height : 270,
			href : '${pageContext.request.contextPath}/actionsController/jumpAddAction?taskId='+taskId+'&projId='+projId+'&procId='+procId+'',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = $('#formDataGrid');//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
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
		parent.$.messager.confirm('询问', '您是否要删除当前任务节点？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
				if (currentUserId != id) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/tImsTasksController/deleteTask', {
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
			href : '${pageContext.request.contextPath}/tImsTasksController/editTasks?id=' + id,
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

	function addFun() {
		parent.$.modalDialog({
			title : '添加任务节点',
			width : 700,
			height : 500,
			href : '${pageContext.request.contextPath}/tImsTasksController/addPage',
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
		$('#type').attr('value','');
		$('#state').attr('value','');
		$('#countersign').attr('value','');
		dataGrid.datagrid('load', {});
	}
</script>
</head>
<body>
	<div  class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 140px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
				
				    <tr>
					   <th>任务节点名称</th>
						<td><input name="name" placeholder="可以查询任务节点名称" class="easyui-validatebox" style="width: 195px;"/></td>
				       <th>任务节点类型</th>
					   <td>
					   <select id="type" name="type" class="easyui-combobox" style="width:200px;height: 26px;">
					      <option value="">请选择节点类型</option>
					      <option value="1">1_开始节点</option>
					      <option value="2">2_任务节点</option>
					      <option value="3">3_结束</option>
					   </select>
					   </td>
				    </tr>
				    <tr>
				        <th>节点状态</th>
				        <td>
				        <select id="state" name="state" class="easyui-combobox" style="width:200px;height: 26px;"> 
				        <option value="">请选择节点状态</option>
				        <option value="0">0_未激活</option>
				        <option value="1">1_办理中(激活)</option>
				        <option value="2">2_已办</option>
				        <option value="3">3_否决</option>
				        <option value="4">4_退回</option>
				        </select>
				        </td>
				        <th>表单类型</th>
				        <td>
				        <select id="countersign" name="countersign" class="easyui-combobox" style="width:200px;height: 26px;">
				        <option value="">请选择表单类型</option>
				        <option value="0">0_不会签</option>
				        <option value="1">1_会签</option>
				        <option value="2">2_多人重复提交表单</option>
				        </select>
				        </td>
				    </tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
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