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
	
	 //表单
	 $('#form').form({
			url : '${pageContext.request.contextPath}/paramConfigController/add',
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
     
	
});

</script>

<div class="easyui-layout" data-options="fit:true,border:false" id="addPageConfig">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;">
		<form id="form" method="post">
			<input type="hidden" name="id" value="${id}">
			<table class="table table-hover table-condensed">
				<tr>
					<th>参数名</th>
					<td>
						<input name="paramName" type="text" maxlength="20" style="width:200px;" class="easyui-validatebox span2" data-options="required:true">
					</td>
					
					<th>参数值</th>
				    <td>
				    	<input name="paramValue" type="text" maxlength="50" style="width:280px;" class="easyui-validatebox span2" data-options="required:true">
				    </td>
				</tr>
				<tr>
					<th>项目</th>
				    <td>
					   	<select id="project" name="projId" class="easyui-combobox" data-options="width:210,height:29,editable:false,panelHeight:120,required:true">
							<c:forEach items="${projects}" var="de">
								<option value="${de.id}">${de.projName}</option>
							</c:forEach>
					   	 </select>
				    </td>
				    <th>参数类型</th>
				    <td>
				    	<input name="paramType" type="text"  style="width:280px;" class="easyui-validatebox span2" data-options="required:true">
				    </td>
				</tr>
				<tr>
					<th>参数描述</th>
				    <td>
					   <input name="paramDescribe" type="text"  style="width:200px;" class="easyui-validatebox span2" data-options="required:true">
				    </td>
				    <th></th>
			        <td>
			        </td>
				</tr>
			 </table>
		</form>
	</div>
</div>