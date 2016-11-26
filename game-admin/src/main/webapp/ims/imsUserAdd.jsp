<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var projectTree;
	var dept_id;
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
							onCheck: function(node) {
								var nodes = projectTree.tree('getChecked');
								if (nodes && nodes.length > 0) {
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
							onSelect : function(node) {//选择节点
								//取消所有选中节点
								var nodes = projectTree.tree('getChecked');
								if (nodes && nodes.length > 0) {
									for ( var i = 0; i < nodes.length; i++) {
										projectTree.tree('uncheck', nodes[i].target);
									}
								}
								//选中当前节点
								projectTree.tree("check",node.target);
						
							
								$.post(
										'${pageContext.request.contextPath}/deptController/treeGrid?projId='
												+ node.id, function(data) {
											$('#treeGrids').treegrid(
													'loadData', data);
										}, 'json')
							},
							onLoadSuccess : function(node, data) {
								var ids = $.stringToList('${user.projectIds}');
								if (ids.length > 0) {
									for (var i = 0; i < ids.length; i++) {
										if (projectTree.tree('find', ids[i])) {
											projectTree.tree('check',
													projectTree.tree('find',
															ids[i]).target);
										}
									}
								}

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
									},
							//		{
							//			field : 'isAdmin',
							//			title : '是否部门管理员',
							//			width : 140,
							//			formatter : function(value, row, index) {
							//				if(row.isExistManager==1){
							//					return '<select id="111" style="width:75px;height: 27px;text-align:center;" ><option value="0">否</option></select>';
							//				}else{
							//					return '<select id="111" style="width:75px;height: 27px;text-align:center;" ><option value="0">否</option><option value="1">是</option></select>';
							//				}
							//				
							//			}
							//		 },
									{
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
							onClickRow:function(row){
								dept_id=row.id;
						    $('#projectForm')
								.form(
										{
											url : '${pageContext.request.contextPath}/imsUserController/insertUserAndADept?flag=dept&userId='+$("#userId2").val()+'&deptId='+dept_id,
										//    data:{userAccount:$("#userAccount").val()},
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
												//密码不能 为空,不能大于6位
												var password = $("#password").val();
												if(password==null || password.length==0){
													parent.$.messager.progress('close');
													return false;
												}else if(password.length<6){
													parent.$.messager.progress('close');
													return false;
												}
												//员工姓名不能大于10位
												var name = $("#name").val();
												if(name==null || name.length==0){
													parent.$.messager.progress('close');
													return false;
												}else if(name.length>10){
													parent.$.messager.progress('close');
													return false;
												}
												//员工编号不能大于10位
												var userNo= $("#userNo").val();
												if(userNo==null || userNo.length==0){
													parent.$.messager.progress('close');
													return false;
												}else if(userNo.length>10){
													parent.$.messager.progress('close');
													return false;
												}
												//年龄必须是数字
												var age = $("#age").val();
												if(age==null || age.length==0){
													parent.$.messager.progress('close');
													return false;
												}else{
													
													reg=/^[-+]?\d*$/;
													if(!reg.test(age)){  
														alert("年龄必须是数字");
														parent.$.messager.progress('close');
														return false;
											        } 
												}
												//邮箱不合法
												var email = $("#email").val();
												if(email!=null && email.length!=0){
													reg=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
													if(!reg.test(email)){    
											            parent.$.messager.progress('close');
														return false;
											        } 
												}
												//验证手机号码
												var phonetel = $("#phone").val();
												 var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
												 if(phonetel==null || phonetel.length==0){
													 alert("手机号码不能为空");
												      parent.$.messager.progress('close');
													  return false;
												 }else{
													 if (!reg.test(phonetel)) {
													      alert("手机号码错误");
													      parent.$.messager.progress('close');
														  return false;
													 }
												 }
												//登陆账号已存在
												var userAccount = $("#userAccount").val();
												if(userAccount==null || userAccount.length==0){
													parent.$.messager.progress('close');
													return false;
												}
												
												var countUserAccount = 0;
												jQuery.ajax({
													url : '${pageContext.request.contextPath}/imsUserController/vailUserAccount?userAccount='+userAccount,
													type : 'POST',
													dataType : 'json',
													async : false,
													success : function(data) {
														//alert("data===="+data);
														countUserAccount=data;
													}
												});
												
												if(countUserAccount>0){
													parent.$.messager.progress('close');
													return false;
												}
						                       
												var treeChecks = $("#treeGrids").treegrid('getChecked');
											
												var treeCheckId = [];
												var isExistM =0;
												if(treeChecks  && treeChecks.length>0){
													for ( var i = 0; i < treeChecks.length; i++) {
														treeCheckId.push(treeChecks[i].id);
														isExistM = treeChecks[i].isExistManager;
													}
												}
											//	var treeCheckId = $("#treeGrids").treegrid('getChecked')[0].id;
												$('#deptIds').val(treeCheckId);
												
												//判断这个部门是否存在管理员
												 var isExis =$('#isExistManager').combobox('getValue');

												if(isExistM==1&&isExis==1){
													parent.$.messager.progress('close');
													return false;
												}
												
												
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
		$('#form')
				.form(
						{
							url : '${pageContext.request.contextPath}/imsUserController/insertUserAndADept?flag=user&userId='+$("#userId2").val(),
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
								
								var treeChecks = $("#treeGrids").treegrid('getChecked');
								
								var isExistM =0;
								if(treeChecks  && treeChecks.length>0){
									for ( var i = 0; i < treeChecks.length; i++) {
										isExistM = treeChecks[i].isExistManager;
									}
								}
								
								//判断这个部门是否存在管理员
								 var isExis =$('#isExistManager').combobox('getValue');
					
								if(isExistM==1&&isExis==1){
									alert("该部门已经存在管理员，不能再添加");
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

	
//	$("#isAdmin").combobox(
//	{
//	 	onSelect : function(n, o) {
//			$("#isAdminDept").val(n.value);
//		}
// 	});
	
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
	
	
	
</script>
<div id="tt" class="easyui-tabs" style="width: 738px; height: 396px;">
	<div title="用户基本信息" style="padding: 5px; display:;">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'center',border:false" title=""
				style="overflow: hidden;">
				<form id="form" method="post">
				   <input id="userId2" name="userId2" type="hidden" value="${userId}" />
					<table class="table table-hover table-condensed">
						<tr>

							<th>登录名称</th>
							<td><input name="userAccount" type="text" id="userAccount"
								placeholder="请输入登录名称" class="easyui-validatebox span2"
								data-options="required:true" ></td>
							<th>邮箱</th>
							<td><input name="email" type="text" placeholder="请输入邮箱"  id="email"
								class="easyui-validatebox span2"></td>

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
								class="easyui-validatebox span2" data-options="required:true"></td>
							<th>员工编号</th>
							<td><input name="userNo" type="text" placeholder="请输入员工编号" id="userNo"
								class="easyui-validatebox span2" data-options="required:true"></td>
						</tr>
						<tr>
							<th>年龄</th>
							<td><input name="age" type="text" placeholder="请输入年龄" id="age"
								class="easyui-validatebox span2" data-options="required:true"></td>
							<th>性别</th>
							<td><select name="gender" class="easyui-combobox" id="gender"
								data-options="width:140,height:29,editable:false,panelHeight:'auto'"
								data-options="required:true"><option value="1">男</option>
									<option value="2">女</option></select></td>
						</tr>
						<tr>
							<th>手机号</th>
						    <td><input name="phone" type="text" placeholder="请输入手机号" id="phone"
								class="easyui-validatebox span2" data-options="required:true"></td>
							<th>密码</th>
							<td><input name="password" type="password" id="password"
								placeholder="请输入密码" class="easyui-validatebox span2"
								data-options="required:true"></td>
						<!-- 	<th>是否是管理员</th>
					        <td><select name="isAdmin" class="easyui-combobox" id="isAdmin"
								data-options="width:140,height:29,editable:false,panelHeight:'auto'"
								data-options="required:true" >
								<option value="0">否</option>
								<option value="1">是</option>
								</select></td>   -->
					
						</tr>


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
					<form id="projectForm" method="post">
					   <table>
					    <tr>
							
							<th >是否部门管理员</th>
							<td><select name="isExistManager"  id="isExistManager"  
							class="easyui-combobox" data-options="width:110,height:29,editable:false,panelHeight:'auto'"
								data-options="required:true">
							  <option value="0">否</option>  
							  <option value="1">是</option>  
							</select></td>
						</tr>
					   </table>
					   <div>&nbsp;</div>
						<ul id="projectTree"></ul>
						<input id="projectIds" name="projectIds" type="hidden" />
						<input id="deptIds" name="deptIds" type="hidden"/>
						<!-- <input id="isAdminDept" name="isAdminDept" type="hidden" value="0"/>  -->
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
		data-options="plain:true,iconCls:'resultset_previous'">折叠</a>
</div>
