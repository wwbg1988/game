<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">

var proInstanceId = '${id}';

var dataGrid;
$(function() {
	parent.$.messager.progress('close');

	$('#fieldLayout').layout({
		fit : true
	});
	
	
	dataGrid = $('#dataGrid')
	.datagrid(
			{
				url : '${pageContext.request.contextPath}/procInstanceController/dataGrid?procId='+proInstanceId,
				fit : true,
				fitColumns : true,
				border : false,
				pagination : true,
				idField : 'id',
				pageSize : 30,
				//pageList : [ 10, 20, 30, 40, 50 ],
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
					field : 'procName',
					title : '实例名称',
					width : 190
				}, {
					field : 'taskName',
					title : '当前任务节点 ',
					width :170
				},{
					field : 'createTime',
					title : '创建时间',
					width : 150,
			        formatter:function(value,row,index){  
	                      var unixTimestamp = new Date(value);  
	                      return unixTimestamp.toLocaleString();  
	                      }  
					
				}] ],
				//toolbar : '#toolbar',
				onLoadSuccess : function() {
				
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


</script>

<div id="fieldLayout" class="easyui-layout"
		style="width: 1100px;">
		
		
          <div data-options="region:'center',title:''"
			style="padding: 5px; background: #eee;">
			<table id="dataGrid"></table>
		  </div>
</div>
