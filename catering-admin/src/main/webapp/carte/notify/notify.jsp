<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>流程管理</title>
<jsp:include page="../../inc.jsp"></jsp:include>


<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/notifyController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			rownumbers : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'createdatetime',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 100,
				hidden : true
			} ] ],
			columns : [ [ {
				field : 'name',
				title : '名称',
				width : 70
			},{
				field : 'listTitle',
				title : '列表标题',
				width : 70
			},{
				field : 'listText',
				title : '列表内容',
				width : 70
			},{
				field : 'title',
				title : '正文标题',
				width : 70
			},{
				field : 'notifyTime',
				title : '公告发布时间',
				width : 150,
			      formatter:function(value,row,index){  
			    	  if(value!=null){
			    		  var unixTimestamp = new Date(value); 
			    		  return unixTimestamp.toLocaleString();  
			    	     }else{
			    		  return null;
			    	     }
                    } 
			},{
				field : 'state',
				title : '是否已经发布',
				width : 150,
				formatter:function(value,row,index){  
                    if(value==0){
                 	   return '未发布';
                    }else if(value==1){
                 	   return '已发布';
                    }
               }
			},
			{
				field : 'projectName',
				title : '项目',
				width : 80
			},			
			{
				field : 'action',
				title : '操作',
				width : 70,
				formatter : function(value, row, index) {
					var str = '';
				    str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
				    str += '&nbsp;&nbsp;&nbsp;';
					str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					str += '&nbsp;&nbsp;&nbsp;';		
				    str += $.formatString('<img onclick="publishFun(\'{0}\');" src="{1}" title="发布公告"/>', row.id,'${pageContext.request.contextPath}/style/images/extjs_icons/key.png');
				    str += '&nbsp;&nbsp;&nbsp;';
				    str += $.formatString('<img onclick="umeditFun(\'{0}\');" src="{1}" title="编辑正文"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/image_add.png');
				    str += '&nbsp;&nbsp;&nbsp;';
				    str += $.formatString('<img onclick="shwoUmFun(\'{0}\');" src="{1}" title="显示正文"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/image.png');
					
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');

				//$(this).datagrid('tooltip');
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

	
	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑公告',
			width : 700,
			height : 530,
			href : '${pageContext.request.contextPath}/notifyController/editnotify?id=' + id,
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
	
	function deleteFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前公告？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
				if (currentUserId != id) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/notifyController/deleteNotify', {
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
	
	function addFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 810,
			height : 500,
			href : '${pageContext.request.contextPath}/notifyController/addnotify',
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

	function publishFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要发布当前公告？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
				if (currentUserId != id) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/notifyController/publishNotify', {
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
	
	function umeditFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		
		if(!parent.$.modalDialog.handler) {
		parent.$.modalDialog({    
		    width:835,    
		    height:500,    
	
		    href:'${pageContext.request.contextPath}/notifyController/umeditFun?id=' + id ,
		    
		    buttons : [ {
				   text : '上传',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				    }
			     } ,{  text: '关闭',
			    handler : function(){
			    	parent.$.messager.progress('close');
			    	parent.$.modalDialog.handler.dialog('close');
			    }
			   }
			]
		});
		} else {
			parent.$.modalDialog.handler.dialog('open');
		}
	}
	
	function shwoUmFun(id){
		$('#ww').dialog({    
		    title: '编辑正文',    
		    width: 800,    
		    height: 600,    
		    closed: false,    
		    cache: false,    
		    href: '${pageContext.request.contextPath}/notifyController/showText?id='+id,    
		    modal: true   
		}); 
	}
	
	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
	     $('#gender').val('');
		dataGrid.datagrid('load', {});
	}

	
	
	
	
</script>
</head>
<body>
	<div  class="easyui-layout" data-options="fit : true,border : false">
		<div   data-options="region:'north',title:'查询条件',border:false" style="height: 170px; overflow: hidden;">
			<form id="searchForm">
			
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
					    <th>名称</th>
					    <td><input  name="name" placeholder="可以查询名称" class="easyui-validatebox" style="width:279px;"/></td>
					    <th>列表标题</th>
					    <td>
					       <input  name="listTitle" placeholder="可以查询列表标题" class="easyui-validatebox" style="width:279px;"/>
					    </td>
					</tr>
					 <tr>
					   <th>正文标题</th>
					   <td>
					     <input  name="title" placeholder="可以查询正文标题" class="easyui-validatebox" style="width:279px;"/>
					   </td>
					   <th>是否已经发布</th>
					   <td>
					   <select id="state" name="state" class="easyui-combobox" style="width:282px;height: 26px;">
					      <option value="">请选择是否发布</option>
					      <option value="0">未发布</option>
					      <option value="1">已发布</option>
					   </select>
					   </td>
					
				    </tr>
				</table>
			</form>
		</div>
		<div   data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		
		 	<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>  
		
		<!--<c:if test="${fn:contains(sessionInfo.resourceList, '/deptLevelController/grantPage')}">
			<a onclick="batchGrantFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'tux'">批量授权</a>
		</c:if>-->
	
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	</div>

	<div id="ww"></div> 
</body>
</html>