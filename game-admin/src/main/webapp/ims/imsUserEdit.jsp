<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var projectTree;
	$(function() {
		parent.$.messager.progress('close');

		//左侧的项目树
		projectTree = $('#projectTree')
				.tree(
						{
							url : '${pageContext.request.contextPath}/ProjectController/allTree',
							//parentField : 'pid',
							//lines : true,
							checkbox : true,
							onClick : function(node) {
					
							},
							onCheck : function(node){
								var nodes = projectTree.tree('getChecked');
								if(nodes && nodes.length>0){
								    $.post(
											'${pageContext.request.contextPath}/deptController/treeGrid?projId='
													+ node.id, function(data) {
												$('#treeGrids').treegrid(
														'loadData', data);
											}, 'json');
								}else{
									$.post(
											'${pageContext.request.contextPath}/deptController/treeGrid?projId='
													+ node.id, function(data) {
												$('#treeGrids').treegrid(
														'loadData', '');
											}, 'json');	
								}
							},
							onSelect : function(node) {//选择事件
			
						
								//选中当前节点
								projectTree.tree("check",node.target);
							
								//获取所有跟节点
								var roots = projectTree.tree('getRoots');
							    //获取所有选中节点
								var nodes = projectTree.tree('getChecked');
							    var projIds=[];
							    
								if (nodes && nodes.length > 0) {
									//for ( var i = 0; i < nodes.length; i++) {
									//	projIds.push(nodes[i].id);
									//}
								
								      projIds.push(node.id);
									$.post(
											'${pageContext.request.contextPath}/deptController/treeGrid?projIds='
													+ projIds, function(data) {
												$('#treeGrids').treegrid(
														'loadData', data);
												projIds=[];
											}, 'json')
								}else{
									$.post(
											'${pageContext.request.contextPath}/deptController/treeGrid?projIds='
													+ projIds, function(data) {
												$('#treeGrids').treegrid(
														'loadData', '');
												projIds=[];
											}, 'json')
									parent.$.messager.alert('提示', "请至少勾选一个项目节点",
									'error');
								}
						
								
										
							},
							onLoadSuccess : function(node, data) {
								//var ids = $.stringToList('${user.projectIds}');
							

								parent.$.messager.progress('close');
							},
							cascadeCheck : false
						});

		//右侧的部门treeGrid
		treeGrid = $('#treeGrids')
				.treegrid(
						{
							url : '',
							fit : true,
							fitColumns : true,
							border : false,
							pagination : false,
							idField : 'id',
						//	singleSelect:false, 
							treeField : 'deptName',
							parentField : 'pid',
							nowrap : true,
							frozenColumns : [ [ {
								field : 'id',
								title : '编号',
								width : 150,
								checkbox : true
							} ] ],
							columns : [ [
									{
										field : 'deptName',
										title : '部门名称',
										width : 200,
										formatter : function(value, row, index) {
											return '<font style="font-style: normal;font-weight: bolder;">'
													+ value + '</font>';
										}
									},
									{
										field : 'deptCode',
										title : '部门编码',
										width : 140,
										formatter : function(value, row, index) {
											return '<font style="font-style: normal;font-weight: bolder;">'
													+ value + '</font>';
										}
									}, 	{
										field : 'projName',
										title : '所属项目名称',
										width : 150,
										hidden : true,
									    formatter : function(value, row, index) {
											return '<font style="font-style: normal;font-weight: bolder;">'
													+ value + '</font>';
										}
									},{
										field : 'projId',
										title : '所属项目编号',
										width : 150,
										hidden : true
									} ] ],
							toolbar : '#toolbar',
							onLoadSuccess : function() {
								
								parent.$.messager.progress('close');

								$(this).treegrid('tooltip');
							},
							onRowContextMenu : function(e, rowIndex, rowData) {
								e.preventDefault();
								$(this).treegrid('unselectAll');
								$(this).treegrid('select', row.id);
								$('#menu').menu('show', {
									left : e.pageX,
									top : e.pageY
								});
							}
						});
		$('#formEdit')
				.form(
						{
							url : '${pageContext.request.contextPath}/imsUserController/updateUser',
							onSubmit : function() {
								/*var age = $("#age").val();
								if(age==''||isNaN(age)){
									parent.$.messager.alert('错误', '年龄必须为数字', 'error');
								}
								var depts*/
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
								if(ids.length==0){
									alert("项目不能为空");
									parent.$.messager.progress('close');
									return false;
								}
							//	$('#projectIds').val(ids);
								
							//	var treeCheckId = $("#treeGrids").treegrid('getChecked');
								if($("#treeGrids").treegrid('getChecked').length==0){
									alert("部门不能为空")
									parent.$.messager.progress('close');
									return false;
								}

								
								return isValid;
							},
							success : function(result) {
								parent.$.messager.progress('close');
								result = $.parseJSON(result);
								
								if (result.success) {
									
									//$("#projectForm").submit();
									
									parent.$.modalDialog.openner_dataGrid
											.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
									parent.$.modalDialog.handler
											.dialog('close');
								} else {
									parent.$.messager.alert('错误', result.msg,
											'error');
								}
							}
						});
		
		
		$('#projectFormEdit')
		.form(
				{
					url : '${pageContext.request.contextPath}/imsUserController/updateDept?id='+$("#id").val(),
					onSubmit : function() {
						/*var age = $("#age").val();
						if(age==''||isNaN(age)){
							parent.$.messager.alert('错误', '年龄必须为数字', 'error');
						}
						var depts*/
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
					 
						$('#projectIds').val(ids);
					
						
						if(ids.length==0){
							//alert("项目不能为空");
							parent.$.messager.progress('close');
							return false;
						}
						if($("#treeGrids").treegrid('getChecked').length==0){
							//alert("部门不能为空")
							parent.$.messager.progress('close');
							return false;
						}
						var checkdepts = $("#treeGrids").treegrid('getChecked');
						var deptids=[];
						if(checkdepts && checkdepts.length>0){
							for(var j=0;j<checkdepts.length;j++){
								deptids.push(checkdepts[j].id);
							}
						}
						//var treeCheckId = $("#treeGrids").treegrid('getChecked')[0].id;
						$('#deptIds').val(deptids);
						
						return isValid;
					},
					success : function(result) {
						parent.$.messager.progress('close');
						result = $.parseJSON(result);
						if (result.success) {
							parent.$.modalDialog.openner_dataGrid
									.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
							parent.$.modalDialog.handler
									.dialog('close');
						} else {
							parent.$.messager.alert('错误', result.msg,
									'error');
						}
					}
				});
		
	});

	jQuery.ajax({
		url : '${pageContext.request.contextPath}/ProjectController/findAll',
		type : 'POST',
		dataType : 'json',
		async : false,
		success : function(data) {
			var myObject = data;
			//var a= "<option value=\'-1\'>请选择</option>";
			var a = '';
			for (var i = 0; i < myObject.length; i++) {
				a += "<option value=\'"+myObject[i].id+"\'>"
						+ myObject[i].projName + "</option>";
				console.log(a);
			}
			$("#projId").html(a);
		}
	});

	//级联下拉框
	$("#projId").combobox(
			{
				onSelect : function(n, o) {
					$("#depts").combobox(
							'reload',
							"${pageContext.request.contextPath}/projectUsersController/findProDept?projId="
									+ n.value);
					$("#depts").combobox("setValue", '请选择部门ID');
				}
			});
	$('#depts').combobox({
		valueField : 'deptId',
		textField : 'deptName'
	})
	function redos() {
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			treeGrid.treegrid('expandAll', node.id);
		} else {
			treeGrid.treegrid('expandAll');
		}
	}

	function undos() {
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			treeGrid.treegrid('collapseAll', node.id);
		} else {
			treeGrid.treegrid('collapseAll');
		}
	}
	
