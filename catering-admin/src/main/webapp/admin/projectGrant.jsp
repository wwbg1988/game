<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var projectTree;
	$(function() {
		projectTree = $('#projectTree').tree({
			url : '${pageContext.request.contextPath}/ProjectController/allTree',
			parentField : '',
			//lines : true,
			checkbox : true,
			onSelect : function(node) {
				//取消所有选中节点
	/* 		    uncheckAll();
				//选中当前节点
				projectTree.tree("check",node.target); */
			},
			onClick : function(node) {
			},
			onLoadSuccess : function(node, data) {
		    
				var ids = $.stringToList('${roleProjectDto.projId}');
				if (ids.length > 0) {
					for ( var i = 0; i < ids.length; i++) {
						if (projectTree.tree('find', ids[i])) {
							projectTree.tree('check', projectTree.tree('find', ids[i]).target);
						}
					}
				}
				$('#projectGrantLayout').layout('panel', 'center').panel('setTitle', $.formatString('[{0}]角色可以访问的项目', '${roleProjectDto.roleName}'));
				parent.$.messager.progress('close');
			},
			cascadeCheck : false
		});

		$('#form').form({
			url : '${pageContext.request.contextPath}/roleProjectController/grant',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				var checknodes = projectTree.tree('getChecked');
				var ids = [];
				if (checknodes && checknodes.length > 0) {
					for ( var i = 0; i < checknodes.length; i++) {
						ids.push(checknodes[i].id);
					}
				}
				$('#projId').val(ids);
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为role.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
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
			}
		});
	});

	function checkAll() {
		var nodes = projectTree.tree('getChecked', 'unchecked');
		if (nodes && nodes.length > 0) {
			for ( var i = 0; i < nodes.length; i++) {
				projectTree.tree('check', nodes[i].target);
			}
		}
	}
	function uncheckAll() {
		var nodes = projectTree.tree('getChecked');
		if (nodes && nodes.length > 0) {
			for ( var i = 0; i < nodes.length; i++) {
				projectTree.tree('uncheck', nodes[i].target);
			}
		}
	}
	function checkInverse() {
		var unchecknodes = projectTree.tree('getChecked', 'unchecked');
		var checknodes = projectTree.tree('getChecked');
		if (unchecknodes && unchecknodes.length > 0) {
			for ( var i = 0; i < unchecknodes.length; i++) {
				projectTree.tree('check', unchecknodes[i].target);
			}
		}
		if (checknodes && checknodes.length > 0) {
			for ( var i = 0; i < checknodes.length; i++) {
				projectTree.tree('uncheck', checknodes[i].target);
			}
		}
	}
</script>
<div id="projectGrantLayout" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center'" title="项目列表" style="width: 150px; padding: 1px;">
		<div class="well well-small">
			<form id="form" method="post">
				<input name="roleId" type="hidden" class="span2" value="${roleProjectDto.roleId}" readonly="readonly">
				<ul id="projectTree"></ul>
				<input id="projId" name="projId" type="hidden" />
			</form>
		</div>
	</div>
	<%--<div data-options="region:'center'" title="" style="overflow: hidden; padding: 10px;">
		<div class="well well-small">
			<span class="label label-success">${roleProjectDto.roleName}</span>
			<div>${roleProjectDto.roleMark}</div>
		</div>
	 	<div class="well well-large">
			<button class="btn btn-success" onclick="checkAll();">全选</button>
			<br /> <br />
			<button class="btn btn-warning" onclick="checkInverse();">反选</button>
			<br /> <br />
			<button class="btn btn-inverse" onclick="uncheckAll();">取消</button>
		</div>
	</div> --%>
</div>