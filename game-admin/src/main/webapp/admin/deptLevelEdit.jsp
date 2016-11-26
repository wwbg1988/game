<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/deptLevelController/update',
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
			<table class="table table-hover table-condensed">
			    <tr>
				    <th>编号</th>
				    <td><input name="id" type="text" class="span2" value="${dldto.id}" readonly="readonly" style="width: 267px;"></td>
			   </tr>
			    <tr>
					<th>部门等级名称</th>
					<td><input name="levelName" value="${dldto.levelName}" type="text" placeholder="请输入部门等级名称" class="easyui-validatebox span2" data-options="required:true"></td>
					
				</tr>
				<tr>
				  <th>部门等级</th>
					<td>
					<select name="level" class="span2" style="width:131px;">
						  <option value="1" <c:if test="${1 == dldto.level}">selected="selected"</c:if> >1级</option>
						  <option value="2" <c:if test="${2 == dldto.level}">selected="selected"</c:if> >2级</option>
						  <option value="3" <c:if test="${3 == dldto.level}">selected="selected"</c:if> >3级</option>
						  <option value="4" <c:if test="${4 == dldto.level}">selected="selected"</c:if> >4级</option>
						  <option value="5" <c:if test="${5 == dldto.level}">selected="selected"</c:if> >5级</option>
						  <option value="6" <c:if test="${6 == dldto.level}">selected="selected"</c:if> >6级</option>
						  <option value="7" <c:if test="${7 == dldto.level}">selected="selected"</c:if> >7级</option>
						  <option value="8" <c:if test="${8 == dldto.level}">selected="selected"</c:if> >8级</option>
						  <option value="9" <c:if test="${9 == dldto.level}">selected="selected"</c:if> >9级</option>
						  <option value="10" <c:if test="${10 == dldto.level}">selected="selected"</c:if> >10级</option>
						</select>
					</td>
				</tr>
				
			</table>
		</form>
	</div>
</div>