<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>评分预警阀值</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/sensitiveController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/sensitiveController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	parent.$.messager.progress('close');
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
						url : '${pageContext.request.contextPath}/configController/QueryConfigScoreValveConf',
						fit : true,
						fitColumns : true,
						border : false,
						pagination : true,
						idField : 'id',
						rownumbers : true,
						singleSelect : true,
						pageSize : 10,
						pageList : [ 10, 20, 30, 40, 50 ],
						checkOnSelect : false,
						selectOnCheck : false,
						nowrap : false,
						frozenColumns : [ [ {
							field : 'id',
							title : '编号',
							width : 150,
							hidden : true
						} ] ],
						columns : [ [
								{
									field : 'cafetoriumName',
									title : '食堂名称 ',
									width : 130
								},
								{
									field : 'valveCount',
									title : '条数阀值 ',
									width : 130
								},
								{
									field : 'valvePercent',
									title : '百分比阀值',
									width : 130,
								},
								{
									field : 'level',
									title : '阀值等级',
									width : 130,
								},
							    {
									field : 'createTime',
									title : '创建时间',
									width : 150,
									formatter : function(value, row, index) {
										if (value != null) {
											var unixTimestamp = new Date(value);
											return unixTimestamp.toLocaleString();
										} else {
											return "";
										}
									}

								},
								{
									field : 'lastUpdateTime',
									title : '修改时间',
									width : 150,
									formatter : function(value, row, index) {
										if (value != null) {
											var unixTimestamp = new Date(
													value);
											return unixTimestamp
													.toLocaleString();
										} else {
											return "";
										}
									}

								},
								{
									field : 'action',
									title : '操作',
									width : 130,
									formatter : function(value, row, index) {
										var str = '&nbsp;&nbsp;';
										if ($.canEdit) {
											str += $.formatString(
															'<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>',
															row.id,
															'${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
										}
										str += '&nbsp;&nbsp;&nbsp;';
										if ($.canDelete) {
											str += $.formatString(
															'<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>',
															row.id,
															'${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
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
	
	
	//删除敏感词预警阀值
	function deleteFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm(
					'询问',
					'您是否要删除当前评分项预警阀值？',
					function(b) {
						if (b) {
							var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
							if (currentUserId != id) {
								parent.$.messager.progress({
									title : '提示',
									text : '数据处理中，请稍后....'
								});
								$.post(
									'${pageContext.request.contextPath}/configController/configScoreValveConfDelete',
									{id : id},
									function(result) {
										if (result.success) {
											parent.$.messager.show({
												title : '提示',
												msg : result.msg
											});
											dataGrid.datagrid('reload');
										}
										parent.$.messager.progress('close');
									}, 'JSON');
							}
						}
					});
	}
	
	//修改敏感词预警阀值
	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
					title : '编辑评分预警',
					width : 400,
					height : 250,
					href : '${pageContext.request.contextPath}/configController/configScoreValveConfUpdatePage?id='+ id,
					buttons : [ {
						text : '提交',
						handler : function() {
							parent.$.modalDialog.openner_dataGrid = dataGrid; //因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
							var f = parent.$.modalDialog.handler.find('#form');
							f.submit();
						}
					} ]
				});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
	</div>
</body>
</html>