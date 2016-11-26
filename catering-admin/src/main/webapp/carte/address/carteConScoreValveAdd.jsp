<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/cafrtoriumController/SaveCarteConScoreValve',
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
				<tr style="display: none;">
					<th>编号</th>
					<td>
						<input name="cafetoriumId" type="text" class="span2" value="${cafetoriumId}" readonly="readonly" style="width:160px;">
				    </td>
				</tr>
				   <tr>
					<th>所属食堂</th>
					<td>
						<input name="cafetoriumName" type="text" style="width:160px;" readonly="readonly" class="easyui-validatebox span2" value="${caftrotiumName}" data-options="required:true">
					</td>
				</tr>
				<tr>
					<th>阀值等级</th>
					<td>
						<input name="level" value="1" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false,min:1">
					</td>
				</tr>
				<tr>
					<th>条数预警阀值</th>
					<td>
						<input name="valveCount" value="1" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false,min:1">
					</td>
				</tr>
				<tr>
				    <th>百分比预警阀值</th>
				    <td>
				    	<input name="valvePercent" value="1" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false,min:1">
				    </td>
			   </tr>
			</table>
		</form>
	</div>
</div>