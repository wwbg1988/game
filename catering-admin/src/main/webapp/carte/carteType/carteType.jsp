<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%   
String imgHeader = null;
if(request.getContextPath() == null || request.getContextPath() == "")
{
    imgHeader = "../upload/";
}
else
{
    imgHeader = "../../upload/";
}

%> 
<title>菜品类型管理</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/carteTypeController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/carteTypeController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>

<script type="text/javascript">
	var dataGrid;
	parent.$.messager.progress('close');
	$(function() {
		dataGrid = $('#dataGrid').propertygrid({
			url : '${pageContext.request.contextPath}/carteTypeController/dataGrid',
			fit : true,
			fitColumns : true,
			border : true,
			pagination : true,
			striped:true,
			idField : 'id',
			pageSize : 50,
			//rownumbers : true,
			groupField:"cafetoriumName",
			showGroup:true,
			pageList : [ 50 ,100,200,300],
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
				field : 'carteTypeName',
				title : '菜品类型 ',
				width :130,
				formatter : function(value, row, index) {
					return '<font style="font-style: normal;font-weight: bolder;">'
							+ value + '</font>';
				}
			} , {
				field : 'upperLimit',
				title : '菜品数量限制',
				width : 100,
				formatter : function(value, row, index) {
					return '<font style="font-style: normal;font-weight: bolder;">'
							+ value + '</font>';
				}
			},  {
				field : 'icon',
				title : '菜品类型默认图片',
				width : 150,
				align:'center',
				formatter : function(value, row, index) {
					  if(row.icon==null||row.icon==""){
			        	   return "";
			           }else{
			        	   return "<img src='<%=imgHeader%>"+row.icon+"' />";
			           }
				}
			}, {
				field : 'icon2',
				title : '菜品类型选中图片',
				width : 150,
				align:'center',
				formatter : function(value, row, index) {
					  if(row.icon==null||row.icon==""){
			        	   return "";
			           }else{
			        	   return "<img src='<%=imgHeader%>"+row.icon2+"' />";
			           }
				}
			},
			
			{
				field : 'cafetoriumName',
				title : '所属食堂',
				width : 120,
				formatter : function(value, row, index) {
					if (value != null) {
						return "<font style='word-wrap:break-word;white-space:normal;font-style: normal;font-weight: bolder;'>"
								+ value + "</font>";
					} else {
						return "";
					}
				}
			},{
				field : 'projName',
				title : '所属项目',
				width : 120,
				formatter : function(value, row, index) {
					if (value != null) {
						return "<font style='word-wrap:break-word;white-space:normal;font-style: normal;font-weight: bolder;'>"
								+ value + "</font>";
					} else {
						return "";
					}
				}
			},	{
				field : 'createTime',
				title : '创建时间',
				width : 150,
				formatter : function(value, row, index) {
				   	if(value!=null){
	                      var unixTimestamp = new Date(value);  
	                  	return '<font style="font-style: normal;font-weight: bolder;">'
						+ (unixTimestamp
								.toLocaleString())
						+ '</font>'; 
	                      } else{
	                    	  return "";
	                      }
				} 
				
			}, {
				field : 'lastUpdateTime',
				title : '修改时间',
				width : 150,
		        formatter:function(value,row,index){  
		        	if(value!=null){
	                      var unixTimestamp = new Date(value);  
	                  	return '<font style="font-style: normal;font-weight: bolder;">'
						+ (unixTimestamp
								.toLocaleString())
						+ '</font>'; 
	                      } else{
	                    	  return "";
	                      }
                      }  
				
			},{
				field : 'action',
				title : '操作',
				width : 130,
				formatter : function(value, row, index) {
					var str = '&nbsp;&nbsp;';
					if ($.canEdit) {
						str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					}
					str += '&nbsp;&nbsp;&nbsp;';
					if ($.canDelete) {
						str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					}
					
					str += '&nbsp;&nbsp;&nbsp;';
						str += $.formatString('<img onclick="searchCarte(\'{0}\',\'{1}\');" src="{2}" title="查看菜品"/>', row.id,row.carteTypeName, '${pageContext.request.contextPath}/style/images/extjs_icons/zoom/zoom.png');
					str += '&nbsp;&nbsp;&nbsp;';  
					str += $.formatString('<img onclick="upLoadFun(\'{0}\');" src="{1}" title="上传列表图片"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/image_add.png');
						  
				
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');

			
			},
			groupFormatter:function(group,rows){
				return  '- <span style="color:green">'+group+' </span>'
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

	function searchCarte(id,name){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '['+name+']'+'拥有菜品信息',
			width : 710,
			height :400,
			draggable : true, //拖拽操作
			closable : true,
			href : '${pageContext.request.contextPath}/carteController/manager?carteTypeId='+id
		
		});
	}
	


	function deleteFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前菜单类型？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
				if (currentUserId != id) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/carteTypeController/delete', {
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


	function uploadCarteFun() {
		parent.$
		.modalDialog({
			title : '菜品excel导入',
			width : 300,
			height : 170,
			href : '${pageContext.request.contextPath}/carteController/addPage',
			buttons : [ {
				text : '导入',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
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
			title : '编辑菜品类型',
			width : 400,
			height :190,
			href : '${pageContext.request.contextPath}/carteTypeController/editPage?id=' + id,
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
			title : '添加菜品类型',
			width : 400,
			height :190,
			href : '${pageContext.request.contextPath}/carteTypeController/addPage',
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


	function upLoadFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '上传图片',
			width : 700,
			height : 300,
			href : '${pageContext.request.contextPath}/carteTypeController/upLoadImage?id=' + id,
			buttons : [ {
				text : '上传',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit ();
				}
			} ]
		});
	}



	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height:90px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
						<th>菜品类型</th>
						<td><input name="carteTypeName" placeholder="输入菜品类型" class="easyui-searchbox"  data-options="prompt:'输入菜品类型',width:170,height:29,editable:false,panelHeight:120"/>
				   
	               </td>
				       <th>所属食堂</th>
					   <td>
					   <select id="cafetoriumId" name="cafetoriumId" class="easyui-combobox" data-options="width:130,height:29,editable:false,panelHeight:120"  >
					    <c:forEach items="${cafeList}" var="de">
								<option value="${de.id}" >${de.cafeName}</option>
							</c:forEach>
					   </select>
					   </td>
			
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid" title="菜品类型表单" data-options="collapsible:true"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
	
			<a onclick="uploadCarteFun();" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'pencil_add'">上传菜品</a> 
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	</div>

	<div id="menu" class="easyui-menu" style="width: 120px; display: none;">

	   <c:if test="${fn:contains(sessionInfo.resourceList, '/carteTypeController/delete')}">
			<div onclick="deleteFun();" data-options="iconCls:'pencil_delete'">删除</div>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/carteTypeController/editPage')}">
			<div onclick="editFun();" data-options="iconCls:'pencil'">编辑</div>
		</c:if>
	</div>
</body>
</html>