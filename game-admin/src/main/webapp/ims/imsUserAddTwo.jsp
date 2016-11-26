<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var projectTree;
	var dept_id;
	$(function() {
		parent.$.messager.progress('close');


		$('#form')
				.form(
						{
							url : '${pageContext.request.contextPath}/imsUserController/insertUserDept2?userId='+$("#userId2").val(),
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
								
							/* 	var checknodes = projectTree.tree('getChecked');
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
								 */
								//判断这个部门是否存在管理员
							/* 	 var isExis =$('#isExistManager').combobox('getValue');
					
								if(isExistM==1&&isExis==1){
									alert("该部门已经存在管理员，不能再添加");
									parent.$.messager.progress('close');
									return false;
								}
								 */
								
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
			//alert(myObject);
			//alert(JSON.stringify(myObject));
			$("#projId").html(a);
			initDept(myObject[0].id);
		}
	});
 
	function initDept(projId){
		jQuery.ajax({
			url : '${pageContext.request.contextPath}/projectUsersController/findProDept?projId='+projId,
			type : 'POST',
			dataType : 'json',
			async : false,
			success : function(data) {
				var kk = data;
				$('#deptId').combotree({
					url : '${pageContext.request.contextPath}/deptController/tree?projId='+projId,
					parentField : 'pid',
					lines : true,
					onLoadSuccess : function() {
						parent.$.messager.progress('close');
					}
				});
			}
		});
		
		
	
	}
	
	
	//级联下拉框
	$("#projId").combobox(
			{
				onSelect : function(n, o) {
					initDept(n.value);
				}
			});


	
	
</script>
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
						</tr>
					</table>
					<span>--------------------------------------------为用户选择项目部门------------------------------------------</span>
					<table class="table table-hover table-condensed">
					       <tr>
					          <th>项目</th>
						      <td><select id="projId" name="projId" class="easyui-combobox"  data-options="width:240,height:29,editable:false"></select>
							  </td>
							  <th>部门</th>
							  <td><select name="deptId"  id="deptId"   data-options="width:240,height:29,editable:false">
							     <option >请选择部门</option>
							  </select>
					       </tr>
					       <tr>
					       	<th >是否部门管理员</th>
							<td><select name="isExistManager"  id="isExistManager"  
							class="easyui-combobox" data-options="width:110,height:29,editable:false,panelHeight:'auto'"
								data-options="required:true">
							  <option value="0">否</option>  
							  <option value="1">是</option>  
							</select></td>
							<th>&nbsp;</th>
							<td>&nbsp;</td>
					       </tr> 
					</table>
				</form>
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
