<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if
	test="${fn:contains(sessionInfo.resourceList, '/fieldDictController/insert')}">
	<script type="text/javascript">
		$.canAddfieldDict = true;
	</script>
</c:if>

<script type="text/javascript">
var field_Id = '${fieldId}';

	var dictGrid;
	$(function() {
		parent.$.messager.progress('close');

		$('#dictLayout').layout({
			fit : true
		});

		dictGrid = $('#dictGrid')
				.datagrid(
						{
							url : '${pageContext.request.contextPath}/dictController/dataGrid',
							fit : true,
							fitColumns : true,
							border : false,
							pagination : true,
							idField : 'id',
							pageSize : 30,
							pageList : [ 10, 20, 30, 40, 50 ],
							sortName : '',
							sortOrder : 'asc',
							checkOnSelect : false,
							selectOnCheck : false,
							nowrap : false,
							frozenColumns : [ [ {
								field : 'id',
								title : '编号',
								width : 80,
								hidden : true
							} ] ],
							columns : [ [{
								field : 'fieldValue',
								title : '字段值',
								width : 100
							}, {
								field : 'fieldDesc',
								title : '字段显示描述 ',
								width :110
							},{
								field : 'createTime',
								title : '创建时间',
								width : 140,
						        formatter:function(value,row,index){  
				                      var unixTimestamp = new Date(value);  
				                      return unixTimestamp.toLocaleString();  
				                      }  
								
							}, {
								field : 'lastUpdateTime',
								title : '修改时间',
								width : 140,
						        formatter:function(value,row,index){  
						        	if(value!=null){
					                      var unixTimestamp = new Date(value);  
					                      return unixTimestamp.toLocaleString();  
					                      } else{
					                    	  return "";
					                      }
				                      }  
								
							},{
								field : 'action',
								title : '操作',
								width : 90,
								formatter : function(value, row, index) {
									 var	str ="";
									 if($.canAddfieldDict){
								       str+= $.formatString('<img onclick="add_Funs(\'{0}\');" src="{1}" title="添加字典"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil_add.png');
									}
								    return str;
								}
							} ] ],
							toolbar : '#toolbar',
							onLoadSuccess : function() {

								parent.$.messager.progress('close');
								//不显示tooltip;
								//	$(this).datagrid('tooltip');
							},
							onRowContextMenu : function(e, rowIndex, rowData) {
								e.preventDefault();
								$(this).datagrid('unselectAll').datagrid(
										'uncheckAll');

								$(this).datagrid('selectRow', rowIndex);
							
							}
						});

	});
	function add_Funs(id){
		parent.$.messager.confirm('询问', '您是否要给字段添加该字典信息？', function(b) {
			if (b) {

				if (id != null) {

					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});

					$.post(
							'${pageContext.request.contextPath}/fieldDictController/insert', {
						    fieldId  : field_Id,
							 dictId  : id
							}, function(result) {
								if (result.success) {
									parent.$.messager.progress('close');
									parent.$.messager.show({
										title : '提示',
										msg : result.msg
									});
									//关闭窗口
								/* 	parent.$.modalDialog.handler.dialog("close"); */
									  $('#dict').dialog("close");
									parent.$.modalDialog.openner_dataGrid
											.datagrid('reload');
									parent.$.modalDialog.openner_dataGrid
											.datagrid('unselectAll').datagrid(
													'uncheckAll');
									
								}else{
									parent.$.messager.progress('close');
									parent.$.messager.show({
										title : '提示',
										msg : result.msg
									});
								}
							}, 'JSON');
				} else {
					parent.$.messager.show({
						title : '提示',
						msg : '更新错误，请联系管理员！'
					});
				}
			}
		});
	}

	function reloadsFun() {
		dictGrid.datagrid('load', {});
		dictGrid.datagrid('unselectAll').datagrid(
				'uncheckAll');

	}
</script>

<div id="dictLayout" class="easyui-layout"
	style="width: 720px; height: 288px;">
	<div id="addcertDiv" data-options="region:'center'" style="padding: 5px; background: #eee;">
		<table id="dictGrid"></table>
	</div>
</div>
