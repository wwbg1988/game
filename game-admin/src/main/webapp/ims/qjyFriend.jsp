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
			url : '${pageContext.request.contextPath}/qjyFriendController/dataGrid',
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
				field : 'age',
				title : '年龄',
				width : 70
			},{
				field : 'gender',
				title : '性别',
				width : 70,
				formatter:function(value,row,index){  
                     if(value==1){
                  	   return '男';
                     }else if(value==2){
                  	   return '女';
                     }
                } 
			},{
				field : 'name',
				title : '名称',
				width : 150
			},{
				field : 'qjyAccount',
				title : '亲加云账号',
				width : 150
			},{
				field : 'userAccount',
				title : '用户账号',
				width : 150
			},{
				field : 'userNo',
				title : '用户编号',
				width : 150
			},{
				field : 'userFriends',
				title : '用户好友',
				width : 450,
				formatter : function(value, row, index) {
					if(value!=null){
			     	return "<font style='word-wrap:break-word;white-space:normal;'>"+value+"</font>";
				    }
					else{
						return "";
					}
				}
			},
			{
				field : 'action',
				title : '操作',
				width : 70,
				formatter : function(value, row, index) {
					var str = '';
							str += $.formatString('<img onclick="adduserFun(\'{0}\');" src="{1}" title="增加好友"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/key.png');
				//	str += '&nbsp;';
				//			str += $.formatString('<img onclick="upLoadFun(\'{0}\');" src="{1}" title="上传图片"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/image_add.png');
							
				
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

	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
	     $('#gender').val('');
		dataGrid.datagrid('load', {});
	}

	function adduserFun(id) {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		parent.$.modalDialog({
			title : '添加好友',
			width :680,
			height : 800,
			href : '${pageContext.request.contextPath}/qjyFriendController/grantUserPage?ids=' + id,
			buttons : [ {
				text : '添加好友',
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
		<div   data-options="region:'north',title:'查询条件',border:false" style="height: 170px; overflow: hidden;">
			<form id="searchForm">
			
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
					    <th>年龄</th>
					    <td><input name="age" placeholder="可以查询年龄" class="easyui-validatebox" style="width:279px;"/></td>
					    <th>性别</th>
					    <td>
					    <select id="gender" name="gender"  class="easyui-combobox" style="width:282px;height: 29px;">
					      <option value="">请选择</option>
					      <option value="1">男</option>
					      <option value="2">女</option>
					    </select>
					    </td>
					</tr>
					<tr>
					    <th>名称</th>
					    <td><input name="name" placeholder="可以查询名称" class="easyui-validatebox" style="width:279px;"/></td>
					    <th>亲加云账号</th>
					    <td><input name="qjyAccount" placeholder="可以查询亲加云账号" class="easyui-validatebox" style="width:279px;"/></td>
					</tr>
					<tr>
					    <th>用户账号</th>
					    <td><input name="userAccount" placeholder="可以查询用户账号" class="easyui-validatebox" style="width:279px;"/></td>
					    <th>用户编号</th>
					    <td><input name="userNo" placeholder="可以查询用户编号" class="easyui-validatebox" style="width:279px;"/></td>
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