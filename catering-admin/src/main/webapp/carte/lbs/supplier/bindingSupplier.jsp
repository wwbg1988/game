<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var resourceTree;
	$(function() {
		resourceTree = $('#resourceTree').tree({
			url : '${pageContext.request.contextPath}/supplierController/findSupplierTree',
			checkbox : true,
			onClick : function(node) {
			},
			onLoadSuccess : function(node, data) {
				var ids =  $.stringToList('${checkedSupplierIds}');
				if(ids.length>0){
					for ( var i = 0; i < ids.length; i++) {
						var foundNode = resourceTree.tree('find', ids[i]);
						if(!!foundNode){
							resourceTree.tree('check', foundNode.target);
						}
					}
				}
				
				$('#roleGrantLayout').layout('panel', 'west').panel('setTitle', $.formatString('{0}的可选供应商', '${sessionInfo.name}'));
				parent.$.messager.progress('close');
			},
			cascadeCheck : true
		});

		$('#form').form({
			url : '${pageContext.request.contextPath}/supplierController/bindingSupplier',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				var checknodes = resourceTree.tree('getChecked');
				var ids = [];
				if (checknodes && checknodes.length > 0) {
					for ( var i = 0; i < checknodes.length; i++) {
						ids.push(checknodes[i].id);
					}
				}
				$('#supplierId').val(ids);
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');
					parent.$.modalDialog.handler.dialog('close'); 
					
					parent.$.messager.show({
						title : '提示',
						msg : result.msg
					});
				}
				else{
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});

	function checkAll() {
		var nodes = resourceTree.tree('getChecked', 'unchecked');
		if (nodes && nodes.length > 0) {
			for ( var i = 0; i < nodes.length; i++) {
				resourceTree.tree('check', nodes[i].target);
			}
		}
	}
	function uncheckAll() {
		var nodes = resourceTree.tree('getChecked');
		if (nodes && nodes.length > 0) {
			for ( var i = 0; i < nodes.length; i++) {
				resourceTree.tree('uncheck', nodes[i].target);
			}
		}
	}
	function checkInverse() {
		var unchecknodes = resourceTree.tree('getChecked', 'unchecked');
		var checknodes = resourceTree.tree('getChecked');
		if (unchecknodes && unchecknodes.length > 0) {
			for ( var i = 0; i < unchecknodes.length; i++) {
				resourceTree.tree('check', unchecknodes[i].target);
			}
		}
		if (checknodes && checknodes.length > 0) {
			for ( var i = 0; i < checknodes.length; i++) {
				resourceTree.tree('uncheck', checknodes[i].target);
			}
		}
	}
</script>
<div id="roleGrantLayout" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'west'" title="供应商列表" style="width: 300px; padding: 1px;">
		<div class="well well-small">
			<form id="form" method="post">
				<ul id="resourceTree"></ul>
				<input id="adminUsersId" name="adminUsersId" type="hidden" value="${adminUsersId}"/>
				<input id="supplierId" name="supplierId" type="hidden" />
			</form>
		</div>
	</div>
	<div data-options="region:'center'" title="" style="overflow: hidden; padding: 10px;">
		<div class="well well-small">
			<span class="label label-success">${sessionInfo.userAccount}</span>
			<div>${sessionInfo.name}</div>
		</div>
		<div class="well well-large">
			<button class="btn btn-success" onclick="checkAll();">全选</button>
			<br /> <br />
			<button class="btn btn-warning" onclick="checkInverse();">反选</button>
			<br /> <br />
			<button class="btn btn-inverse" onclick="uncheckAll();">取消</button>
		</div>
	</div>
</div>