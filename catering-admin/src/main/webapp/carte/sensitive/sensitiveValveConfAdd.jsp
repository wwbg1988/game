<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/sensitiveController/sensitiveVconfAdd',
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
			<table class="table table-hover table-condensed">
				<c:if test="${flag == 0}">
					<tr>
						<th>项目名称</th>
					    <td>
						     <select id="projId" name="projId" class="easyui-combobox" data-options="width:180,height:29,editable:false,panelHeight:120,required:true"  >
								<c:forEach items="${listproject}" var="de">
									<option value="${de.id}" >${de.projName}</option>
								</c:forEach>
						   	 </select>
					    </td>
					</tr>
				</c:if>
				<c:if test="${flag == 1}">
					<tr>
						<th>项目名称</th>
					    <td>
						   	 <input id="projId" name="projId" value="${proId}" type="hidden"/>
						   	 <input name="proName" value="${proName}" type="text" style="width:160px;" disabled="disabled" class="easyui-validatebox span2" data-options="required:true">
					    </td>
					</tr>
				</c:if>
				<tr style="display: none;">
					<th>编号</th>
					<td><input name="id" type="text" class="span2"
						value="${SensitiveValveConfDto.id}" readonly="readonly" style="width:180px;"></td>
				</tr>
				<tr>
					<th>条数触发的阀值</th>
					<td>
						<input name="valveCount" value="1" class="easyui-numberspinner" style="width: 180px; height: 29px;" required="required" data-options="editable:false,min:1">
					</td>
				</tr>
			    <tr>
					<th>阀值等级</th>
					<td>
						<input name="level" value="1" class="easyui-numberspinner" style="width: 180px; height: 29px;" required="required" data-options="editable:false,min:1">
					</td>
				</tr>
					<tr>
					<th style="  padding-top: 21px;">百分比触发的阀值</th>
					<td style="height:150;padding-top: 21px;">
					<!-- 	<input name="valvePercent" value="1" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false,min:1"> -->
					<input class="easyui-slider" name="valvePercent"  style="width:180px;"   
                     data-options="showTip:true,rule:[0,'|',25,'|',50,'|',75,'|',100]" />  
					</td>
				</tr>
				
			 </table>
		</form>
	</div>
</div>