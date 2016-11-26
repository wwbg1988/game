<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(function(){
	parent.$.messager.progress('close');
	
	 //$('#projId option[value="默认"]').remove();
	 //$("#projId").click(function(){
		// alert('ok');
	 //});

	
	
	//联动
	 $('#project').combobox({
         onSelect: function (record) {
        	 $('#supplier').combobox('clear');
        	//供应商
             $('#supplier').combobox({
                 disabled: false,
                 url: '${pageContext.request.contextPath}/supplierController/findSuppliersBy?projectId=' + record.value,
                 valueField: 'id',
                 textField: 'supplierName'
             });
         }
     });
	 
	 //表单
	 $('#form').form({
			url : '${pageContext.request.contextPath}/transportDriverController/edit',
			onSubmit : function() {
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
	 
	 var projectId = '${projectId}';
	 //初始化
	 if(!!projectId){
		 $('#supplier').combobox({
	         disabled: false,
	         url: '${pageContext.request.contextPath}/supplierController/findSuppliersBy?projectId=' + projectId,
	         valueField: 'id',
	         textField: 'supplierName'
	     });
	 }
	
});

</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;">
		<form id="form" method="post">
			<input type="hidden" name="id" value="${driver.id}">
			<table class="table table-hover table-condensed">
				<tr>
					<th>项目名称</th>
				    <td>
					     <select id="project" name="projectId" class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120,required:true">
							<c:forEach items="${projects}" var="de">
								<option value="${de.id}" <c:if test="${de.id==projectId}">selected</c:if>>${de.projName}</option>
							</c:forEach>
					   	 </select>
				    </td>
				    <th>供应商</th>
					<td>
					  	<select id="supplier" name="adminUserId" class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120,required:true">
					  		<option value="${driver.adminUserId}" selected="selected">${driver.supplierName}</option>
					   	 </select> 
					</td>
				</tr>
				<tr>
					<th>配送人账号</th>
				    <td>
					   <input name="account" type="text" value="${driver.account}" readonly="readonly" style="width:184px;" class="easyui-validatebox span2" data-options="required:true">
				    </td>
				    <th>密码</th>
				    <td>
				    	<input name="password" type="password" readonly="readonly" value="${driver.password}"  style="width:184px;" class="easyui-validatebox span2" data-options="required:true">
				    </td>
				</tr>
				<tr>
					<th>姓名</th>
				    <td>
				    	<input name="name" type="text" value="${driver.name}" style="width:184px;" class="easyui-validatebox span2" data-options="required:true">
				    </td>
					<th>昵称</th>
				    <td>
					   <input name="nickName" type="text" value="${driver.nickName}" style="width:184px;" class="easyui-validatebox span2" data-options="required:true">
				    </td>
				</tr>
				<tr>
					<th>手机号码</th>
				    <td>
				    	<input name="phone" type="text" value="${driver.phone}" style="width:184px;" class="easyui-validatebox span2" data-options="required:true">
				    </td>
				    <th>状态</th>
					<td>
						<select id="state" name="state" class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120">
							<option value="1" <c:if test="${1==driver.state}"> selected</c:if>>有任务</option>
							<option value="0" <c:if test="${0==driver.state}"> selected</c:if>>无任务</option>
				   		</select>
					</td>
				</tr>
			 </table>
		</form>
	</div>
</div>