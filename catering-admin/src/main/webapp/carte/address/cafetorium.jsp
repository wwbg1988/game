<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>食堂管理</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/cafrtoriumController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/cafrtoriumController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/carteTypeController/addPage')}">
	<script type="text/javascript">
		$.canAddCarteType = true;
	</script>
</c:if>

<script type="text/javascript">
	var dataGrid;
	parent.$.messager.progress('close');
	$(function() {
		dataGrid = $('#dataGrid')
				.propertygrid(
						{
							url : '${pageContext.request.contextPath}/cafrtoriumController/dataGrid',
							fit : true,
							fitColumns : true,
							border : true,
							pagination : true,
							idField : 'id',
							pageSize : 30,
							groupField : "addressName",
							showGroup : true,
							pageList : [ 10, 20, 30, 40, 50 ],
							//sortName : 'deptName',
							//sortOrder : 'asc',
							checkOnSelect : false,
							selectOnCheck : false,
							singleSelect : true,
							nowrap : false,
							frozenColumns : [ [ {
								field : 'id',
								title : '编号',
								width : 150,
								hidden : true
							} ] ],
							columns : [ [
									{
										field : 'cafeName',
										title : '食堂名称',
										width : 160,
										formatter : function(value, row, index) {
											return '<font style="font-style: normal;font-weight: bolder;">'
													+ value + '</font>';
										}
									},
									{
										field : 'cafeCode',
										title : '食堂编码',
										width : 100,
										formatter : function(value, row, index) {
											return '<font style="font-style: normal;font-weight: bolder;">'
													+ value + '</font>';
										}
									},
									{
										field : 'mobileNo',
										title : '食堂联系电话',
										width : 130,
										formatter : function(value, row, index) {
											return '<font style="font-style: normal;font-weight: bolder;word-wrap:break-word;white-space:normal;">'
													+ value + '</font>';
										}
									},
									{
										field : 'address',
										title : '食堂地址',
										width : 120,
										formatter : function(value, row, index) {
											return '<font style="font-style: normal;font-weight: bolder;">'
													+ value + '</font>';
										}
									},
									{
										field : 'email',
										title : '食堂邮箱',
										width : 150,
										formatter : function(value, row, index) {
											return '<font style="font-style: normal;font-weight: bolder;word-wrap:break-word;white-space:normal;">'
													+ value + '</font>';
										}
									},
									{
										field : 'userName',
										title : '食堂负责人',
										width : 110,
										formatter : function(value, row, index) {
											if (value != null) {
												return '<font style="font-style: normal;font-weight: bolder;">'
														+ value + '</font>';
											} else {
												return "";
											}
										}
									},
									{
										field : 'companyName',
										title : '所属公司',
										width : 120,
										formatter : function(value, row, index) {
											if (value != null) {
												return '<font style="font-style: normal;font-weight: bolder;">'
														+ value + '</font>';
											} else {
												return "";
											}
										}
									},
									{
										field : 'addressName',
										title : '所属城市',
										width : 110,
										formatter : function(value, row, index) {
											return '<font style="font-style: normal;font-weight: bolder;">'
													+ value + '</font>';
										}
									},
									{
										field : 'projName',
										title : '所属项目',
										width : 120,
										formatter : function(value, row, index) {
											if (value != null) {
												return '<font style="font-style: normal;font-weight: bolder;">'
														+ value + '</font>';
											} else {
												return "";
											}
										}
									},
								
									{
										field : 'createTime',
										title : '创建时间',
										width : 150,
										formatter : function(value, row, index) {
											var unixTimestamp = new Date(value);
											return '<font style="font-style: normal;font-weight: bolder;">'
													+ (unixTimestamp
															.toLocaleString())
													+ '</font>';
										}

									},
									/* 	{
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

										}, */
									{
										field : 'action',
										title : '操作',
										width : 270,
										formatter : function(value, row, index) {
											var str = '&nbsp;&nbsp;';
											if ($.canEdit) {
												str += $
														.formatString(
																'<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>',
																row.id,
																'${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
											}
											str += '&nbsp;&nbsp;&nbsp;';
											if ($.canDelete) {
												str += $
														.formatString(
																'<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>',
																row.id,
																'${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
											}

											str += '&nbsp;&nbsp;&nbsp;';
											str += $
													.formatString(
															'<img onclick="addCarteConScoreValve(\'{0}\',\'{1}\');" src="{2}" title="添加餐厅评分预警阀值"/>',
															row.id,
															row.cafeName,
															'${pageContext.request.contextPath}/style/images/extjs_icons/arrow/arrow_divide.png');

											str += '&nbsp;&nbsp;&nbsp;';
											if ($.canAddCarteType) {

												str += $
														.formatString(
																'<img onclick="addCarteType(\'{0}\',\'{1}\');" src="{2}" title="添加菜品类型"/>',
																row.id,
																row.cafeName,
																'${pageContext.request.contextPath}/style/images/extjs_icons/layout/layout_add.png');
											}
											str += '&nbsp;&nbsp;&nbsp;';
											
											str += $
											.formatString(
													'<img onclick="grantFun(\'{0}\');" src="{1}" title="勾选评论项"/>',
													row.id,
													'${pageContext.request.contextPath}/style/images/extjs_icons/lock/lock_go.png');
											
											str += '&nbsp;&nbsp;&nbsp;';
											
								
											str += $
													.formatString(
															'<img onclick="searchConfigScore(\'{0}\',\'{1}\');" src="{2}" title="查看食堂配置项分数"/>',
															row.id,
															row.cafeName,
															'${pageContext.request.contextPath}/style/images/extjs_icons/email/email_open.png');
											
											str += '&nbsp;&nbsp;&nbsp;';
											str += $
													.formatString(
															'<img onclick="searchCafeComment(\'{0}\',\'{1}\');" src="{2}" title="查看食堂评论"/>',
															row.id,
															row.cafeName,
															'${pageContext.request.contextPath}/style/images/extjs_icons/email/email_add.png');

										
								
								
											return str;
										}
									} ] ],
							toolbar : '#toolbar',
							onLoadSuccess : function() {
								$('#searchForm table').show();
								parent.$.messager.progress('close');

								//$(this).datagrid('tooltip');
							},
							groupFormatter : function(group, rows) {
								return '- <span style="color:green">' + group
										+ ' </span>'
							},
							onRowContextMenu : function(e, rowIndex, rowData) {
								e.preventDefault();
								$(this).datagrid('unselectAll').datagrid(
										'uncheckAll');
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
		parent.$.messager
				.confirm(
						'询问',
						'您是否要删除当前食堂？',
						function(b) {
							if (b) {
								var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
								if (currentUserId != id) {
									parent.$.messager.progress({
										title : '提示',
										text : '数据处理中，请稍后....'
									});
									$
											.post(
													'${pageContext.request.contextPath}/cafrtoriumController/delete',
													{
														id : id
													},
													function(result) {
														if (result.success) {
															parent.$.messager
																	.show({
																		title : '提示',
																		msg : result.msg
																	});
															dataGrid
																	.datagrid('reload');
														} else {
															parent.$.messager
																	.show({
																		title : '提示',
																		msg : result.msg
																	});
														}
														parent.$.messager
																.progress('close');
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
	//查看食堂配置项分数
	function searchConfigScore(caftoriumId, caftrotiumName) {
		parent.$
				.modalDialog({
					title : '[' + caftrotiumName + ']' + '配置项分数',
					width : 650,
					height : 400,
					href : '${pageContext.request.contextPath}/configScoreController/manager?caftrotiumId='
							+ caftoriumId

				})
	}

	//添加菜品类型
	function addCarteType(caftrotiumId, caftrotiumName) {
		parent.$
				.modalDialog({
					title : '添加菜品类型',
					width : 400,
					height : 220,
					href : '${pageContext.request.contextPath}/carteTypeController/addPage?caftrotiumId='
							+ caftrotiumId
							+ '&caftrotiumName='
							+ caftrotiumName,
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

	//添加餐厅评分预警阀值 
	function addCarteConScoreValve(cafetoriumId, caftrotiumName) {
		parent.$
				.modalDialog({
					title : '餐厅评分项阀值',
					width : 400,
					height : 250,
					href : '${pageContext.request.contextPath}/cafrtoriumController/addCarteConScoreValve?cafetoriumId='
							+ cafetoriumId
							+ '&caftrotiumName='
							+ caftrotiumName,
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

	//查看食堂评论
	function searchCafeComment(caftrotiumId, caftrotiumName) {
		parent.$
				.modalDialog({
					title : '[' + caftrotiumName + ']' + '评论列表',
					width : 650,
					height : 400,
					href : '${pageContext.request.contextPath}/commentController/manager?caftrotiumId='
							+ caftrotiumId
				});
	}
	//编辑食堂
	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$
				.modalDialog({
					title : '编辑食堂',
					width : 600,
					height : 270,
					href : '${pageContext.request.contextPath}/cafrtoriumController/editPage?id='
							+ id,
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
	//勾选敏感词
	function grantFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$
				.modalDialog({
					title : '勾选配置项',
					width : 500,
					height : 400,
					href : '${pageContext.request.contextPath}/configController/grantPage?id='
							+ id,
					buttons : [ {
						text : '确定',
						handler : function() {
							parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
							var f = parent.$.modalDialog.handler.find('#form');
							f.submit();
						}
					} ]
				});
	}

	function addFun() {
		parent.$
				.modalDialog({
					title : '添加食堂',
					width : 600,
					height : 240,
					href : '${pageContext.request.contextPath}/cafrtoriumController/addPage',
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
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false"
			style="height: 100px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed"
					style="display: none;">
					<tr>
						<th></th>
						<td><input class="easyui-searchbox"
							data-options="prompt:'',width:130,height:29,menu:'#mm',editable:false,panelHeight:120,searcher:searchFun"
							style="width: 240px" />
							<div id="mm">
								<div data-options="name:'cafeName',iconCls:'icon-ok'">食堂名称</div>
								<div data-options="name:'cafeCode',iconCls:'icon-ok'">食堂编码</div>
								<div data-options="name:'mobileNo',iconCls:'icon-ok'">食堂电话</div>
								<div data-options="name:'address',iconCls:'icon-ok'">食堂地址</div>
							</div></td>
						<%-- 		   <th>所属区域</th>
					   <td>
					   <select id="addressId" name="addressId" class="easyui-combobox" data-options="width:130,height:29,editable:false,panelHeight:120"  >
					    <c:forEach items="${addressList}" var="de">
								<option value="${de.id}" >${de.addressName}</option>
							</c:forEach>
					   </select>
					   </td>
						<th>所属公司</th>
					   <td>
					 <select id="companyId" name="companyId" class="easyui-combobox" data-options="width:130,height:29,editable:false,panelHeight:120"  >
					
						<c:forEach items="${companyList}" var="de">
								<option value="${de.id}" >${de.companyName}</option>
							</c:forEach>
					</select>
					   </td>
					   		 --%>

					</tr>


				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid" title="食堂表单" data-options="collapsible:true"></table>
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
			test="${fn:contains(sessionInfo.resourceList, '/cafrtoriumController/delete')}">
			<div onclick="deleteFun();" data-options="iconCls:'pencil_delete'">删除</div>
		</c:if>
		<c:if
			test="${fn:contains(sessionInfo.resourceList, '/cafrtoriumController/editPage')}">
			<div onclick="editFun();" data-options="iconCls:'pencil'">编辑</div>
		</c:if>
	</div>
</body>
</html>