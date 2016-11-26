<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(function(){
	parent.$.messager.progress('close');
	
	 //$('#projId option[value="默认"]').remove();
	 //$("#projId").click(function(){
		// alert('ok');
	 //});

	
	
	//联动
	 $('#project').combobox({
         onSelect: function (record) {
        	 $('#project option[value="默认"]').remove();
        	 
             $('#cafetoriumId').combobox({
                 disabled: false,
                 url: '${pageContext.request.contextPath}/cafrtoriumController/findCafetoriumsBy?projId=' + record.value,
                 valueField: 'id',
                 textField: 'cafeName'
             });
             
             $('#cafetoriumId option[value="默认"]').remove();
             
             $('#cafetoriumId').combobox('select', '请选择食堂');
         }
     });
	 
	 //表单
	 $('#form').form({
			url : '${pageContext.request.contextPath}/pageConfigController/add',
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

</script>

<div class="easyui-layout" data-options="fit:true,border:false" id="addPageConfig">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				<tr>
					<th>配置项描述</th>
					<td>
						<input name="name" type="text" maxlength="14" style="width:184px;" class="easyui-validatebox span2" data-options="required:true">
					</td>
					
					<th>项目名称</th>
				    <td>
					     <select id="project" name="projectId" class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120,required:true">
					     	<option selected="selected" value='默认'>请选择项目</option>
							<c:forEach items="${projects}" var="de">
								<option value="${de.id}">${de.projName}</option>
							</c:forEach>
					   	 </select>
				    </td>
				</tr>
				<tr>
					<th>餐厅名称</th>
				    <td>
					   	<select id="cafetoriumId" name="cafetoriumId" class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120,required:true"  >
					   		<option value="默认" selected="selected">请先选择项目</option>
					   	 </select>
				    </td>
				    <th>启用url</th>
				    <td>
				    	<input name="enabledUrl" type="text"  style="width:184px;" class="easyui-validatebox span2" data-options="required:true">
				    </td>
				</tr>
				<tr>
					<th>非启用url</th>
				    <td>
					   <input name="notEnabledUrl" type="text"  style="width:184px;" class="easyui-validatebox span2" data-options="required:true">
				    </td>
				    <th>是否启用</th>
			        <td>
			      		<select name="isEnabled" class="easyui-combobox" data-options="width:184,height:29,editable:false,panelHeight:120,required:true"  >
			      			<option value="1" selected="selected">是</option>
					   		<option value="0">否</option>
					   	</select>
			        </td>
				</tr>
			 </table>
		</form>
	</div>
</div>