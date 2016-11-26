<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>角色管理</title>
<jsp:include page="../inc.jsp"></jsp:include>

<script type="text/javascript">
	var treeGrid;
	$(function() {
		treeGrid = $('#treeGrid').treegrid({
			url : '${pageContext.request.contextPath}/ProjectController/treeGrid',
			idField : 'id',
			treeField : 'name',
			parentField : 'pid',
			fit : true,
			fitColumns : false,
			border : false,
			nowrap : true,
			showHeader : true,
			frozenColumns : [ [ {
				title : '编号',
				field : 'id',
				width : 350,
				hidden : true
			}, {
				field : 'name',
				title : '项目名称',
				width : 450
			} ] ],
			columns : [ [ {
				field : 'pid',
				title : '用户所在的项目ID',
				width : 450,
				hidden : true
			}, {
				field : 'pname',
				title : '用户所在的项目',
				width : 280
			}, {
				field : 'remark',
				title : '备注',
				width : 450
			}, {
				field : 'action',
				title : '操作',
				width : 170,
				formatter : function(value, row, index) {
					var str = '';
			
						str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
					
					str += '&nbsp;';
					
						str += $.formatString('<img onclick="grantFun(\'{0}\');" src="{1}" title="添加流程"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil_add.png');
					
					str += '&nbsp;';
					
						str += $.formatString('<img onclick="adduserFun(\'{0}\');" src="{1}" title="增加用户"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/key.png');
					
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onContextMenu : function(e, row) {
				e.preventDefault();
				$(this).treegrid('unselectAll');
				$(this).treegrid('select', row.id);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			},
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
				$('#searchForm table').show();
				$(this).treegrid('tooltip');
			}
		});
	});

	function editFun(id) {
		if (id != undefined) {
			treeGrid.treegrid('select', id);
		}
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			parent.$.modalDialog({
				title : '编辑项目',
				width : 700,
				height : 500,
				href : '${pageContext.request.contextPath}/ProjectController/editProject?id=' + id,
				buttons : [ {
					text : '编辑',
					handler : function() {
						parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#form');
						f.submit();
					}
				} ]
			});
		}
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加项目',
			width : 700,
			height : 130,
			href : '${pageContext.request.contextPath}/qinJiaController/wuWeiConnect',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function redo() {
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			treeGrid.treegrid('expandAll', node.id);
		} else {
			treeGrid.treegrid('expandAll');
		}
	}

	function undo() {
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			treeGrid.treegrid('collapseAll', node.id);
		} else {
			treeGrid.treegrid('collapseAll');
		}
	}

	function grantFun(id) {
		if (id != undefined) {
			treeGrid.treegrid('select', id);
		}
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			parent.$.modalDialog({
				title : '添加流程',
				width : 700,
				height : 200,
				href : '${pageContext.request.contextPath}/tImsProcessController/addPage?projid=' + id,
				buttons : [ {
					text : '添加流程',
					handler : function() {
						parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#form');
						f.submit();
					}
				} ]
			});
		}
	}
	
	function adduserFun(id) {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		parent.$.modalDialog({
			title : '用户授权',
			width : 800,
			height : 600,
			href : '${pageContext.request.contextPath}/ProjectController/grantUserPage?ids=' + id,
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
		treeGrid.treegrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
	
		treeGrid.treegrid('load', {});
	}
</script>
</head>
<body>
	<div  class="easyui-layout" data-options="fit : true,border : false">
	   
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 100px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
					    <th>ID</th>
						<td><input name="id" placeholder="可以查询ID" class="span2" style="width:279px;"/></td>
						<th>项目名称</th>
						<td><input name="projName" placeholder="可以查询任务节点名称" class="span2" style="width:279px;"/></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="treeGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
		
		<!--<c:if test="${fn:contains(sessionInfo.resourceList, '/deptLevelController/grantPage')}">
			<a onclick="batchGrantFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'tux'">批量授权</a>
		</c:if>-->
	
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	</div>


</body>
</html>