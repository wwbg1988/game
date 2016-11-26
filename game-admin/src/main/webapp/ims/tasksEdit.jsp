<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/tImsTasksController/updateTasks',
			onSubmit : function() {
				var levelName = $("#levelName").val();
				if(levelName==''){
					parent.$.messager.alert('错误', '部门等级名称不能为空', 'error');
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
		   <input id="id" name="id" type="hidden" value="${tDto.id}" />
			<table class="table table-hover table-condensed">
			
			    <tr>
			        <th>任务节点名称</th>
					<td><input id="name" name="name" value="${tDto.name}" type="text" placeholder="请输入任务节点名称" class="easyui-validatebox span2" data-options="required:true" style="width:267px;"></td>
			        <th></th>
			        <td>&nbsp;</td>
			    </tr>
			
				<tr>
				   <!--  <th>任务节点对应表单</th>
					<td>
					<select id="formId" name="formId" class="easyui-combobox"  data-options="width:140,height:29,editable:false" >
					  <c:forEach items="${forms}" var="pr">
								<option value="${pr.id}" <c:if test="${pr.id == tDto.formId}">selected="selected"</c:if>>${pr.name}</option>
					  </c:forEach>   
					</select>
				    </td>  -->
				    <th>任务节点类型</th>
					<td>
					<select id="type" name="type" class="easyui-combobox"  data-options="width:140,height:29,editable:false,panelHeight:'auto'" >
					      <option value="1" <c:if test="${1 == tDto.type}">selected="selected"</c:if>>1_开始节点</option>
					      <option value="2" <c:if test="${2 == tDto.type}">selected="selected"</c:if>>2_任务节点</option>
					      <option value="3" <c:if test="${3 == tDto.type}">selected="selected"</c:if>>3_结束</option>
					</select>
					</td>
					  <th>表单类型</th>
				     <td>
				        <select id="countersign" name="countersign" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
				        <option value="0" <c:if test="${0 == tDto.countersign}">selected="selected"</c:if> >0_不会签</option>
				        <option value="1" <c:if test="${1 == tDto.countersign}">selected="selected"</c:if> >1_会签</option>
				        <option value="2" <c:if test="${2 == tDto.countersign}">selected="selected"</c:if> >2_多人重复提交表单</option>
				        </select>
				     </td>
				</tr>
								
			</table>
		</form>
	</div>
</div>