<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>部门管理</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/deptController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/deptController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>

<script type="text/javascript">
	var treeGrid;
	//parent.$.messager.progress('close');
	$(function() {
		treeGrid = $('#treeGrid')
				.treegrid(
						{
							url : '${pageContext.request.contextPath}/groupInfoController/findBy',
							fit : true,
							fitColumns : true,
							border : false,
							pagination : false,
							idField : 'id',
							treeField : 'groupName',
							parentField : 'parentId',
							nowrap : true,
							frozenColumns : [ [ {
								field : 'id',
								title : '编号',
								width : 150,
								hidden : true
							} ] ],
							columns : [ [
									{
										field : 'groupName',
										title : '群名称',
										width : 180,
										formatter : function(value, row, index) {
											return '<font style="font-style: normal;font-weight: bolder;">'
													+ value + '</font>';
										}
									},
									{
										field : 'groupId',
										title : '亲加云群编码',
										width : 100,
										formatter : function(value, row, index) {
											if(value!=null){
												return '<font style="font-style: normal;font-weight: bolder;">'
												+ value + '</font>';
											}else{
												return "";
											}
											
										}
									},

									{
										field : 'createTime',
										title : '创建时间',
										width : 140,
										formatter : function(value, row, index) {
											var unixTimestamp = new Date(value);
									return '<font style="font-style: normal;font-weight: bolder;">'+(unixTimestamp.toLocaleString())+'</font>';
										}

									},
									{
										field : 'lastUpdateTime',
										title : '修改时间',
										width : 140,
										formatter : function(value, row, index) {
											if (value != null) {
												var unixTimestamp = new Date(
														value);
												return '<font style="font-style: normal;font-weight: bolder;">'+(unixTimestamp.toLocaleString())+'</font>';
											} else {
												return "";
											}
										}
									},
									{
										field : 'groupInfo',
										title : '群信息',
										width : 185,
									    formatter : function(value, row, index) {
									    	if(value!=null){
										     	return "<font style='word-wrap:break-word;white-space:normal;font-style: normal;font-weight: bolder;'>"+value+"</font>";
											    }
												else{
													return "";
												}
			                             }
									},	{
										field : 'users',
										title : '群成员',
										width : 185,
									    formatter : function(value, row, index) {
									    	if(value!=null){
										     	return "<font style='word-wrap:break-word;white-space:normal;font-style: normal;font-weight: bolder;'>"+value+"</font>";
											    }
												else{
													return "";
												}
			                             }
									},{
										field : 'createusername',
										title : '群创建者',
										width : 185,
									    formatter : function(value, row, index) {
									    	if(value!=null){
										     	return "<font style='word-wrap:break-word;white-space:normal;font-style: normal;font-weight: bolder;'>"+value+"</font>";
											    }
												else{
													return "";
												}
			                             }
									},
									
									{
										field : 'action',
										title : '操作',
										width : 120,
										formatter : function(value, row, index) {
											var str = '';
											
												str += $.formatString(
																'<img onclick="editFun(\'{0}\',\'{1}\');" src="{2}" title="编辑"/>',
																row.id,
																row.projId,
																'${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
											
											str += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
										
												str += $.formatString(
																'<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>',
																row.id,
																'${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');

												str += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
												str += $.formatString('<img onclick="adduserFun(\'{0}\');" src="{1}" title="添加人员"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/key.png');	
												
											return str;
										}
									} ] ],
							toolbar : '#toolbar',
							onLoadSuccess : function() {

								parent.$.messager.progress('close');

								$(this).treegrid('tooltip');
							},
							onRowContextMenu : function(e, rowIndex, rowData) {
								e.preventDefault();
								$(this).treegrid('unselectAll');
								$(this).treegrid('select', row.id);
								$('#menu').menu('show', {
									left : e.pageX,
									top : e.pageY
								});
							}
						});
	});

	function formatterdate(val, row) {
		var date = new Date(val);
		return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'
				+ date.getDate();
	}

	function deleteFun(id) {

		if (id == undefined) {//点击右键菜单才会触发这个
			treeGrid.treegrid('select', id);
		}

		var node = treeGrid.treegrid('getSelected');
		if (node) {

			parent.$.messager
					.confirm(
							'询问',
							'您是否要删除当前群组？',
							function(b) {
								if (b) {
									parent.$.messager.progress({
										title : '提示',
										text : '数据处理中，请稍后....'
									});
									$
											.post(
													'${pageContext.request.contextPath}/groupInfoController/deleteGroupInfo',
													{
														id : id
													},
													function(result) {
														if (result.success) {
															parent.$.messager
																	.alert(
																			'提示',
																			result.msg,
																			'info');
															treeGrid
																	.treegrid('reload');
															//parent.layout_west_tree.tree('reload');
														} else {
															parent.$.messager
																	.alert(
																			'提示',
																			result.msg,
																			'info');
														}
														parent.$.messager
																.progress('close');
													}, 'JSON');
								}
							});
		}
	}
	
	function adduserFun(id) {
	
		parent.$.modalDialog({
			title : '添加人员',
			width :680,
			height : 800,
			href : '${pageContext.request.contextPath}/groupInfoController/grantUserPage?ids=' + id,
			buttons : [ {
				text : '添加人员',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = treeGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}
	

	function editFun(id, projId) {
		if (id != undefined) {
			treeGrid.treegrid('select', id);
		}
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			parent.$
					.modalDialog({
						title : '编辑部门',
						width : 300,
						height : 250,
						href : '${pageContext.request.contextPath}/groupInfoController/editPage?id='
								+ node.id,
						buttons : [ {
							text : '编辑',
							handler : function() {
								parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
								var f = parent.$.modalDialog.handler
										.find('#form');
								f.submit();
							}
						} ]
					});
		}
	}

	function addFun(){
		parent.$.modalDialog({
			title : '添加群组',
			width : 500,
			height : 310,
			href : '${pageContext.request.contextPath}/groupInfoController/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function cleanFun() {
		$('#searchForm input').val('');
		treeGrid.treegrid('load', {});
		treeGrid.treegrid('unselectAll');
	}

	function redo() {
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			treeGrid.treegrid('expandAll', node.id);
		} else {
			treeGrid.treegrid('expandAll');
		}
	}

	function undo() {
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			treeGrid.treegrid('collapseAll', node.id);
		} else {
			treeGrid.treegrid('collapseAll');
		}
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">

		<div data-options="region:'center',border:false">
			<table id="treeGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">

		<div style="height: 2px;"></div>
 <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
		<a onclick="redo();" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'resultset_next'">展开</a> <a
			onclick="undo();" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'resultset_previous'">折叠</a> <a
			onclick="treeGrid.treegrid('reload');" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'transmit'">刷新</a>
	</div>

	<div id="menu" class="easyui-menu" style="width: 120px; display: none;">
     <!--  <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>   -->
	</div>
</body>
</html>