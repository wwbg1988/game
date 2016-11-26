<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%   
String basePath = "/carte/umedit/";  
String path = request.getContextPath();
String baseIp = null;
if(request.getContextPath() == null || request.getContextPath() == "")
{
    baseIp = "..";
}
else
{
    baseIp = "../..";
}
%> 
<title>用户管理</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/imsUserController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/imsUserController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/imsUserController/grantPage')}">
	<script type="text/javascript">
		$.canGrant = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/imsUserController/editPwdPage')}">
	<script type="text/javascript">
		$.canEditPwd = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').propertygrid({
			url : '${pageContext.request.contextPath}/imsUserController/dataGrid',
			fit : true,
			fitColumns : true,
			//rownumbers : true ,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 200,
			groupField:"deptNames",
			showGroup:true,
			pageList : [100,200,300,400 ],
			//sortName : 'deptName',
			//sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			//singleSelect:true,
			nowrap : false,
			frozenColumns : [ [{
				field : 'id',
				title : '编号',
				width : 150,
				hidden:true
			
			}  ] ],
			columns : [ [ /*  {
				field : 'userNo',
				title : '员工编号',
				width : 150,
				hidden: true
			}, */  {
				field : 'userAccount',
				title : '登录名称',
				width : 120,
				formatter : function(value, row, index) {
					if(value!=null){
					return '<font style="font-style: normal;font-weight: bolder;">'
							+ value + '</font>';
					}else{
					return "";
							}
				}
			},{
				field : 'qjyAccount',
				title : '亲加云账号',
				width : 150,
				formatter : function(value, row, index) {
					if(value!=null){
					return '<font style="font-style: normal;font-weight: bolder;">'
							+ value + '</font>';
					}else{
					return "";
							}
				}
			}, {
				field : 'name',
				title : '用户姓名',
				width : 150,
				formatter : function(value, row, index) {
					if(value!=null){
					return '<font style="font-style: normal;font-weight: bolder;">'
							+ value + '</font>';
					}else{
					return "";
							}
				}
			},{
				field : 'email',
				title : '电子邮箱',
				width : 150,
				formatter : function(value, row, index) {
					if(value!=null){
					return '<font style="font-style: normal;font-weight: bolder;word-wrap:break-word;white-space:normal;">'
							+ value + '</font>';
					}else{
					return "";
							}
				}
			},{
				field : 'phone',
				title : '手机号码',
				width : 150,
				formatter : function(value, row, index) {
					if(value!=null){
					return '<font style="font-style: normal;font-weight: bolder;word-wrap:break-word;white-space:normal;">'
							+ value + '</font>';
					}else{
					return "";
							}
				}
			}, {
				field : 'gender',
				title : '性别',
				width : 100,
				formatter : function(value, row, index) {
					 if(value==1){
							return '<font style="font-style: normal;font-weight: bolder;">男</font>';
					 }else if(value==2){
							return '<font style="font-style: normal;font-weight: bolder;">女</font>';
					 }
				}
			},{
				field : 'userImage',
				title : '用户图片',
				width : 150,
				formatter : function(value, row, index) {
					  if(row.userImage==null||row.userImage==""){
			        	   return "";
			           }else{
			        	 
			        	   return "<img src='<%=baseIp%>"+row.userImage+"' />";
			           }
				}
			},{
				field : 'projectNames',
				title : '项目名称',
				width : 150,
				formatter : function(value, row, index) {
					if(value!=null){
					return '<font style="font-style: normal;font-weight: bolder;">'
							+ value + '</font>';
					}else{
					return "";
							}
				}
			},{
				field : 'deptNames',
				title : '部门名称',
				width : 150,
				formatter : function(value, row, index) {
					if(value!=null){
					return '<font style="font-style: normal;font-weight: bolder;">'
							+ value + '</font>';
					}else{
					return "";
							}
				}
			},{
				field : 'isAdmin',
				title : '是否部门管理员',
				width : 110,
				 formatter:function(value,row,index){  
                     if(value==1){
                  	   return '是';
                     }else {
                  	   return '否';
                     }
                }
			},{
				field : 'action',
				title : '操作',
				width : 140,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canEdit) {
						str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					}
					str += '&nbsp;';
					if ($.canGrant) {
						str += $.formatString('<img onclick="grantFun(\'{0}\');" src="{1}" title="授权"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/key.png');
					}
					str += '&nbsp;';
					if ($.canDelete) {
						str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					}
					str += '&nbsp;';
					if ($.canEditPwd) {
						str += $.formatString('<img onclick="editPwdFun(\'{0}\');" src="{1}" title="修改密码"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/lock/lock_edit.png');
					}
					str += '&nbsp;&nbsp;&nbsp;';
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
			groupFormatter:function(group,rows){
				return  '- <span style="color:green">'+group+' </span>'
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
		parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
				if (currentUserId != id) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/imsUserController/deleteUser', {
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
			title : '编辑用户',
			width :768,
			height : 480,
			href : '${pageContext.request.contextPath}/imsUserController/edit?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#formEdit');
					f.submit();
					var f2 = parent.$.modalDialog.handler.find('#projectFormEdit');
					f2.submit();
				}
			} ]
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加用户',
			width :768,
			height : 480,
			href : '${pageContext.request.contextPath}/imsUserController/add',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					 var f2 = parent.$.modalDialog.handler.find('#projectForm');
					 f2.submit();
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				//	if(f.submit().success){
				//		alert("11111111111111");
				//		var f2 = parent.$.modalDialog.handler.find('#projectForm');
				//		f2.submit();
				//	}
					
				}
			} ]
		});
	}



	function grantFun(id) {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		parent.$.modalDialog({
			title : '用户授权',
			width : 500,
			height : 300,
			href : '${pageContext.request.contextPath}/imsUserController/grantPage?ids=' + id,
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

	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		$("#gender").val("");
		dataGrid.datagrid('load', {});
	}
	
	
	function editPwdFun(id) {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		parent.$.modalDialog({
			title : '编辑用户密码',
			width : 500,
			height : 200,
			href : '${pageContext.request.contextPath}/imsUserController/editPwdPage?id=' + id,
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
			href : '${pageContext.request.contextPath}/imsUserController/upLoadImage?id=' + id,
			buttons : [ {
				text : '上传',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit ();
				}
			} ]
		});
	}
	
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 160px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
						<th>登录名</th>
						<td><input name="userAccount" placeholder="可以查询登录名" class="easyui-validatebox"  style="width: 215px;"/></td>
						 <th>员工姓名</th>
					    <td><input name="name" placeholder="可以查询员工姓名" class="easyui-validatebox"  style="width: 215px;"/></td>
					</tr>
					<tr>
					     <th>电子邮箱</th>
					     <td><input name="email" placeholder="可以查询邮箱" class="easyui-validatebox"  style="width: 215px;"/></td>
					     <th>用户编号</th>
					     <td><input name="userNo" placeholder="可以查询用户编号" class="easyui-validatebox"  style="width: 215px;"/></td>
					</tr>
					<tr>
					    <th>性别</th>
					    <td>
					       <select id="gender" name="gender"  class="easyui-combobox" style="height: 27px;">
					          <option value="">请选择</option>
					          <option value="1">男</option>
					          <option value="2">女</option>
					       </select>
					    </td>
					    <th>亲加云账号</th>
					    <td><input name="qjyAccount" placeholder="可以查询亲加云账号" class="easyui-validatebox"  style="width: 215px;"/></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid" title="团餐用户表单" data-options="collapsible:true"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
		<!--<c:if test="${fn:contains(sessionInfo.resourceList, '/userController/grantPage')}">
			<a onclick="batchGrantFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'tux'">批量授权</a>
		</c:if>-->
	
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	</div>

</body>
</html>