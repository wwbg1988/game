<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(function(){
	parent.$.messager.progress('close');
	
	 //表单
	 $('#form').form({
			url : '${pageContext.request.contextPath}/transportTaskController/dispatching/add',
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

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;">
		<form id="form" method="post">
			<input type="hidden" name="id" value="${task.id}">
			<table class="table table-hover table-condensed">
				<tr>
					<th>项目名称</th>
				    <td>
					     <input value="${task.projectName}"  readonly="readonly" data-options="width:200,height:29,editable:false,panelHeight:120,required:true">
				    </td>
				    <th>供应商</th>
					<td>
					  	<input value="${task.adminSupplierUserName}" readonly="readonly">
					</td>
				</tr>
				<tr>
					<th>任务名称</th>
				    <td>
				    	<input value="${task.taskName}" readonly="readonly">
				    </td>
				    <th>目的地</th>
				    <td>
					   	<input value="${task.destPlace}" readonly="readonly">
				    </td>
				</tr>
				<tr>
					<th>货物</th>
				    <td>
				    	<input value="${task.merchandise}" readonly="readonly">
				    </td>
				    <th>出发地</th>
				    <td>
					   	<input 
					   		<c:choose>
						   		<c:when test='${task.departPlace==null || task.departPlace==""}'>
						   			value="${task.supplierAddress}"
						   		</c:when> 
					   			<c:otherwise>
					   				value="${task.departPlace}"
					   			</c:otherwise>
					   		</c:choose>
					   		name="departPlace">
				    </td>
				</tr>
				<tr>
					<th>配送人</th>
				    <td>
					   	 <select id="driver" name="driverId" class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120,required:true">
					   	 	<c:forEach items="${drivers}" var="de">
								<option value="${de.id}" <c:if test="${de.id==task.driverId}"> selected</c:if>>${de.name}</option>
							</c:forEach>
					   	 </select>
				    </td>
				    <th></th>
				    <td>
				    </td>
				</tr>
			 </table>
		</form>
	</div>
</div>