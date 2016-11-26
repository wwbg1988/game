<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>字段管理</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/fieldsController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/fieldsController/editPage')}">
	<script type="text/javascript">
		$.canGrant = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/fieldsController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>

<c:if test="${fn:contains(sessionInfo.resourceList, '/fieldDictController/manager')}">
	<script type="text/javascript">
		$.canSearchfieldDict = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	//parent.$.messager.progress('close');
	$(function() {
	
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/fieldsController/dataGrid',
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
			singleSelect:true,
			selectOnCheck : false,
			nowrap : false,
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				checkbox : true
			} ] ],
			columns : [ [{
				field : 'paramDesc',
				title : '参数描述',
				width : 130
			}, {
				field : 'paramName',
				title : '参数名称 ',
				width :130
			}, {
				field : 'createTime',
				title : '创建时间',
				width : 150,
		        formatter:function(value,row,index){  
                      var unixTimestamp = new Date(value);  
                      return unixTimestamp.toLocaleString();  
                      }  
				
			}, {
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
				
			},{
				field : 'pattern',
				title : '正则校验',
				width : 150
			},{
				field : 'isencrypt',
				title : '是否加密',
				width : 130,   
				formatter:function(value,row,index){  
                   if(value==true){
                	   return "是";
                   }else if(value==false){
                	   return "否";  
                   }
                    } 
			},{
				field : 'isuniline',
				title : '独占一行',
				width : 130,
				formatter:function(value,row,index){  
					   if(value==true){
	                	  return "是";
	                   }else if(value==false){
	                	   return "否";  
	                   }
	                    } 
			},{
				field : 'isdiy',
				title : '自定义字段',
				width : 150,
				formatter:function(value,row,index){  
				
	                   if(value==false){
	                	   return "否";
	                   }else if(value==true){
	                	   return "是";  
	                   }
	                    } 
			}, {
				field : 'length',
				title : '长度',
				width : 80
			},{
				field : 'height',
				title : '高度',
				width : 80
			},{
				field : 'type',
				title : '字段类型',
				width : 130,
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
				width : 130,
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
				width : 130,
				formatter:function(value,row,index){  
	                   if(value==false){
	                	   return "否";
	                   }else if(value==true){
	                	   return "是";  
	                   }
	                    } 
			},{
				field : 'formName',
				title : '所属表单',
				width : 140
			},{
				field : 'action',
				title : '操作',
				width : 180,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canEdit) {
						str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					}
					
					str += '&nbsp;&nbsp;&nbsp;';
					if ($.canDelete) {
						str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					}
					str += '&nbsp;&nbsp;&nbsp;';
					
					if($.canSearchfieldDict){
					str += $.formatString('<img onclick="searchDictFun(\'{0}\',\'{1}\');" src="{2}" title="查找对应字典"/>', row.id,row.type, '${pageContext.request.contextPath}/style/images/extjs_icons/search.png');
					}
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				
				parent.$.messager.progress('close');

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


	function searchDictFun(id,type){
		
	    if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		
		if(type==2||type==6||type==10){
		
		parent.$.modalDialog({
			title : '字段字典信息',
			width : 730,
			height :'auto',
			draggable : true, //拖拽操作
			closable : true,
			href : '${pageContext.request.contextPath}/fieldDictController/manager?id=' +id
		
		});
	  }else{
			parent.$.messager.alert('提示',"只有单选框(radio),复选框(checkbox),下拉框(select)才可以添加字典信息!", 'info');
		 
	  }
		
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
					$.post('${pageContext.request.contextPath}/fieldsController/delete', {
						id : id
					}, function(result) {
						if (result.success) {
							   parent.$.messager.show({
									title : '提示',
									msg : result.msg
								});
							dataGrid.datagrid('reload');
						}else{
							   parent.$.messager.show({
									title : '提示',
									msg : result.msg
								});
							
						}
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



	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑字段',
			width : 500,
			height : 400,
			href : '${pageContext.request.contextPath}/fieldsController/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
				    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加字段',
			width : 500,
			height : 400,
			href : '${pageContext.request.contextPath}/fieldsController/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function grantFun(id) {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		parent.$.modalDialog({
			title : '用户授权',
			width : 500,
			height : 300,
			href : '${pageContext.request.contextPath}/fieldsController/grantPage?ids=' + id,
			buttons : [ {
				text : '授权',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}


	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
	dataGrid.datagrid('load', {});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height:165px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
						<th>参数描述</th>
						<td><input name="paramDesc" placeholder="输入参数描述" class="span2" /></td>
						<th>参数名称</th>
						<td><input name="paramName" placeholder="输入参数名称" class="span2" /></td>
							<th>是否加密</th>
						  <td>
						 <select  name="isencrypt" class="easyui-combobox" style="width:100px;" data-options="width:140,height:29,editable:false,panelHeight:55">
					          <option value="0">否</option>
					          <option value="1">是</option>
					 
					   </select>
					   </td>
					</tr>
					<tr>
						<th>字段类型</th>
					   <td>
					   <select  name="type" class="easyui-combobox" style="width:100px;" data-options="width:140,height:29,editable:false,panelHeight:150">
					      <option value="1">text</option>
					      <option value="2">checkbox</option>
					      <option value="3">hidden</option>
					      <option value="4">img</option>
					      <option value="5">password</option>
					      <option value="6">radio</option>
					      <option value="7">reset</option>
					      <option value="8">file</option>
					      <option value="9">label</option>
					      <option value="10">select</option>
					      <option value="11">textarea</option>
					   </select>
					   </td>
					<th>是否独占一行</th>
					   <td>
					   <select  name="isuniline" class="easyui-combobox" style="width:100px;" data-options="width:140,height:29,editable:false,panelHeight:55">
					     <option value="1">是</option>
					      <option value="0">否</option>
					   </select>
					   </td>
					   	<th>是否必填</th>
						  <td>
						 <select  name="isneed" class="easyui-combobox" style="width:100px;" data-options="width:140,height:29,editable:false,panelHeight:55">
					     <option value="1">是</option>
					      <option value="0">否</option>
					   </select>
					   </td>
					</tr>
						<tr>
						<th>数据类型</th>
						  <td>
						 <select  name="dataType" class="easyui-combobox" style="width:100px;" data-options="width:140,height:29,editable:false,panelHeight:105">
					      <option value="1">int</option>
					      <option value="2">string</option>
					      <option value="3">float</option>
					      <option value="4">date</option>
					   </select>
					   </td>
				
					 <th>所属表单</th>
					   <td>
					 <select id="forms" name="formId" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'120'"  >
					
						<c:forEach items="${forms}" var="de">
								<option value="${de.id}" >${de.name}</option>
							</c:forEach>
					</select>
					   </td>
					<th></th>
					<td></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<%-- <c:if test="${fn:contains(sessionInfo.resourceList, '/fieldsController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
		</c:if>
		 --%>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	</div>

	<div id="menu" class="easyui-menu" style="width: 120px; display: none;">
	
		<c:if test="${fn:contains(sessionInfo.resourceList, '/fieldsController/delete')}">
			<div onclick="deleteFun();" data-options="iconCls:'pencil_delete'">删除</div>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/fieldsController/editPage')}">
			<div onclick="editFun();" data-options="iconCls:'pencil'">编辑</div>
		</c:if>
	</div>
</body>
</html>