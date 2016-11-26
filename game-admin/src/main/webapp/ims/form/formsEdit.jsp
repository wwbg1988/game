<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
parent.$.messager.progress('close');
	$(function() {
	

		$('#form').form({
			url : '${pageContext.request.contextPath}/formsController/edit',
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
				}else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
	
	/* //级联下拉框
	$("#projects").combobox({
        onSelect:function(n,o){
        	$("#tasks").combobox('reload',"${pageContext.request.contextPath}/tImsTasksController/findAll?projId="+n.value);
        	$("#tasks").combobox("setValue",'请选择');
        }
    });
	$('#tasks').combobox({  
	    valueField:'id',  
	    textField:'name'  
	})   */


</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				<tr style="display: none;">
					<th>编号</th>
					<td><input name="id" type="text" class="span2" value="${formsDto.id}" readonly="readonly"></td>
					
				</tr>
				<tr>
				<th>表单名称</th>
					<td><input name="name" type="text" placeholder="请输入表单名称"  value="${formsDto.name}" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<%-- <th>表单类型</th>
					<td><select name="type" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:80">
					      <option value="1" <c:if test="${1 == formsDto.type}">selected="selected"</c:if> >单人表单</option>
						  <option value="2" <c:if test="${2 == formsDto.type}">selected="selected"</c:if> >多人表单</option>
						  <option value="3" <c:if test="${3 == formsDto.type}">selected="selected"</c:if> >重复提交表单</option>
						</select></td> --%>
					<th>任务节点</th>
					<td>
					<select id="tasks" name="taskId" class="easyui-combobox"  data-options="width:140,height:29,editable:false,panelHeight:100" readonly="readonly" >
					<c:forEach items="${tasks}" var="pr">
								<option value="${pr.id}" <c:if test="${pr.id == formsDto.taskId}">selected="selected"</c:if>>${pr.name}</option>
							</c:forEach>
					</select>
					</td>
			
				</tr>
			</table>
		</form>
	</div>
</div>
