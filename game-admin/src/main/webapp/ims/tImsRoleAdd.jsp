<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/imsRoleController/insertRole',
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
	
	jQuery.ajax({
		url:'${pageContext.request.contextPath}/ProjectController/findAll',
		type:'POST',
		dataType:'json',
		async:false,
		success:function(data){
			var myObject =data;
			var a= "<option value=\'\'>请选择项目</option>";
			//var a='';
            for (var i = 0; i < myObject.length; i++) {
				 a+="<option value=\'"+myObject[i].id+"\'>"+myObject[i].projName+"</option>";
			 }
            $("#projId").html(a);
		}
	}); 

 	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				<tr>
					<th>角色名称</th>
					<td><input id="name" name="name" type="text" placeholder="请输入角色名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				    <th>描述</th>
					<td><input id="describes" name="describes" type="text" placeholder="请输入描述" class="easyui-validatebox span2"  value=""></td>
				</tr>
			    <tr>
			       <th>项目名称</th>
			       <td>
			          <select id="projId" name="projId" class="easyui-combobox"  data-options="width:140,height:29,editable:false"></select>
			       </td>
			       <th>&nbsp;</th>
			       <td>&nbsp;</td>
			    </tr>
			</table>
		</form>
	</div>
</div>