<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {

	//  var sta = $("#last").val();
	//  alert(sta);
	//  $("#startDateS").datetimebox("setValue",'3/4/2010 02:03:56');  
	  
		
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/tImsProcessController/updatePro',
			onSubmit : function() {
				var levelName = $("#levelName").val();
				if(levelName==''){
					parent.$.messager.alert('错误', '流程名称名称不能为空', 'error');
				}
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
		   <input  id="last" type="hidden" value="${stad}" />
		   <input  id="id" name="id" type="hidden" value="${pDto.id}"/>
		   <input id="projId"  name="projId" type="hidden" value="${pDto.projId}" />
			<table class="table table-hover table-condensed">
			    <tr>
			        <th>流程名称</th>
			        <td><input id="procName" name="procName" type="text" class="span2" value="${pDto.procName}"  style="width: 267px;"></td>
			        <th>流程描述</th>
					<td><input id="describes" name="describes" value="${pDto.describes}" type="text" placeholder="请输入描述" class="easyui-validatebox span2" data-options="required:true" style="width:267px;"></td>
			    </tr>
			    <tr>
					<th>流程开始日期</th>
					<td> <input id="startDateS" name="startDateS" class="easyui-datetimebox"  data-options="width:140,height:29,editable:false" value="${stad}"></input></td>
					<th>流程结束日期</th>
					<td>
					    <input id="endDateS" name="endDateS" class="easyui-datetimebox"  data-options="width:140,height:29,editable:false" value="${endd}"></input>
					</td>
				</tr>
				
			</table>
		</form>
	</div>
</div>