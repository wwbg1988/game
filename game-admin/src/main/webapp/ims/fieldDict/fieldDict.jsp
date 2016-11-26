<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if
	test="${fn:contains(sessionInfo.resourceList, '/fieldDictController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>

<c:if
	test="${fn:contains(sessionInfo.resourceList, '/fieldDictController/fieldDictAddPage')}">
	<script type="text/javascript">
		$.canAddfieldDictPage = true;
	</script>
</c:if>


<script type="text/javascript">
var field_Id = '${fieldId}';
var dataGrid;
$(function() {
	parent.$.messager.progress('close');

	$('#dictLayout').layout({
		fit : true
	});
	
	dataGrid = $('#dataGrid')
	.datagrid(
			{
				url : '${pageContext.request.contextPath}/fieldDictController/dataGrid?fieldId='+field_Id,
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
				singleSelect:true,
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
					width : 120
				}, {
					field : 'fieldDesc',
					title : '字段显示描述 ',
					width :150
				}, {
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
						var str = '';
					    if ($.canDelete) {
							str = $.formatString('<img onclick="deleteFormFieldFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
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


function addDictFun() {

	   $('#dict').dialog({
		    title: '查看字典列表',
		    width:730,
		    height:'auto',
		    closed: false,
		    cache: false,
		    href:'${pageContext.request.contextPath}/fieldDictController/fieldDictAddPage?fieldId='+field_Id,
		    modal: true
		});
	  
	
}

function deleteFormFieldFun(id){
	if (id == undefined) {//点击右键菜单才会触发这个
		var rows = dataGrid.datagrid('getSelections');
		id = rows[0].id;
	} else {//点击操作里面的删除图标会触发这个
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
	}
	parent.$.messager.confirm('询问', '您是否要删除当前字段字典？', function(b) {
		if (b) {
			var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
			if (currentUserId != id) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/fieldDictController/delete', {
					fieldId : field_Id,
					dictId :id
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
		style="width: 730px; height: 300px;">
		
		   <div data-options="region:'north',title:''"
			style="padding: 2px; background: #eee;height: 30px;border: 0">
		         <div >
		         <c:if test="${fn:contains(sessionInfo.resourceList, '/fieldDictController/fieldDictAddPage')}">
		            <a onclick="addDictFun();" href="javascript:void(0);"
				      class="easyui-linkbutton"
				      data-options="plain:true,iconCls:'pencil_add'">添加已有字典信息</a>
			     </c:if>
	             </div>
		    </div>
		
          <div data-options="region:'center',title:''"
			style="padding: 5px; background: #eee;">
			<table id="dataGrid"></table>
		  </div>
</div>
	
	<div id="dict"></div>