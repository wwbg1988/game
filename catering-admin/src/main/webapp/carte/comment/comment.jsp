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
				url : '${pageContext.request.contextPath}/commentController/dataGrid?caftrotiumId='+caftrotiumId,
				fit : true,
				fitColumns : true,
				border : false,
				pagination : true,
				idField : 'id',
				pageSize : 30,
				groupField:"groupTime",
				showGroup:true,
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
				columns : [ [{
					field : 'comment',
					title : '评论内容',
					width : 220
				}, {
					field : 'cafeName',
					title : '所属食堂 ',
					width :150
				},
				{
					field : 'sensitiveName',
					title : '触发敏感词 ',
					width :150,
				    formatter:function(value,row,index){  
				    	return "<font color='green'>"+value+"</font>";
				    }
				},
				{
					field : 'createTime',
					title : '创建时间',
					width : 140,
			        formatter:function(value,row,index){  
			        	if(value!=null){
		                      var unixTimestamp = new Date(value);  
		                      return unixTimestamp.toLocaleString();  
		                      } else{
		                    	  return "";
		                      } 
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
					width : 80,
					formatter : function(value, row, index) {
					
							str = $.formatString('<img onclick="deleteCarteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
						   return str;
					}
				} ] ],
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




function deleteCarteFun(id){
	if (id == undefined) {//点击右键菜单才会触发这个
		var rows = dataGrid.datagrid('getSelections');
		id = rows[0].id;
	} else {//点击操作里面的删除图标会触发这个
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
	}
	parent.$.messager.confirm('询问', '您是否要删除当前评论？', function(b) {
		if (b) {
			var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
			if (currentUserId != id) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/commentController/delete', {
				id:id
				}, function(result) {
					if (result.success) {
						   parent.$.messager.show({
								title : '提示',
								msg : result.msg
							});
					}
					dataGrid.datagrid('reload');
					parent.$.messager.progress('close');
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
	
