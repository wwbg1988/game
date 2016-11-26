<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>用户管理</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/carteUserController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>


<script type="text/javascript">
	var dataGrid;
	parent.$.messager.progress('close');
	$(function() {
		dataGrid = $('#dataGrid')
				.datagrid(
						{
							url : '${pageContext.request.contextPath}/carteUserController/dataGrid',
							fit : true,
							fitColumns : true,
							border : true,
							pagination : true,
							idField : 'id',
							pageSize : 30,
					        pageList : [ 10, 20, 30, 40, 50 ],
							//sortName : 'deptName',
							//sortOrder : 'asc',
							checkOnSelect : false,
							selectOnCheck : false,
							singleSelect:true,
							nowrap : false,
							frozenColumns : [ [ {
								field : 'id',
								title : '编号',
								width : 150,
								checkbox : true
							} ] ],
							columns : [ [	
							           {
							         	field : 'openId',
								        title : '微信id',
								        width : 160,
								        formatter : function(value, row, index) {
									     var a="";
									     if(value!=null){
										    a=value;
									     }else{
										    a="";
									     }
									         return '<font style="font-style: normal;font-weight: bolder;">'
											+ a + '</font>';
								        }
							        },
									{
										field : 'name',
										title : '用户姓名',
										width : 160,
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
										field : 'birthday',
										title : '用户生日',
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
										field : 'phone',
										title : '用户电话',
										width : 130,
										formatter : function(value, row, index) {
											if(value!=null){
											return '<font style="font-style: normal;font-weight: bolder;word-wrap:break-word;white-space:normal;">'
													+ value + '</font>';
											}else{
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

									}/* ,
							        {
										field : 'action',
										title : '操作',
										width : 270,
										formatter : function(value, row, index) {
											var str = '&nbsp;&nbsp;';
										
											if ($.canDelete) {
												str += $
														.formatString(
																'<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>',
																row.id,
																'${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
											   }
											return str;
										}
									} */
									] ],
							toolbar : '#toolbar',
							onLoadSuccess : function() {
								$('#searchForm table').show();
								parent.$.messager.progress('close');

								//$(this).datagrid('tooltip');
							},
						     onRowContextMenu : function(e, rowIndex, rowData) {
								e.preventDefault();
								$(this).datagrid('unselectAll').datagrid(
										'uncheckAll');
								$(this).datagrid('selectRow', rowIndex);
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
						'您是否要删除当前用户？',
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
													'${pageContext.request.contextPath}/carteUserController/delete',
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
						   <td>
					    <input  class="easyui-searchbox"  data-options="prompt:'',width:130,height:29,menu:'#mm',editable:false,panelHeight:100,searcher:searchFun" style="width:240px"/>
					<div id="mm">
		               <div data-options="name:'name',iconCls:'icon-ok'">用户姓名</div>
		               <div data-options="name:'birthday',iconCls:'icon-ok'">用户生日</div>
		               <div data-options="name:'phone',iconCls:'icon-ok'">用户电话</div>
		           </div>
					   </td>
			       </tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid" title="商城用户表单" data-options="collapsible:true"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
	

		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a><a
			href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'brick_delete',plain:true"
			onclick="cleanFun();">清空条件</a>
	</div>
</body>
</html>