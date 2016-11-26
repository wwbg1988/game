<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<script type="text/javascript">
var dataGrid;
$(function() {
	parent.$.messager.progress('close');

	$('#dictLayout').layout({
		fit : true
	});
	
	dataGrid = $('#dataGrid1').datagrid(
			{
				url : '${pageContext.request.contextPath}/sensitiveController/sensitivedataGrid',
				fit : true,
				fitColumns : true,
				border : false,
				pagination : true,
				idField : 'id',
				pageSize : 10,
				groupField:"groupTime",
				pageList : [ 10, 20, 30, 40, 50 ],
				checkOnSelect : false,
				selectOnCheck : false,
				rownumbers : true,
				singleSelect:true,
				nowrap : false,
			    frozenColumns : [ [ {
			    	field : 'id',
					title : '编号',
					width : 150,
					hidden : true
				} ] ],
				columns : [ [
					{
						field : 'proName',
						title : '阀值所属项目 ',
						width : 130
					},
					{
						field : 'valveCount',
						title : '条数触发的阀值 ',
						width : 130
					},
					{
						field : 'valvePercent',
						title : '百分比触发的阀值',
						width : 130,
					},
					{
						field : 'level',
						title : '阀值等级',
						width : 130,
					},
					{
					field : 'action',
					title : '操作',
					width : 80,
					formatter : function(value, row, index) {
							str = $.formatString('<img onclick="saveSensitive(\'{0}\');" src="{1}" title="选择预警阀值"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil_add.png');
						 return str;
					}
				} ] ],
				//toolbar : '#toolbar',
				onLoadSuccess : function() {
					$('#searchForm table').show();
					parent.$.messager.progress('close');
					//parent.$.modalDialog.openner_dataGrid = dataGrid;
					$(this).datagrid('tooltip');
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

	//给敏感词设置预警阀值
	function saveSensitive(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm(
					'确定',
					'请点击确定为敏感词设置预警阀值？',
					function(b) {
						if (b) {
							var currentUserId = '${sessionInfo.id}';
							if (currentUserId != id) {
								parent.$.messager.progress({
									title : '提示',
									text : '数据处理中，请稍后....'
								});
								$.post(
									'${pageContext.request.contextPath}/sensitiveController/SaveSensitive',
									{
										id : id,
										sensitiveId:$("#sensitiveId").val()
									},
									function(result) {
										if (result.success) {
											parent.$.messager.show({
														title : '提示',
														msg : result.msg
													});
											parent.$.modalDialog.openner_dataGrid.datagrid('reload');
										    parent.$.modalDialog.handler.dialog('close');
											parent.$.messager.progress('close');
										}
										
									}, 'JSON');
							}
						}
					});
	}

</script>
<div id="dictLayout" class="easyui-layout" style=" ">
   <input type="hidden" value="${sensitiveId}" id="sensitiveId"/>
  <div data-options="region:'center',title:''" style="padding: 5px; background: #eee;">
	<table id="dataGrid1"></table>
  </div>
</div>
	
