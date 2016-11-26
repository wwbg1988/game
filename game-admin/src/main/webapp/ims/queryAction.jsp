<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if
	test="${fn:contains(sessionInfo.resourceList, '/queryConditionController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
	
</c:if>

<c:if
	test="${fn:contains(sessionInfo.resourceList, '/queryConditionController/addPage')}">
	<script type="text/javascript">
		$.canAdd = true;
	</script>
</c:if>

<script type="text/javascript">
var query_Id = '${queryId}';

var dataGrid;
$(function() {
	parent.$.messager.progress('close');

	$('#conditionLayout').layout({
		fit : true
	});
	
	dataGrid = $('#dataGrid')
	.datagrid(
			{
				url : '${pageContext.request.contextPath}/queryActionController/dataGrid?queryId='+query_Id,
				fit : true,
				fitColumns : true,
				border : false,
				pagination : true,
				idField : 'id',
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				//sortName : 'deptName',
				//sortOrder : 'asc',
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : false,
			    frozenColumns : [ [ {
					field : 'id',
					title : '编号',
					width : 150,
					hidden : true
				} ] ],
				columns : [ [{
					field : 'projectName',
					title : '项目名称',
					width : 190
				}, {
					field : 'queryName',
					title : '查询名称',
					width :170
				},{
					field : 'name',
					title : '动作名称',
					width :170
				},{
					field : 'url',
					title : '地址',
					width :170
				},{
					field : 'action',
					title : '操作',
					width : 180,
					formatter : function(value, row, index) {
						var str = '';
					
							str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
							str += '&nbsp;&nbsp;&nbsp;';
							str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
							return str;

					}
				} ] ],
				//toolbar : '#toolbar',
				onLoadSuccess : function() {
					$('#searchForm table').show();
					parent.$.messager.progress('close');
					parent.$.modalDialog.openner_dataGrid = dataGrid;
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


function addFun() {

	   $('#dd').dialog({
		    title: '添加自定义动作',
		    width:500,
		    height:150,
		    closed: false,
		    cache: false,
		    href:'${pageContext.request.contextPath}/queryActionController/addPage?queryId='+query_Id,
		    modal: true,
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = $('#dd').find("#form");
					f.submit();
				}
			} ]
		});

}

function editFun(id) {

	   $('#dd1').dialog({
		    title: '编辑自定义动作',
		    width:500,
		    height:150,
		    closed: false,
		    cache: false,
		    href:'${pageContext.request.contextPath}/queryActionController/editPage?id='+id,
		    modal: true,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = $('#dd1').find("#form");
					f.submit();
				}
			} ]
		});
}

function deleteFun(id) {
	if (id == undefined) {//点击右键菜单才会触发这个
		var rows = dataGrid.datagrid('getSelections');
		id = rows[0].id;
	} else {//点击操作里面的删除图标会触发这个
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
	}
	parent.$.messager.confirm('询问', '您是否要删除当前字段？', function(b) {
		if (b) {
			var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
			if (currentUserId != id) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/queryActionController/deleteAction', {
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


</script>

<div id="conditionLayout" class="easyui-layout"
		style="width: 800px;">
		
		   <div data-options="region:'north',title:''"
			style="padding: 2px; background: #eee;height: 30px;border: 0">
		         <div >
		            <a onclick="addFun();" href="javascript:void(0);"
				      class="easyui-linkbutton"
				      data-options="plain:true,iconCls:'font_add'">添加自定义动作</a>
	             </div>
		    </div>
		
          <div data-options="region:'center',title:''"
			style="padding: 5px; background: #eee;">
			<table id="dataGrid"></table>
		  </div>
</div>
	
	<div id="dd"></div>
	<div id="dd1"></div>
	