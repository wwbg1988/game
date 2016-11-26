<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
var form_Id = '${formId}';
var form_field_Id ='${formFieldId}';
	var existFieldDataGrid;
	$(function() {
		parent.$.messager.progress('close');

		$('#existFieldLayout').layout({
			fit : true
		});

		existFieldDataGrid = $('#existFieldDataGrid')
				.datagrid(
						{
							url : '${pageContext.request.contextPath}/fieldsController/addExistFieldDataGrid?formId='+form_Id,
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
								width : 150,
								hidden : true
							} ] ],
							columns : [ [ {
								field : 'paramDesc',
								title : '参数描述',
								width : 150
							},{
								field : 'paramName',
								title : '参数名称 ',
								width :130
							},{
								field : 'formName',
								title : '所属表单',
								width : 130
							},{
								field : 'action',
								title : '操作',
								width : 130,
								formatter : function(value, row, index) {
								    var	str = $.formatString('<img onclick="add_Funs(\'{0}\');" src="{1}" title="引用该字段"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil_add.png');
									return str;
								}
							} ] ],
							toolbar : '#toolbar',
							onLoadSuccess : function() {
								$('#formFieldAddsearchForm table').show();
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
		parent.$.messager.confirm('询问', '您是否要引用该字段信息？', function(b) {
			if (b) {

				if (id != null) {

					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});

					$.post(
							'${pageContext.request.contextPath}/fieldsController/addExistFields', {
							 refFieldId     : id,
							 formId      : form_Id,
							 formFieldId : form_field_Id
							}, function(result) {
								if (result.success) {
									parent.$.messager.progress('close');
									parent.$.messager.show({
										title : '提示',
										msg : result.msg
									});
									//关闭窗口
								/* 	parent.$.modalDialog.handler.dialog("close"); */
									  $('#existsFieldAdd').dialog("close");
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
		existFieldDataGrid.datagrid('load', {});
		existFieldDataGrid.datagrid('unselectAll').datagrid(
				'uncheckAll');

	}
	
	function searchFormFieldAddFun() {
		existFieldDataGrid.datagrid('load', $.serializeObject($('#formFieldAddsearchForm')));
	}
	function cleanFun() {
		$('#formFieldAddsearchForm input').val('');
		$('#forms').combobox('setValue', "请选择");


		existFieldDataGrid.datagrid('load', {});
	}
</script>

<div id="existFieldLayout" class="easyui-layout"
	style="">
		<div data-options="region:'north',border:false"
			style="height: 70px; overflow: hidden;">
			<form id="formFieldAddsearchForm">
				<table class="table table-hover table-condensed"
					style="display: none;">
					<tr>
					<th>参数描述</th>
						<td><input name="paramDesc" placeholder="输入参数描述"
							class="span2" /></td>
					<th>所属表单:</th>
					   <td>
					 <select id="forms" name="otherFormId" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'"  >
						<option value="">请选择</option>
						<c:forEach items="${formList}" var="de">
								<option value="${de.id}" >${de.name}</option>
							</c:forEach>
					</select>
					   </td>
					</tr>
	        </table>
			</form>
		</div>
	<div id="addcertDiv" data-options="region:'center'" style="padding: 5px; background: #eee;">
		<table id="existFieldDataGrid"></table>
	</div>
</div>
	<div id="toolbar" style="display: none;">
	

		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'brick_add',plain:true" onclick="searchFormFieldAddFun();">过滤条件</a><a
			href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'brick_delete',plain:true"
			onclick="cleanFun();">清空条件</a>
	</div>
