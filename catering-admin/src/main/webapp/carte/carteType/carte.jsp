<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if
	test="${fn:contains(sessionInfo.resourceList, '/carteController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>

<c:if
	test="${fn:contains(sessionInfo.resourceList, '/carteController/addPage')}">
	<script type="text/javascript">
		$.canAddPage = true;
	</script>
</c:if>


<script type="text/javascript">
var carteTypeId = '${carteTypeId}';
var dataGrid;
$(function() {
	parent.$.messager.progress('close');

	$('#dictLayout').layout({
		fit : true
	});
	
	dataGrid = $('#dataGrid')
	.datagrid(
			{
				url : '${pageContext.request.contextPath}/carteController/dataGrid?carteTypeId='+carteTypeId,
				fit : true,
				fitColumns : true,
				border : false,
				pagination : true,
				model:true,
				idField : 'id',
				pageSize : 10,
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
					field : 'carteName',
					title : '菜品名称',
					width : 120
				}, 
				{
					field : 'sustenance',
					title : '营养',
					width : 100
				},
				{
					field : 'calorie',
					title : '热量',
					width : 100
				},{
					field : 'carteWeek',
					title : '菜品周期 ',
					width :150,
				    formatter:function(value,row,index){  
				    return "第"+value+"周";	
				    }
				}, {
					field : 'img',
					title : '图片地址',
					width :150
				},
				{
					field : 'carteTypeName',
					title : '菜品类型',
					width : 120
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
					width : 140,
					formatter : function(value, row, index) {
						var str = '';
					    if ($.canDelete) {
							str = $.formatString('<img onclick="deleteCarteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
						}
					    str += '&nbsp;&nbsp;&nbsp;';  
						str += $.formatString('<img onclick="editCarteFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					    
					    str += '&nbsp;&nbsp;&nbsp;';  
						str += $.formatString('<img onclick="upLoadFun(\'{0}\');" src="{1}" title="上传列表图片"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/image_add.png');
						
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


function addCarteFun() {

	   $('#carte').dialog({
		    title: '菜品excel导入',
		    width:350,
		    height:150,
		    closed: false,
		    cache: false,
		    href:'${pageContext.request.contextPath}/carteController/addPage?carteTypeId='+carteTypeId,
		    modal: true
		});
	  
	
}

//编辑菜品
function editCarteFun(id){
	if (id == undefined) {
		var rows = dataGrid.datagrid('getSelections');
		id = rows[0].id;
	} else {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
	}
	$("#carteEdit").dialog({
		title : '编辑菜品',
		width : 400,
		height :290,
		modal: true,  
		href : '${pageContext.request.contextPath}/carteController/editPage?id=' + id,
		buttons : [ {
			text : '编辑',
			handler : function() {
			    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
				var f = $("#carteEdit").find('#form');
				f.submit();
			}
		} ]
	});
}


function deleteCarteFun(id){
	if (id == undefined) {//点击右键菜单才会触发这个
		var rows = dataGrid.datagrid('getSelections');
		id = rows[0].id;
	} else {//点击操作里面的删除图标会触发这个
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
	}
	parent.$.messager.confirm('询问', '您是否要删除当前菜品？', function(b) {
		if (b) {
			var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
			if (currentUserId != id) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/carteController/delete', {
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


function upLoadFun(id) {
	if (id == undefined) {
		var rows = dataGrid.datagrid('getSelections');
		id = rows[0].id;
	} else {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
	}
	$('#ddd').dialog({
		title : '上传图片',
		width : 700,
		height : 300,
		modal: true,  
		href : '${pageContext.request.contextPath}/carteController/upLoadImage?id=' + id,
		buttons : [ {
			text : '上传',
			handler : function() {
				var form=$('#ddd').find("#form");
				form.submit();
				parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					}
		} ]
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
	<div id="ddd"></div>  
	<div id="carte"></div>
	<div id="carteEdit"></div>