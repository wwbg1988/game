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
	test="${fn:contains(sessionInfo.resourceList, '/queryConditionController/editPage')}">
	<script type="text/javascript">
		$.canEdit= true;
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
				url : '${pageContext.request.contextPath}/queryConditionController/dataGrid?queryId='+query_Id,
				fit : true,
				fitColumns : true,
				border : false,
				pagination : true,
				idField : 'id',
				pageSize : 10,
				singleSelect:true,
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
					field : 'name',
					title : '过滤名称',
					width : 190
				},  {
					field : 'fieldParamDesc',
					title : '字段描述 ',
					width :150
				}, {
					field : 'fieldParamName',
					title : '字段参数 ',
					width :170
				},  
				{
					field : 'opt',
					title : '过滤条件',
					width :190,	
					formatter : function(value, row, index) {
						
						if(value==1){
							return "<font style='word-wrap:break-word;white-space:normal;'>EQ</font>";
						}
						else if(value==2){
							return "LT";
						}
						else if(value==3){
							return "LTE";
						}
						else if(value==4){
							return "GT";
						}
						else if(value==5){
							return "GTE";
						}
						else if(value==6){
							return "LIKE";
						}
						else if(value==7){
							return "<font style='word-wrap:break-word;white-space:normal;'>ORDERBY_DESC</font>";
						}
						else if(value==8){
							return "<font style='word-wrap:break-word;white-space:normal;'>ORDERBY_ASC</font>";
						}
					}
				},
				{
					field : 'serialNum',
					title : '排序号 ',
					width :120
				},
				{
					field : 'createTime',
					title : '创建时间',
					width : 200,
			        formatter:function(value,row,index){  
			        	if(value!=null){
		                      var unixTimestamp = new Date(value);  
		                      return unixTimestamp.toLocaleString();  
		                      } else{
		                    	  return "";
		                      } 
	                      }  
					
				},
				{
					field : 'queryName',
					title : '所属查询 ',
					width :170
				},
				{
					field : 'projName',
					title : '所属项目 ',
					width :170
				},{
					field : 'action',
					title : '操作',
					width : 160,
					formatter : function(value, row, index) {
						var str = '';
					    if ($.canEdit) {
					        str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					    }
							str += '&nbsp;&nbsp;&nbsp;';
					    if ($.canDelete) {
							str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
						}
					
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
		    title: '添加过滤字段',
		    width:400,
		    height:350,
		    closed: false,
		    cache: false,
		    href:'${pageContext.request.contextPath}/queryConditionController/addPage?queryId='+query_Id,
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

	if (id == undefined) {
		var rows = dataGrid.datagrid('getSelections');
		if(rows &&  rows[0] && rows[0].id){
		id = rows[0].id;
		}else{
			parent.$.messager.alert('提示','请选择要编辑的列,亲!', 'info');
			return false;
		}
	} else {
		
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
	}
	
	   $('#editDialog').dialog({
		title : '编辑过滤字段',
		width : 450,
		height : 260,
		modal: true,
		href : '${pageContext.request.contextPath}/queryConditionController/editPage?id=' + id,
		buttons : [ {
			text : '编辑',
			handler : function() {
			    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
			    var f = $('#editDialog').find("#form");
				f.submit();
			}
		} ]
	});
}


function deleteFun(id){
	if (id == undefined) {//点击右键菜单才会触发这个
		var rows = dataGrid.datagrid('getSelections');
		if(rows &&  rows[0] && rows[0].id){
			id = rows[0].id;
			}else{
				parent.$.messager.alert('提示','请选择要删除的列,亲!', 'info');
				return false;
			}
	} else {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
	}
	parent.$.messager.confirm('询问', '您是否要删除当前过滤字段？', function(b) {
		if (b) {
			var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
			if (currentUserId != id) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				
				$.post('${pageContext.request.contextPath}/queryConditionController/delete', {
					
				      queryConditionId:id
			   }, function(result) {
					if (result.success) {
						   parent.$.messager.show({
								title : '提示',
								msg : result.msg
							});
					}else{
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
 function refreshFun(){
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll'); 
	 
 }
</script>

<div id="conditionLayout" class="easyui-layout"
		style="width: 697px;height: 336px;">
		
		   <div data-options="region:'north',title:''"
			style="padding: 2px; background: #eee;height: 30px;border: 0">
		         <div >
		         
				   <a href="javascript:void(0)" id="mb" class="easyui-menubutton"     
                    data-options="menu:'#mm',plain:true,iconCls:'font_add'">操作</a>  
	             </div>
		    </div>
		
          <div data-options="region:'center',title:''"
			style="padding: 5px; background: #eee;">
			<table id="dataGrid"></table>
		  </div>
</div>
	
	<div id="dd"></div>
	<div id="editDialog"></div>
	<div id="mm" style="width:150px;">   
  
    <c:if test="${fn:contains(sessionInfo.resourceList, '/queryConditionController/addPage')}">
     <div data-options="iconCls:'pencil_add'" onclick="addFun()">添加</div>  
	</c:if>
   
    <div class="menu-sep"></div>   
    <div data-options="iconCls:'pencil'" onclick="editFun()" >编辑</div>   
    <div class="menu-sep"></div>   
    <div data-options="iconCls:'pencil_delete'" onclick="deleteFun()">删除</div>   
    <div class="menu-sep"></div>   
    <div data-options="iconCls:'database_refresh'" onclick="refreshFun()">刷新表单</div>   
</div> 
	