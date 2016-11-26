<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var userTrees;
	var readRole;
	$(function() {
		//用户赋权列表
		userTrees = $('#userTrees').tree({
			url : '${pageContext.request.contextPath}/actionsController/userTreeBy',
			parentField : 'pid',
			//lines : true,
			checkbox : true,
			onClick : function(node) {
			},
			onLoadSuccess : function(node, data) {
				//var ids = $.stringToList('${roleRead.resourceIds}');
				var ids = $.stringToList('${userRead.roleIds}');
				if (ids.length > 0) {
					for ( var i = 0; i < ids.length; i++) {

						if (userTrees.tree('find', ids[i])) {
							userTrees.tree('check', userTrees.tree('find', ids[i]).target);
						}
					}
				}
				//$('#roleGrantLayout').layout('panel', 'west').panel('setTitle', $.formatString('[{0}]角色可以访问的资源', '${role.name}'));
				parent.$.messager.progress('close');
			},
			onCheck : function(node) {
				
			     //获取所有选中的节点
			    var isChecked=1;
			    var checkNodes = userTrees.tree('getChecked','checked');
			      if(checkNodes && checkNodes.length>0 ){
			    	  for(var j=0;j<checkNodes.length;j++){
			    		  if(checkNodes[j].id==node.id){
			    			//  alert("该节点选中");
			    			  isChecked=2;
			    		  }
			    	  }
			      }
			
				var childrens = userTrees.tree('getChildren',node.target); 
				if(childrens && childrens.length>0 ){
					for(var i =0; i<childrens.length ; i++ ){
						if(isChecked==2){
							userTrees.tree('check',childrens[i].target);
						}else{
							userTrees.tree('uncheck',childrens[i].target);
						}
					}
				}
			},
			cascadeCheck : false
		});
		
		//角色赋权列表
		RoleTrees = $('#RoleTrees').tree({
			url : '${pageContext.request.contextPath}/fieldsController/readTree',
			parentField : '',
			//lines : true,
			checkbox : true,
			onClick : function(node) {
			},
			onLoadSuccess : function() {
				//var ids = $.stringToList('${usersRead.roleIds}');
				var ids = $.stringToList('${roleRead.resourceIds}');
				if (ids.length > 0) {
					for ( var i = 0; i < ids.length; i++) {
						if (RoleTrees.tree('find', ids[i])) {
							RoleTrees.tree('check', RoleTrees.tree('find', ids[i]).target);
						}
					}
				}
				parent.$.messager.progress('close');
			},
			cascadeCheck : false,
			//value : $.stringToList('${user.roleIds}')
		});
		

		//角色赋权
		$('#roleForm').form({
			url : '${pageContext.request.contextPath}/fieldsController/grantRead',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				var checknodes = RoleTrees.tree('getChecked');
				var ids = [];
				if (checknodes && checknodes.length > 0) {
					for ( var i = 0; i < checknodes.length; i++) {
						ids.push(checknodes[i].id);
					}
				}
				$('#roleIds').val(ids);
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);	
				if (result.success) {
					
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					//parent.$.modalDialog.handler.dialog('close');
					 $('#dd5').dialog('close');
					parent.$.modalDialog.openner_dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	

	
	
		//用户赋权
		$('#forms').form({
			url : '${pageContext.request.contextPath}/fieldsController/grantUsersRead',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				var checknodes = userTrees.tree('getChecked');
				var ids = [];
				if (checknodes && checknodes.length > 0) {
					for ( var i = 0; i < checknodes.length; i++) {
						ids.push(checknodes[i].id);
					}
				}
				$('#userIdss').val(ids);
				

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
	

	
	$("#searchNameBtn").click(function(){
		var searchName=$("#searchName").val();
		
		userTrees = $('#userTrees').tree({
			url : '${pageContext.request.contextPath}/actionsController/userTreeBy?searchName='+searchName,
			parentField : 'pid',
			//lines : true,
			checkbox : true,
			onClick : function(node) {
			},
			onLoadSuccess : function(node, data) {
				var ids = $.stringToList('${userRead.roleIds}');
				if (ids.length > 0) {
					for ( var i = 0; i < ids.length; i++) {
						if (userTrees.tree('find', ids[i])) {
							userTrees.tree('check', userTrees.tree('find', ids[i]).target);
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
	<div data-options="region:'west'" title="用户列表" style="width: 350px; padding: 1px;overflow:hidden;height:300px;">
		<div class="well well-small">
		<form id="forms" method="post">
				<ul id="userTrees"></ul>
				<input id="userIdss" name="userIdss" type="hidden" />

			</form>
		</div>
	</div> 
	<div data-options="region:'center'"  style="width: 20px; padding: 1px;">
	</div>
	<div data-options="region:'east'" title="角色列表" style="width:350px; overflow: hidden;">
		<div class="well well-small">
			<form id="roleForm" method="post">
				<ul id="RoleTrees"></ul>
				<input id="roleIds" name="roleIds" type="hidden" />
			</form>
		</div>
	</div>
	

	<div data-options="region:'north'" title="" style="overflow: hidden; padding: 10px;">
		<div class="well well-small">
			<input type="text" id="searchName" placeholder="请输入查询姓名名称"/>
			<input type="button" id="searchNameBtn"  value="搜索"/>
		</div>

	</div>
</div>