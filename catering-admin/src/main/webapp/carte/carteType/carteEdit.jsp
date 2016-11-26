<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	parent.$.messager.progress('close');
	$(function() {

		$('#form').form({
			url : '${pageContext.request.contextPath}/carteController/edit',
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
					$("#carteEdit").dialog('close');
					parent.$.messager.show({
						title : '提示',
						msg : result.msg
					});
					//parent.$.modalDialog.handler.dialog('close');
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
						value="${carteDto.id}" readonly="readonly" style="width:160px;"></td>
				</tr>
			
				<tr>
					<th>菜品名称</th>
					<td><input name="carteName" type="text"
						value="${carteDto.carteName}" readonly="readonly" class="easyui-validatebox span2"
						data-options="required:true" style="width:160px;"></td>
				
				</tr>

                 <tr>	
                 <th>菜品热量</th>
				 <td><input name="calorie" 	value="${carteDto.calorie}" placeholder="请输入菜品热量" class="easyui-validatebox span2" style="width: 160px; height: 29px;" required="required" >
				 </td>
				 </tr>
                 <tr>	
                 <th>菜品营养</th>
				 <td>
				 <input name="sustenance" value="${carteDto.sustenance}" placeholder="请输入菜品营养" class="easyui-numberspinner" style="width: 160px; height: 29px;" required="required" data-options="editable:false,min:1,max:5">
				 </td>
				 </tr>
			</table>
		</form>
	</div>
</div>
