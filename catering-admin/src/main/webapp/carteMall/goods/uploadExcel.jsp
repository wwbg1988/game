<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
$(function() {
	parent.$.messager.progress('close');
	
	$('#form').form({
		url : '${pageContext.request.contextPath}/goodsController/uploadExcel',
		onSubmit : function() {
			/*var age = $("#age").val();
			if(age==''||isNaN(age)){
				parent.$.messager.alert('错误', '年龄必须为数字', 'error');
			}
			var depts*/
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
<div>
<!-- <h5>导入Excel</h5> -->
<hr>
<form id="form"  method="post" enctype="multipart/form-data">
	<input type="file" name="excel">
</form>
</div>