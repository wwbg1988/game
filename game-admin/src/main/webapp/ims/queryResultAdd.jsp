<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/queryResultController/insertResult',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					//parent.$.modalDialog.handler.dialog('close');
					//parent.$.messager.alert('提示', result.msg, 'info');
					 $('#dd').dialog('close');
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
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
		     <input  id="queryId" name="queryId" type="hidden" value="${queryId}"/>
			<table class="table table-hover table-condensed">
				
			    <tr>
			    	<th>所属字段</th>
					<td>
					<select id="fieldsId" name="fieldsId" class="easyui-combobox"  data-options="width:140,height:29,editable:false,panelHeight:'auto'" >
					   <option value="">请选择返回字段</option>
					   <c:forEach items="${listFields}" var="de">
						 <option value="${de.id}"   >${de.paramDesc}</option>
					   </c:forEach>
					</select>
					</td>
					<th>字段排序</th>
					<td>
					  <input id="serialNum" name="serialNum" class="easyui-numberspinner" style="width:80px;" value="1"  required="required" data-options="min:1,max:100,editable:false">  
					</td>
			    </tr>
			</table>
		</form>
	</div>
</div>