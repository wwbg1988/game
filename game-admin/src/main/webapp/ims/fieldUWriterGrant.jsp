<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var userWriteTree;
	var writeRole;
	$(function() {
		
		var roleOwner =$.stringToList('${roleOwner}');
		if(roleOwner=='ownerRoleOk'){
			$('#roleOwnId').attr('checked',true);
		}
		var userOwner =$.stringToList('${userOwner}');
		if(userOwner=='ownerUserSOk'){
			$('#userOwnId').attr('checked',true);
		}

		userWriteTree = $('#userWriteTree').tree({
			url : '${pageContext.request.contextPath}/actionsController/fieldTree',
			parentField : 'pid',
			//lines : true,
			checkbox : true,
			onClick : function(node) {
			},
			onLoadSuccess : function(node, data) {
				var ids = $.stringToList('${userWriterIdss.roleIds}');
				if (ids.length > 0) {
					for ( var i = 0; i < ids.length; i++) {
						if (userWriteTree.tree('find', ids[i])) {
							userWriteTree.tree('check', userWriteTree.tree('find', ids[i]).target);
						}
					}
				}
				//$('#roleGrantLayout').layout('panel', 'west').panel('setTitle', $.formatString('[{0}]角色可以访问的资源', '${role.name}'));
				parent.$.messager.progress('close');
			},
			onCheck : function(node) {
				
			     //获取所有选中的节点
			    var isChecked=1;
			    var checkNodes = userWriteTree.tree('getChecked','checked');
			      if(checkNodes && checkNodes.length>0 ){
			    	  for(var j=0;j<checkNodes.length;j++){
			    		  if(checkNodes[j].id==node.id){
			    			//  alert("该节点选中");
			    			  isChecked=2;
			    		  }
			    	  }
			      }
			
				var childrens = userWriteTree.tree('getChildren',node.target); 
				if(childrens && childrens.length>0 ){
					for(var i =0; i<childrens.length ; i++ ){
						if(isChecked==2){
							userWriteTree.tree('check',childrens[i].target);
						}else{
							userWriteTree.tree('uncheck',childrens[i].target);
						}
					}
				}
			},
			cascadeCheck : false
		});
		

		writeRole =	$('#writeRole').tree({
			url : '${pageContext.request.contextPath}/actionsController/allUserRoleTree',
			parentField : '',
			//lines : true,
			checkbox : true,
			onClick : function(node) {
			},
			onLoadSuccess : function() {
				var ids = $.stringToList('${roleWriterIdss.resourceIds}');
				if (ids.length > 0) {
					for ( var i = 0; i < ids.length; i++) {
						if (writeRole.tree('find', ids[i])) {
							writeRole.tree('check', writeRole.tree('find', ids[i]).target);
						}
					}
				}
				parent.$.messager.progress('close');
			},
			cascadeCheck : false,
			//value : $.stringToList('${user.roleIds}')
		});
		


		$('#userForm').form({
			url : '${pageContext.request.contextPath}/fieldsController/grantUsers',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				var checknodes = userWriteTree.tree('getChecked');
				var ids = [];
				if (checknodes && checknodes.length > 0) {
					for ( var i = 0; i < checknodes.length; i++) {
						ids.push(checknodes[i].id);
					}
				}
				$('#userIds').val(ids);
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					//parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为role.jsp页面预定义好了
					//parent.$.modalDialog.handler.dialog('close');
					
				}else{
					parent.$.messager.alert('操作', result.msg, 'error');
				}
			}
		});
	});
		$('#writerForm').form({
			url : '${pageContext.request.contextPath}/fieldsController/grantWriter',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				var checknodes = writeRole.tree('getChecked');
				var ids = [];
				if (checknodes && checknodes.length > 0) {
					for ( var i = 0; i < checknodes.length; i++) {
						ids.push(checknodes[i].id);
					}
				}
				$('#roleWriterIds').val(ids)
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);	
				if (result.success) {
					
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					//parent.$.modalDialog.handler.dialog('close');
					 $('#dd4').dialog('close');
					parent.$.modalDialog.openner_dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	

	
	
	$("#searchNameBtn").click(function(){
		var searchName=$("#searchName").val();
		
		userWriteTree = $('#userWriteTree').tree({
			url : '${pageContext.request.contextPath}/actionsController/fieldTree?searchName='+searchName,
			parentField : 'pid',
			//lines : true,
			checkbox : true,
			onClick : function(node) {
			},
			onLoadSuccess : function(node, data) {
				var ids = $.stringToList('${userWriterIdss.roleIds}');
				if (ids.length > 0) {
					for ( var i = 0; i < ids.length; i++) {
						if (userWriteTree.tree('find', ids[i])) {
							userWriteTree.tree('check', userWriteTree.tree('find', ids[i]).target);
						}
					}
				}
				//$('#roleGrantLayout').layout('panel', 'west').panel('setTitle', $.formatString('[{0}]角色可以访问的资源', '${role.name}'));
				parent.$.messager.progress('close');
			},
			cascadeCheck : false
		});
		
	});


</script>
<div id="roleGrantLayout" class="easyui-layout" data-options="fit:true,border:false">
	 <div data-options="region:'west'" title="用户列表" style="width: 260px; padding: 1px;">
		<div class="well well-small">
			<form id="userForm" method="post">
				<ul id="userWriteTree"></ul>
				<input type="checkbox" name="userOwn" id="userOwnId" value="userOwnOk">是否仅供提交表单组可见
				<input id="userIds" name="userIds" type="hidden" />
			</form>
		</div>
	</div> 
	 <div data-options="region:'center'" title="" style="width: 20px; overflow: hidden;">
	
	</div> 
	<div data-options="region:'east'" title="角色列表" style="width:260px; overflow: hidden;">
		<div class="well well-small">
			<form id="writerForm" method="post">
				<ul id="writeRole"></ul>
				<input id="roleWriterIds" name="roleWriterIds" type="hidden" />
			</form>
		</div>
	</div>
	

	<div data-options="region:'north'" title="" style="overflow: hidden; padding: 10px;">
		<div class="well well-small">
			<input type="text" id="searchName" placeholder="请输入查询姓名名称"/>
			<input type="button" id="searchNameBtn"  value="搜索"/>
		</div>
		<!-- <div class="well well-large">
			<button class="btn btn-success" onclick="checkAll();">全选</button>
			<br /> <br />
			<button class="btn btn-warning" onclick="checkInverse();">反选</button>
			<br /> <br />
			<button class="btn btn-inverse" onclick="uncheckAll();">取消</button>
		</div> -->
	</div>
</div>