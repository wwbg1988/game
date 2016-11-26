<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {

	//  var sta = $("#last").val();
	//  alert(sta);
	//  $("#startDateS").datetimebox("setValue",'3/4/2010 02:03:56');  
	  
		
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/queryActionController/updateAction',
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
					// parent.$.modalDialog.handler.dialog('close');
					 $('#dd1').dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
	

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
		   <input  id="id" name="id" type="hidden" value="${qad.id}"/>
		   <input  id="projectId" name="projectId" type="hidden" value="${qad.projectId}" />
		   <input id="queryId" name="queryId" type="hidden" value="${qad.queryId}" />
			<table class="table table-hover table-condensed">
			    <tr>
			        <th >动作名称</th>
			        <td><input id="name" name="name" type="text" class="asyui-validatebox span2" value="${qad.name}"  ></td>
			        <th>动作URL地址</th>
					<td><input id="url" name="url" value="${qad.url}" type="text"  class="easyui-validatebox span2" data-options="required:true" ></td>
			    </tr>
			     
			</table>
		</form>
	</div>
</div>