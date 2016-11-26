<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	parent.$.messager.progress('close');
	$(function() {
		$('#form').form({
			url : '${pageContext.request.contextPath}/companyController/edit',
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
						value="${companyDto.id}" readonly="readonly" style="width:160px;"></td>
				</tr>
				   <tr>
					<th>公司名称</th>
					<td>
						<input name="companyName" type="text" style="width:140px;" value="${companyDto.companyName}" class="easyui-validatebox span2" data-options="required:true">
				    	<input id="projId" name="projId" type="hidden" value="${companyDto.projId}"/>
					</td>
				</tr>
		  <%--   	<c:if test="${companyDto.flag == 0}">
					<tr>
						<th>项目名称</th>
					    <td>
						     <select id="projId" name="projId" class="easyui-combobox" data-options="width:154,height:29,editable:false,panelHeight:120,required:true"  >
								<c:forEach items="${listproject}" var="de">
									<c:if test="${companyDto.projId == de.id}">
										<option value="${de.id}" selected="selected">${de.projName}</option>
									</c:if>
									<c:if test="${companyDto.projId != de.id}">
										<option value="${de.id}" >${de.projName}</option>
									</c:if>
								</c:forEach>
						   	 </select>
					    </td>
					</tr>
		    	</c:if>
		    	<c:if test="${companyDto.flag == 1}">
					<input id="projId" name="projId" type="hidden" value="${companyDto.projId}"/>
		    	</c:if> --%>
				<tr>
					<th>公司电话</th>
					<td>
						<input name="mobileNo" type="text" style="width:140px;" value="${companyDto.mobileNo}" class="easyui-validatebox span2" data-options="required:false">
					</td>
				</tr>
				<tr>
					<th>公司邮箱</th>
					<td>
						<input name="email" type="text" style="width:140px;" value="${companyDto.email}" class="easyui-validatebox span2" data-options="required:false">
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
