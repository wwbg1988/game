<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	parent.$.messager.progress('close');
	$(function() {

		$('#form').form({
			url : '${pageContext.request.contextPath}/sensitiveController/edit',
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
					//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为resource.jsp页面预定义好了
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');
					parent.$.messager.show({
						title : '提示',
						msg : result.msg
					});
					parent.$.modalDialog.handler.dialog('close');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
			 	<tr style="display: none;">
					<th>编号</th>
					<td><input name="id" type="text" class="span2"
						value="${sensitiveDto.id}" readonly="readonly" style="width:160px;"></td>
				</tr>
				   <tr>
					<th>敏感词名称</th>
					<td>
						<input name="sensitiveName" type="text" value="${sensitiveDto.sensitiveName}" style="width:140px;" class="easyui-validatebox span2" data-options="required:true">
					</td>
				</tr>
				<tr>
				   <th>是否预警</th>
				   <td>
						<select  name="warning" class="easyui-combobox" data-options="width:154,height:29,editable:false,panelHeight:55" >
						      <option value="1" >是</option>
						      <option value="0" >否</option>
					    </select>
				   </td>
				</tr>
				<tr>
			 	   <th>所属食堂</th>
				   <td>
					   <select id="cafetoriumId" name="cafetoriumId" class="easyui-combobox" disabled="disabled" data-options="width:154,height:29,editable:false,panelHeight:120"  >
					    <c:forEach items="${cafeList}" var="de">
					    		<c:if test="${de.cafeName == sensitiveDto.cafetoriumName}">
									<option value="${de.id}" selected="selected">${de.cafeName}</option>
					    		</c:if>
					    		<c:if test="${de.cafeName != sensitiveDto.cafetoriumName}">
					    			<option value="${de.id}">${de.cafeName}</option>
					    		</c:if>
							</c:forEach>
					   </select>
				   </td>
				</tr>
			</table>
		</form>
	</div>
</div>
