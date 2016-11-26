<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(function(){
	parent.$.messager.progress('close');
	
	 
	 //表单
	 $('#form').form({
			url : '${pageContext.request.contextPath}/transportDriverController/editPassword',
			onSubmit : function() {
				if($('#newPassword').val() != $('#confirmPassword').val()){
					alert("两次密码不一致");
					return false;
				}
				
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
					//parent.$.messager.alert('提示', result.msg, 'info');
					parent.$.messager.show({
						title : '提示',
						msg : result.msg
					});
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
     
	
});

</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;">
		<form id="form" method="post">
			<input type="hidden" name="id" value="${driver.id}">
			<table class="table table-hover table-condensed">
				<tr>
					<th>账号</th>
				    <td>
					   <input name="account" type="text" value="${driver.account}" readonly="readonly" style="width:184px;" class="easyui-validatebox span2" data-options="required:true">
				    </td>
				</tr>
				<tr>
					<th>原密码</th>
				    <td>
					   <input name="password" type="password"   style="width:184px;" class="easyui-validatebox span2" data-options="required:true">
				    </td>
				</tr>
				<tr>
					<th>新密码</th>
				    <td>
					   <input name="newPassword" id="newPassword" type="password"  style="width:184px;" class="easyui-validatebox span2" data-options="required:true">
				    </td>
				</tr>
				<tr>
					<th>确认密码</th>
				    <td>
					   <input name="confirmPassword" id="confirmPassword" type="password"  style="width:184px;" class="easyui-validatebox span2" data-options="required:true">
				    </td>
				</tr>
				
			 </table>
		</form>
	</div>
</div>