//	$("#isAdmin").combobox(
//			{
//				onSelect : function(n, o) {
//					$("#isAdminDept").val(n.value);
//				}
//			});
	
</script>
<div id="tt" class="easyui-tabs" style="width: 738px; height: 396px;">
	<div title="用户基本信息" style="padding: 5px; display:;">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'center',border:false" title=""
				style="overflow: hidden;">
				<form id="formEdit" method="post">
				<input id="id" name="id" type="hidden" value="${user.id}"/>
					<table class="table table-hover table-condensed">
						<tr>

							<th>登录名称</th>
							<td><input name="userAccount" type="text" id="userAccount"
								placeholder="请输入登录名称" class="easyui-validatebox span2"
								data-options="required:true" value="${user.userAccount}"></td>
							<th>邮箱</th>
							<td><input name="email" type="text" placeholder="请输入邮箱"  id="email"
								class="easyui-validatebox span2" value="${user.email}"></td>

						</tr>
					<!-- 	<tr>
							<th>项目</th>
							<td><select id="projId" name="projId"
								class="easyui-combobox"
								data-options="width:140,height:29,editable:false,panelHeight:'auto'"></select></td>
							<th>部门</th>
							<td><select id="depts" name="deptId" class="easyui-combobox"
								data-options="width:140,height:29,editable:false,panelHeight:'auto'">
									<option value="">请选择部门ID</option>
							</select></td>
						</tr>   -->
						<tr>
							<th>员工姓名</th>
							<td><input name="name" type="text" placeholder="请输入员工姓名" id="name"
								class="easyui-validatebox span2" data-options="required:true" value="${user.name}"></td>
							<th>员工编号</th>
							<td><input name="userNo" type="text" placeholder="请输入员工编号" id="userNo"
								class="easyui-validatebox span2" data-options="required:true" value="${user.userNo}"></td>
						</tr>
						<tr>
							<th>年龄</th>
							<td><input name="age" type="text" placeholder="请输入年龄" id="age"
								class="easyui-validatebox span2" data-options="required:true" value="${user.age}"></td>
							<th>性别</th>
							<td><select name="gender" class="easyui-combobox" id="gender"
								data-options="width:140,height:29,editable:false,panelHeight:'auto'"
								data-options="required:true">
								<c:if test="${user.gender==1}"><option value="1" selected="selected">男</option><option value="2">女</option></c:if>
				                <c:if test="${user.gender==2}"><option value="1" >男</option><option value="2" selected="selected">女</option></c:if>
							</select></td>
						</tr>
					<!-- 	<tr>
						    <th>是否是管理员</th>
					        <td>
					        <select name="isAdmin" class="easyui-combobox" id="isAdmin"
								data-options="width:140,height:29,editable:false,panelHeight:'auto'"
								data-options="required:true" >
								<c:if test="${user.isAdmin==0}"><option value="0" selected="selected">否</option> <option value="1">是</option></c:if>
								<c:if test="${user.isAdmin==1}"><option value="0" >否</option> <option value="1" selected="selected">是</option></c:if>
								</select></td>
							<th>&nbsp;</th>
							<td>&nbsp;</td>
						</tr>   -->
					</table>
				</form>
			</div>

		</div>
	</div>
	<div title="所属项目部门信息" data-options="closable:false"
		style="overflow: auto; padding: 5px; display:;">
		<div id="projectLayout" class="easyui-layout"
			data-options="fit:true,border:false">
			<div data-options="region:'west'" title="项目列表"
				style="width: 250px; padding: 1px;">
				<div class="well well-small">
					<form id="projectFormEdit" method="post">
						<ul id="projectTree"></ul>
						<input id="projectIds" name="projectIds" type="hidden" />
						<input id="deptIds" name="deptIds" type="hidden"/>
					<!--	<input id="isAdminDept" name="isAdminDept" type="hidden" value="${user.isAdmin}"/>   -->
					</form>
				</div>
			</div>
			<div data-options="region:'center'"
				style="width: 20px; padding: 1px;">
				<table id="treeGrids"></table>
			</div>


		</div>
	</div>

</div>
<div id="toolbar" style="display: none;">

	<div style="height: 2px;"></div>

	<a onclick="redos();" href="javascript:void(0);"
		class="easyui-linkbutton"
		data-options="plain:true,iconCls:'resultset_next'">展开</a> <a
		onclick="undos();" href="javascript:void(0);"
		class="easyui-linkbutton"
		data-options="plain:true,iconCls:'resultset_previous'">折叠</a><a
			onclick="projectTree.tree('reload');" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'transmit'">刷新</a>
</div>
