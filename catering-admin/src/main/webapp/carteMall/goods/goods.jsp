<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>商品管理</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/goodsController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>


<script type="text/javascript">
	var dataGrid;
	parent.$.messager.progress('close');
	$(function() {
		dataGrid = $('#dataGrid')
				.propertygrid(
						{
							url : '${pageContext.request.contextPath}/goodsController/dataGrid',
							fit : true,
							fitColumns : true,
							border : true,
							pagination : true,
							idField : 'id',
						    groupField:"cafeName",
							showGroup:true,
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
										field : 'goodsName',
										title : '商品名称',
										width : 180,
										formatter : function(value, row, index) {
											return '<font style="font-style: normal;font-weight: bolder;">'
													+ value + '</font>';
										}
									},
									{
										field : 'price',
										title : '商品价格',
										width : 150,
										formatter : function(value, row, index) {
											return '<font style="font-style: normal;font-weight: bolder;">'
													+ value + '</font>';
										}
									},
									{
										field : 'salesPrice',
										title : '促销价格',
										width : 110,
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
										field : 'icon',
										title : '商品图片',
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
										field : 'introduce',
										title : '商品介绍',
										width : 200,
										formatter : function(value, row, index) {
											return '<font style="font-style: normal;font-weight: bolder;">'
													+ value + '</font>';
										}
									},
									{
										field : 'isTurn',
										title : '是否海报轮转',
										width : 160,
										formatter : function(value, row, index) {
											var a="";
											if(value==1){
												a="是";
											}else{
												a="否";
											}
											return '<font style="font-style: normal;font-weight: bolder;">'
													+ a + '</font>';
										}
									},
									{
										field : 'countLimit',
										title : '最大购买量',
										width : 140,
										formatter : function(value, row, index) {
											return '<font style="font-style: normal;font-weight: bolder;">'
													+ value + '</font>';
										}
									},
									{
										field : 'goodsStocks',
										title : '库存量',
										width : 140,
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
										field : 'goodsTypeName',
										title : '所属商品类型',
										width : 150,
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
										field : 'percent',
										title : '商品折扣',
										width : 110,
										formatter : function(value, row, index) {
											if(value!=0 && value!=null){
												return '<font style="font-style: normal;font-weight: bolder;">'
												+ value + '%</font>';
											}else{
												return "";
											}
										
										}
									},
									{
										field : 'createTime',
										title : '创建时间',
										width : 180,
										formatter : function(value, row, index) {
											if(value!=null){
												var unixTimestamp = new Date(value);
												return '<font style="font-style: normal;font-weight: bolder;">'
														+ (unixTimestamp
																.toLocaleString())
														+ '</font>';
											}else{
												return "";
											}
										
										}

									} ,
							        {
										field : 'action',
										title : '操作',
										width : 170,
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
									} 
									] ],
							toolbar : '#toolbar',
							onLoadSuccess : function() {
								$('#searchForm table').show();
								parent.$.messager.progress('close');

								//$(this).datagrid('tooltip');
							},
							 groupFormatter:function(group,rows){
								return  '- <span style="color:green">'+group+' </span>'
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
						'您是否要删除当前商品？',
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
													'${pageContext.request.contextPath}/goodsController/delete',
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

	function add_Goods(){
	     parent.$
			.modalDialog({
				title : '添加商品',
				width : 650,
				height :410,
				href : '${pageContext.request.contextPath}/goodsController/addPage',
				buttons : [ {
					text : '添加',
					handler : function() {
						parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#form');
						f.submit();
					}
				} ]
			});
			
		}
	
	function uploadGoods(){
		parent.$.modalDialog({
			title:'上传商品',
			width:650,
			height:410,
			href:'${pageContext.request.contextPath}/goodsController/uploadExcelPage',
			buttons : [ {
				text : '上传',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
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
					    <td>
					    <input  class="easyui-searchbox"  data-options="prompt:'',width:130,height:29,menu:'#mm',editable:false,panelHeight:100,searcher:searchFun" style="width:240px"/>
					<div id="mm">
		               <div data-options="name:'goodsName',iconCls:'icon-ok'">商品名称</div>
		               <div data-options="name:'price',iconCls:'icon-ok'">商品价格</div>
		               <div data-options="name:'salesPrice',iconCls:'icon-ok'">促销价格</div>
		               <div data-options="name:'countLimit',iconCls:'icon-ok'">最大购买量</div>
		               <div data-options="name:'percent',iconCls:'icon-ok'">商品折扣</div>
		           </div>
					   </td>
			       </tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid" title="商品表单" data-options="collapsible:true"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
	     <a onclick="uploadGoods();" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'transmit'">上传商品</a>
		 <a onclick="add_Goods();" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'transmit'">添加商品</a>
         <a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a><a
			href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'brick_delete',plain:true"
			onclick="cleanFun();">清空条件</a>
	</div>
</body>
</html>