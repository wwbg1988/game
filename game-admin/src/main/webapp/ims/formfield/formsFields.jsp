<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if
	test="${fn:contains(sessionInfo.resourceList, '/fieldsController/formFieldDelete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
	
</c:if>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/fieldsController/formFieldAddPage')}">
	<script type="text/javascript">
		$.canRefrence = true;
	</script>
</c:if>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/fieldsController/clearRefrenceField')}">
	<script type="text/javascript">
		$.canClearRefrence = true;
	</script>
</c:if>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/fieldsController/addPage')}">
	<script type="text/javascript">
		$.canAddField = true;
	</script>
</c:if>

<script type="text/javascript">
var form_Id = '${formId}';
var proj_Id = '${projId}';
var proc_Id = '${procId}';
var dataGrid;
$(function() {
	parent.$.messager.progress('close');

	$('#fieldLayout').layout({
		fit : true
	});
	
	dataGrid = $('#dataGrid')
	.datagrid(
			{
				url : '${pageContext.request.contextPath}/fieldsController/formFieldDataGrid?formId='+form_Id,
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
					width : 150,
					hidden : true
				} ] ],
				columns : [ [{
					field : 'paramDesc',
					title : '参数描述',
					width : 190
				}, {
					field : 'paramName',
					title : '参数名称 ',
					width :170
				},  {
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
					
				}, /*  {
					field : 'lastUpdateTime',
					title : '修改时间',
					width : 150,
			        formatter:function(value,row,index){  
			        	if(value!=null){
		                      var unixTimestamp = new Date(value);  
		                      return unixTimestamp.toLocaleString();  
		                      } else{
		                    	  return "";
		                      }
	                      }  
					
				}, */{
					field : 'pattern',
					title : '正则校验',
					width : 180
				},{
					field : 'isencrypt',
					title : '是否加密',
					width : 170,   
					formatter:function(value,row,index){  
	                   if(value==1){
	                	   return "是";
	                   }else if(value==0){
	                	   return "否";  
	                   }
	                    } 
				},{
					field : 'isuniline',
					title : '独占一行',
					width : 170,
					formatter:function(value,row,index){  
		                   if(value==0){
		                	   return "否";
		                   }else if(value==1){
		                	   return "是";  
		                   }
		                    } 
				},{
					field : 'isdiy',
					title : '是否自定义',
					width : 210,
					formatter:function(value,row,index){  
		                   if(value==0){
		                	   return "否";
		                   }else if(value==1){
		                	   return "是";  
		                   }
		                    } 
				}, {
					field : 'length',
					title : '长度',
					width : 120
				},{
					field : 'height',
					title : '高度',
					width : 120
				},{
					field : 'type',
					title : '字段类型',
					width : 175,
					formatter:function(value,row,index){  
		                   if(value==1){
		                	   return "text";
		                   }else if(value==2){
		                	   return "checkbox";  
		                   }else if(value==3){
		                	   return "hidden";  
		                   }else if(value==4){
		                	   return "img";  
		                   }else if(value==5){
		                	   return "password";  
		                   }else if(value==6){
		                	   return "radio";  
		                   }else if(value==7){
		                	   return "reset";  
		                   }else if(value==8){
		                	   return "file";  
		                   }else if(value==9){
		                	   return "label";  
		                   }else if(value==10){
		                	   return "select";  
		                   }else if(value==11){
		                	   return "textarea";  
		                   }else{
		                	   return "无";
		                   }
		                    }
				},{
					field : 'dataType',
					title : '数据类型',
					width : 175,
					formatter:function(value,row,index){  
		                   if(value==1){
		                	   return "int";
		                   }else if(value==2){
		                	   return "string";  
		                   }else if(value==3){
		                	   return "float";  
		                   }else if(value==4){
		                	   return "date";  
		                   }else{
		                	   return "无";
		                   }
		                    }
				},{
					field : 'isneed',
					title : '是否必填',
					width : 170,
					formatter:function(value,row,index){  
		                   if(value==0){
		                	   return "否";
		                   }else if(value==1){
		                	   return "是";  
		                   }
		                    } 
				},{
					field : 'refrenceFormName',
					title : '引用表单',
					width : 170
				},{
					field : 'refrenceParamDesc',
					title : '引用参数',
					width : 170
				},{
					field : 'action',
					title : '操作',
					width : 430,
					formatter : function(value, row, index) {
						var str = '';
					    if ($.canDelete) {
							str = $.formatString('<img onclick="deleteFormFieldFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
						}
						//str += '&nbsp;';
							//str += $.formatString('<img onclick="grantFun(\'{0}\');" src="{1}" title="字段写入授权"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/key.png');
							
					//	str += '&nbsp;';
							//str += $.formatString('<img onclick="grantReadFun(\'{0}\');" src="{1}" title="字段读取授权"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/key.png');
							
							str += '&nbsp;';
							str += $.formatString('<img onclick="grantUWriterFun(\'{0}\');" src="{1}" title="用户写入权限"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/key.png');	
							
							str += '&nbsp;';
							str += $.formatString('<img onclick="grantUReadFun(\'{0}\');" src="{1}" title="用户读取权限"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/key.png');			
                       
                       
						if($.canRefrence){
                        	str += '&nbsp;&nbsp;';
							str += $.formatString('<img onclick="addExistsFun(\'{0}\');" src="{1}" title="引用已有字段"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/arrow/arrow_redo.png');
                        }
						
						if($.canClearRefrence){
							str += '&nbsp;&nbsp;';
							str += $.formatString('<img onclick="clearExistsFun(\'{0}\');" src="{1}" title="清除引用字段"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/arrow/cross.png');
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
		    title: '添加新字段',
		    width:500,
		    height:350,
		    closed: false,
		    cache: false,
		    href:'${pageContext.request.contextPath}/fieldsController/addPage?formId='+form_Id+'&projId='+proj_Id+'&procId='+proc_Id+'',
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



function addExistsFun(fieldId) {

	   $('#existsFieldAdd').dialog({
		    title: '引用已有字段',
		    width:630,
		    height:340,
		    closed: false,
		    cache: false,
		    href:'${pageContext.request.contextPath}/fieldsController/formFieldAddPage?formId='+form_Id+'&fieldId='+fieldId,
		    modal: true
		});
	  }


function clearExistsFun(id){
	if (id == undefined) {//点击右键菜单才会触发这个
		var rows = dataGrid.datagrid('getSelections');
		id = rows[0].id;
	} else {//点击操作里面的删除图标会触发这个
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
	}
	parent.$.messager.confirm('询问', '您是否要清除引用字段信息？', function(b) {
		if (b) {
			var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
			if (currentUserId != id) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/fieldsController/clearRefrenceField', {
					fieldId : id
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
function grantReadFun(id){
	 $('#dd3').dialog({
		    title: '读取授权',
		    width:575,
		    height:200,
		    closed: false,
		    cache: false,
		    href : '${pageContext.request.contextPath}/fieldsController/grantReadPage?formId=' +form_Id+'&fieldId='+id,
		    		  // href:'${pageContext.request.contextPath}/formsFieldsController/formFieldAddPage?formId='+form_Id,
		    				  /*buttons : [ {
				text : '授权',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ],*/
		    buttons : [ {
				text : '授权',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
					//var f = parent.$.modalDialog.handler.find('#form');
					var f =$("#dd3").find('#readForm');
					f.submit();
				}
			} ],
		    modal: true
		});
	
}

function grantFun(id) {
	   $('#dd2').dialog({
		    title: '写入权限',
		    width:575,
		    height:200,
		    closed: false,
		    cache: false,
		    href : '${pageContext.request.contextPath}/fieldsController/grantPage?formId=' +form_Id+'&fieldId='+id,
		    		  // href:'${pageContext.request.contextPath}/formsFieldsController/formFieldAddPage?formId='+form_Id,
		    				  /*buttons : [ {
				text : '授权',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ],*/
		    buttons : [ {
				text : '授权',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
					//var f = parent.$.modalDialog.handler.find('#form');
					var f =$("#dd2").find('#writerForm');
					f.submit();
				}
			} ],
		    modal: true
		});
	
	
}

function grantUWriterFun(id) {
	   $('#dd4').dialog({
		    title: '用户写入权限',
		    width:550,
		    height:500,
		    closed: false,
		    cache: false,
		    href : '${pageContext.request.contextPath}/fieldsController/grantUsersWriter?formId=' +form_Id+'&fieldId='+id,
		    		
		    		
		    		  // href:'${pageContext.request.contextPath}/formsFieldsController/formFieldAddPage?formId='+form_Id,
		    buttons : [ {
				text : '授权',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
					//var f = parent.$.modalDialog.handler.find('#form');
					var a=$("#dd4").find('#userForm');
					a.submit();
					var f =$("#dd4").find('#writerForm');
					f.submit();
				}
			} ],
		    modal: true
		});
	
	
}

function grantUReadFun(id) {
	   $('#dd5').dialog({
		    title: '用户读取权限',
		    width:800,
		    height:600,
		    closed: false,
		    cache: false,
		    href : '${pageContext.request.contextPath}/fieldsController/grantUserReadPage?formId=' +form_Id+'&fieldId='+id,
		    		
		    		
		    		  // href:'${pageContext.request.contextPath}/formsFieldsController/formFieldAddPage?formId='+form_Id,
		    buttons : [ {
				text : '授权',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
					var f =$("#dd5").find('#forms');
					f.submit();
					var a =$("#dd5").find('#roleForm');
					a.submit();
				}
			} ],
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
	parent.$.messager.confirm('询问', '您是否要删除当前表单字段？', function(b) {
		if (b) {
			var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
			if (currentUserId != id) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/fieldsController/formFieldDelete', {
					fieldId : id,
					 formId :form_Id
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

</script>

<div id="fieldLayout" class="easyui-layout"
		style="width: 1100px;">
		
		   <div data-options="region:'north',title:''"
			style="padding: 2px; background: #eee;height: 30px;border: 0">
		         <div >
		         	<c:if test="${fn:contains(sessionInfo.resourceList, '/fieldsController/addPage')}">
		            <a onclick="addFun();" href="javascript:void(0);"
				      class="easyui-linkbutton"
				      data-options="plain:true,iconCls:'font_add'">添加新字段</a>
				   </c:if>
	             </div>
		    </div>
		
          <div data-options="region:'center',title:''"
			style="padding: 5px; background: #eee;">
			<table id="dataGrid"></table>
		  </div>
</div>
	
	<div id="dd"></div>
	<div id="existsFieldAdd" style="display: block;" ></div>
	<div id="dd2"></div>
	<div id="dd3"></div>
	<div id="dd4"></div>
	<div id="dd5"></div>