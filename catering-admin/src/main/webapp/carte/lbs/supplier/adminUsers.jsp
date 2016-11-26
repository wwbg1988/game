<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>后台账号</title>
<c:if test="${fn:contains(sessionInfo.resourceList, '/supplierController/bindingSupplier/adminUsers')==false}">	
	<% 
		request.getRequestDispatcher("../../../error/403.jsp").forward(request, response);
	%>
</c:if>
<jsp:include page="../../../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/supplierController/bindingSupplier')}">
	<script type="text/javascript">
		$.canBinding = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/userController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			rownumbers : true,
			idField : 'id',
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
			}, {
				field : 'userAccount',
				title : '登录名称',
				width : 80
			} ] ],
			columns : [ [ {
				field : 'pwd',
				title : '密码',
				width : 60,
				formatter : function(value, row, index) {
					return '******';
				}
			}, {
				field : 'userNo',
				title : '员工编号',
				width : 150
			}, {
				field : 'deptName',
				title : '部门',
				width : 150,
				hidden : true
			}, {
				field : 'roleIds',
				title : '所属角色ID',
				width : 150,
				hidden : true
			}, {
				field : 'name',
				title : '用户姓名',
				width : 150
			}, {
				field : 'action',
				title : '操作',
				width : 100,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canBinding) {
						str += $.formatString('<img onclick="bindingSupplierFun(\'{0}\');" src="{1}" title="绑定供应商"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/key.png');
					}
					return str;
				}
			} ] ],
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');
			},
			toolbar : [ {
				iconCls : 'brick_add',
				text : '过滤查询',
				handler : function() {
					searchFun();
				}
			}, '-', {
				iconCls : 'brick_delete',
				text : '清除过滤',
				handler : function() {
					clearFun();
				}
			} ]
		});
	});

	function bindingSupplierFun(id) {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		parent.$.modalDialog({
			title : '绑定供应商',
			width : 500,
			height : 400,
			href : '${pageContext.request.contextPath}/supplierController/bindingSupplierPage?adminUsersId=' + id,
			buttons : [ {
				text : '绑定',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function deleteFun(id) {
	/* 	if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';
				if (currentUserId != id) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/supplierController/delete', {
						id : id
					}, function(result) {
						parent.$.messager.progress('close');
						
						if (result.success) {
							parent.$.messager.show({
										title : '提示',
										msg : result.msg
									});
							dataGrid.datagrid('reload');
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
						msg : '不可以删除自己！'
					});
				}
			}
		}); */
	}


	function addFun() {
		/* parent.$.modalDialog({
			title : '添加用户',
			width : 700,
			height : 500,
			href : '${pageContext.request.contextPath}/supplierController/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		}); */
	}

	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function clearFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
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
						<th></th>
						<td>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
</body>
</html>