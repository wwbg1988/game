<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/deptLevelController/insert',
			onSubmit : function() {
				/*var age = $("#age").val();
				if(age==''||isNaN(age)){
					parent.$.messager.alert('错误', '年龄必须为数字', 'error');
				}
				var depts*/
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
	jQuery.ajax({
		url:'${pageContext.request.contextPath}/deptController/findAll',
		type:'POST',
		dataType:'json',
		async:false,
		success:function(data){
			var myObject =data;
			//var a= "<option value=\'-1\'>请选择</option>";
			var a='';
            for (var i = 0; i < myObject.length; i++) {
				 a+="<option value=\'"+myObject[i].id+"\'>"+myObject[i].deptName+"</option>";
				 console.log(a);
            }
            $("#depts").html(a);
		}
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				
			
				<tr>
					<th>部门等级名称</th>
					<td><input id="levelName" name="levelName" type="text" placeholder="请输入部门等级名称" class="easyui-validatebox span2" data-options="required:true"></td>
					
				</tr>
				<tr>
				  <th>部门等级</th>
					<td>
					<select id="level" name="level" class="span2" style="width:131px;">
						  <option value="1" >1级</option>
						  <option value="2" >2级</option>
						  <option value="3" >3级</option>
						  <option value="4" >4级</option>
						  <option value="5" >5级</option>
						  <option value="6" >6级</option>
						  <option value="7" >7级</option>
						  <option value="8" >8级</option>
						  <option value="9" >9级</option>
						  <option value="10" >10级</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>