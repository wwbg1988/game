<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<script type="text/javascript">
var caftrotiumId = '${caftrotiumId}';
var dataGrid;
$(function() {
	parent.$.messager.progress('close');

	$('#dictLayout').layout({
		fit : true
	});
	
	dataGrid = $('#dataGrid')
	.propertygrid(
			{
				url : '${pageContext.request.contextPath}/configScoreController/dataGrid?caftrotiumId='+caftrotiumId,
				fit : true,
				fitColumns : true,
			    groupField:"configName",
				groupField:"groupTime",
				showGroup:true,
				border : false,
				pagination : true,
				idField : 'id',
				rownumbers : true,
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
					width : 80,
					hidden : true
				} ] ],
				columns : [ [	{
					field : 'configName',
					title : '评论项 ',
					width :180,
				    formatter:function(value,row,index){  
				    return value;
				    }
				}, {
					field : 'score',
					title : '配置项分数',
					width : 150,
					formatter:function(value,row,index){  
					    	return "<font color='green'>"+value+"</font>";
					    }
				}, {
					field : 'cafeName',
					title : '所属食堂 ',
					width :180
				},
			 	{
					field : 'createTime',
					title : '创建时间',
					width : 170,
			        formatter:function(value,row,index){  
			        	if(value!=null){
		                      var unixTimestamp = new Date(value);  
		                      return unixTimestamp.toLocaleString();  
		                      } else{
		                    	  return "";
		                      } 
	                      }  
					
				}/* ,{
					field : 'action',
					title : '操作',
					width : 100,
					formatter : function(value, row, index) {
					
							str = $.formatString('<img onclick="deleteCarteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
						   return str;
					}
				} */ ] ],
				groupFormatter:function(group,rows){
					return  '- <span style="color:green">'+group+' </span>' +' - <span style="color:red">  total:'+rows.length+'</span>'
				},
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





</script>

<div id="dictLayout" class="easyui-layout"
		style=" ">
		
		<!--    <div data-options="region:'north',title:''"
			style="padding: 2px; background: #eee;height: 30px;border: 0">
		      
		    </div> -->
		
          <div data-options="region:'center',title:''"
			style="padding: 5px; background: #eee;">
			<table id="dataGrid"></table>
		  </div>
</div>
	
