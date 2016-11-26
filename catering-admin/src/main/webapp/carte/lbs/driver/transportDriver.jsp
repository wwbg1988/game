<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>驾驶员管理</title>

<jsp:include page="../../../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/transportDriverController/manager')==false}">
	<% 
		request.getRequestDispatcher("../../../error/403.jsp").forward(request, response);
	%>
</c:if>

<c:if test="${fn:contains(sessionInfo.resourceList, '/transportDriverController/addPage')}">
	<script type="text/javascript">
		$.canAdd = true;
	</script>
</c:if>


<c:if test="${fn:contains(sessionInfo.resourceList, '/transportDriverController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>

<c:if test="${fn:contains(sessionInfo.resourceList, '/transportDriverController/editPasswordPage')}">
	<script type="text/javascript">
		$.canEditPassword = true;
	</script>
</c:if>

<c:if test="${fn:contains(sessionInfo.resourceList, '/transportDriverController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>



<script type="text/javascript">

	var dataGrid;
	$(function() {
		parent.$.messager.progress('close');

		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/transportDriverController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			rownumbers : true,
			model:true,
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
				field : 'account',
				title : '配送人账号',
				width : 100
			}, {
				field : 'name',
				title : '姓名',
				width : 100
			}, {
				field : 'nickName',
				title : '昵称',
				width : 100
			}, {
				field : 'imei',
				title : '设备号',
				width : 100
			}, {
				field : 'phone',
				title : '手机号码',
				width : 100
			}, {
				field : 'state',
				title : '状态',
				width : 100,
				formatter:formatDriverState
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
			} ,{
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
					
					if ($.canEditPassword) {
						str += '&nbsp;&nbsp;'+$.formatString(
								'<img onclick="editPasswordFun(\'{0}\');" src="{1}" title="修改密码"/>',
								row.id,
								'${pageContext.request.contextPath}/style/images/extjs_icons/cog_edit.png');
					}
					
					if ($.canDelete) {
						str += '&nbsp;&nbsp;'+$.formatString(
								'<img onclick="delFun(\'{0}\');" src="{1}" title="删除"/>',
								row.id,
								'${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					}
					
					return str;
				}
				
			}] ],
			toolbar : [ {
				iconCls : 'pencil_add',
				text : '添加',
				handler : function() {
					if($.canAdd){
						addFun();
					}
					else{
						alert("没有权限");
					}
					
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
	        	 $('#supplier').combobox('clear');

	        	//供应商
	             $('#supplier').combobox({
	                 disabled: false,
	                 url: '${pageContext.request.contextPath}/supplierController/findSuppliersBy?projectId=' + record.value,
	                 valueField: 'id',
	                 textField: 'supplierName'
	             });

	         }
	     });
		
		
		$('#project').combobox('setValue',null);
		$('#supplier').combobox('setValue',null);
		$('#state').combobox('setValue',null);
		
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
	
	//格式化驾驶员状态
	function formatDriverState(value, row, index) {
		if (value != null) {
			if("1"==value){
				return "有任务";
			}
			return "无任务";
		} else {
			return "无任务";
		}
	}
	
	//添加
	function addFun(){
		parent.$.modalDialog({
			title : '配送人信息',
			width : 800,
			height : 300,
			href : '${pageContext.request.contextPath}/transportDriverController/addPage',
			buttons : [ {
				text : '录入',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
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
	
	//修改密码
	function editPasswordFun(id){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '修改密码',
			width : 400,
			height : 300,
			href : '${pageContext.request.contextPath}/transportDriverController/editPasswordPage?id='+id,
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
	
	//编辑
	function editFun(id){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑任务',
			width : 800,
			height : 300,
			href : '${pageContext.request.contextPath}/transportDriverController/editPage?id='+id,
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
									$.post('${pageContext.request.contextPath}/transportDriverController/delete',
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
	
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false"
			style="height: 120px">
			<form id="searchForm">
				<table class="table table-hover table-condensed">
					<tr>
						<th>项目</th>
						<td>
							<select id="project" name="projectId" class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120">
								<c:forEach items="${projects}" var="de">
									<option value="${de.id}">${de.projName}</option>
								</c:forEach>
					   		</select>
						</td>
						<th>供应商</th>
						<td>
							<select id="supplier" name="adminUserId" class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120">
					   		</select>
						</td>
					</tr>
					<tr>
						<th>状态</th>
						<td>
							<select id="state" name="state" class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120">
								<option value="1">有任务</option>
								<option value="0">无任务</option>
					   		</select>
						</td>
						<th></th>
						<td>
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

