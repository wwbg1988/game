<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	parent.$.messager.progress('close');
	$(function() {
		$('#form').form({
			url : '${pageContext.request.contextPath}/configController/configScoreValveConfUpdate',
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
					<td>
						<input name="id" type="text" class="span2" value="${ConfDto.id}" readonly="readonly" style="width:160px;">
					</td>
				</tr>
				<tr>
					<th>食堂名称</th>
					<td>
						<input name="cafetoriumName" type="text" class="span2" value="${ConfDto.cafetoriumName}" readonly="readonly" style="width:160px;">
					</td>
				</tr>
				<tr>
					<th>条数阀值</th>
					<td>
						<input name="valveCount"  value="${ConfDto.valveCount}" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false,min:1">
					</td>
				</tr>
				
				
				<tr>
					<th>阀值等级</th>
					<td>
						<input name="level"  value="${ConfDto.level}" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false,min:1">
					</td>
				</tr>
				<%-- <tr>
					<th>百分比阀值</th>
					<td>
						<input name="valvePercent"  value="${ConfDto.valvePercent}" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false,min:1">
					</td>
				</tr> --%>
				<tr>
				    <th style="  padding-top: 21px;">百分比阀值</th>
					<td style="height:150;padding-top: 21px;">
				    <input class="easyui-slider" name="valvePercent"  value="${ConfDto.valvePercent}" style="width:180px;"   
                     data-options="showTip:true,rule:[0,'|',25,'|',50,'|',75,'|',100]" />  
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
