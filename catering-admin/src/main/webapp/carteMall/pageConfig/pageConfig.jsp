<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>微信商城配置项管理</title>
<c:if test="${fn:contains(sessionInfo.resourceList, '/pageConfigController/manager')==false}">	
	<% 
		request.getRequestDispatcher("../../error/403.jsp").forward(request, response);
	%>
</c:if>

<jsp:include page="../../inc.jsp"></jsp:include>

<c:if test="${fn:contains(sessionInfo.resourceList, '/pageConfigController/addPage')}">
	<script type="text/javascript">
		$.canAdd = true;
	</script>
</c:if>

<c:if test="${fn:contains(sessionInfo.resourceList, '/pageConfigController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>

<c:if test="${fn:contains(sessionInfo.resourceList, '/pageConfigController/editEnabledPage')}">
	<script type="text/javascript">
		$.canSet = true;
	</script>
</c:if>

<c:if test="${fn:contains(sessionInfo.resourceList, '/pageConfigController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>

<script type="text/javascript">

	var dataGrid;
	$(function() {
		parent.$.messager.progress('close');

		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/pageConfigController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			rownumbers : true,
			model : true,
			idField : 'id',
			pageSize : 10,
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
				width : 80,
				hidden : true
			} ] ],
			columns : [ [ {
				field : 'name',
				title : '配置项描述',
				width : 100
			}, {
				field : 'cafetoriumName',
				title : '餐厅名称',
				width : 100
			}, {
				field : 'enabledUrl',
				title : '启用时url',
				width : 100
			}, {
				field : 'notEnabledUrl',
				title : '非启用时url',
				width : 100
			},
			{
				field : 'isEnabled',
				title : '是否启用',
				formatter:formatEnabled,
				width : 100
			}, {
				field : 'projectName',
				title : '项目',
				formatter:formatNull,
				width : 100
			}, {
				field : 'createTime',
				title : '创建时间',				
				formatter:formatDate,
				width : 100
			}, {
				field : 'lastUpdateTime',
				title : '最后修改时间',
				formatter:formatDate,
				width : 100
			},{
				field : 'flag',
				title : '动作',
				width : 100,
				formatter : function(value, row, index) {
					var str='';
					/* if ($.canAdd) {
							str += '&nbsp;&nbsp;'+$.formatString(
									'<img onclick="addFun(\'{0}\');" src="{1}" title="发布"/>',
									row.id,
									'${pageContext.request.contextPath}/style/images/extjs_icons/pencil_add.png');
					} */
					
					if ($.canEdit) {
						str += '&nbsp;&nbsp;'+$.formatString(
								'<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>',
								row.id,
								'${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					}
					
					if ($.canSet) {
						str += '&nbsp;&nbsp;'+$.formatString(
								'<img onclick="editEnabledFun(\'{0}\');" src="{1}" title="配置"/>',
								row.id,
								'${pageContext.request.contextPath}/style/images/extjs_icons/cog.png');
					}
					
					if ($.canDelete) {
						str += '&nbsp;&nbsp;'+$.formatString(
								'<img onclick="delFun(\'{0}\');" src="{1}" title="删除"/>',
								row.id,
								'${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					}
					
					return str;
				}
			}
			] ],
			toolbar : [ {
				iconCls : 'pencil_add',
				text : '添加',
				handler : function() {
					addFun();
				}
			}, '-', {
				iconCls : 'brick_add',
				text : '过滤查询',
				handler : function() {
					searchFun();
				}
			}, '-', {
				iconCls : 'brick_delete',
				text : '清空过滤',
				handler : function() {
					clearFun();
				}
			} ]
		});	
		
		
		$('#project').combobox({
	         onSelect: function (record) {
	        	 $('#cafetoriumId').combobox('clear');

	        	//供应商
	             $('#cafetoriumId').combobox({
	                 disabled: false,
	                 url: '${pageContext.request.contextPath}/cafrtoriumController/findCafetoriumsBy?projId=' + record.value,
	                 valueField: 'id',
	                 textField: 'cafeName'
	             });

	         }
	     });
		
	});
	
	
	//替换空
	function formatNull(value, row, index) {
		if (value == null) {
			return "空";
		}
		else{
			return value;
		}
		
	}

	//格式化时间
	function formatDate(value, row, index) {
		if (value != null) {
			var unixTimestamp = new Date(value);
			return unixTimestamp.toLocaleString();
		} else {
			return "";
		}
	}
	
	//格式化1,0
	function formatEnabled(value, row, index) {
		if (value != null) {
			if("1"==value){
				return "是";
			}
			return "否";
		} else {
			return "";
		}
	}
	
	//添加
	function addFun(){
		parent.$.modalDialog({
			title : '添加配置项',
			width : 800,
			height : 230,
			href : '${pageContext.request.contextPath}/pageConfigController/addPage',
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
	
	//编辑
	function editFun(id){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑配置',
			width : 800,
			height : 300,
			href : '${pageContext.request.contextPath}/pageConfigController/editPage?id='+id,
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
	
	//配置
	function editEnabledFun(id){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑配置',
			width : 800,
			height : 300,
			href : '${pageContext.request.contextPath}/pageConfigController/editEnabledPage?id='+id,
			buttons : [ {
				text : '配置',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}
	
	//删除
	function delFun(id){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager
				.confirm('提示','您是否要删除当前任务？',
						function(b) {
							if (b) {
								var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
								if (currentUserId != id) {
									parent.$.messager.progress({
										title : '提示',
										text : '数据处理中，请稍后....'
									});
									$.post('${pageContext.request.contextPath}/pageConfigController/delete',
									{
										id : id
									},
									function(result) {
										parent.$.messager
										.progress('close');
										
										if (result.success) {
											parent.$.messager
													.show({
														title : '提示',
														msg : result.msg
													});
											dataGrid
													.datagrid('reload');
										}
										else{
											parent.$.messager.show({
												title : '提示',
												msg : result.msg
											});
										}
										
									}, 'JSON');
								} else {
									parent.$.messager.show({
										title : '提示',
										msg : '删除失败！'
									});
								}
							}
						});
	}
	
	//查询
	function searchFun(){
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	
	//清空条件
	function clearFun(){
		$('#searchForm input').val('');
		$('#searchForm select').val('');
	}
	
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false"
			style="height: 120px">
			<form id="searchForm">
				<table class="table table-hover table-condensed">
					<tr>
						<th>项目名称</th>
						<td>
							<select id="project" name="projectId"  class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120">
								<c:forEach items="${projects}" var="de">
									<option value="${de.id}">${de.projName}</option>
								</c:forEach>
						   	 </select>
						</td>
						<th>餐厅名字</th>
						<td>
							<select id="cafetoriumId" name="cafetoriumId" class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120">
					   		</select>
						</td>
					</tr>
					<tr>
						<th>描述</th>
						<td>
							<input name="name" placeholder="输入配置项描述" class="span2" style="width:196px;"/>
						</td>
						<th>是否启用</th>
						<td>
							<select id="isEnabled" name="isEnabled" class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120">
								<option value="1">是</option>
								<option value="0">否</option>
					   		</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',title:''">
			<table id="dataGrid"></table>
		</div>
	</div>
</body>
</html>

