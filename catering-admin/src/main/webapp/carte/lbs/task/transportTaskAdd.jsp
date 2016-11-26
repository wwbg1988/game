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
        	 $('#supplier').combobox('clear');
        	 $('#destPlace').combobox('clear');

        	//供应商
             $('#supplier').combobox({
                 disabled: false,
                 url: '${pageContext.request.contextPath}/supplierController/findSuppliersBy?projectId=' + record.value,
                 valueField: 'id',
                 textField: 'supplierName'
             });
             
             
 			 //目的地        	 
             $('#destPlace').combobox({
                 disabled: false,
                 url: '${pageContext.request.contextPath}/transportDestController/getTransportDestDtosBy?projectId=' + record.value,
                 valueField: 'id',
                 textField: 'address'
             });
             

         }
     });
	
	 $('#project').combobox('setValue',null);
	 
	 //表单
	 $('#form').form({
			url : '${pageContext.request.contextPath}/transportTaskController/add',
			onSubmit : function() {
				$("#supplierName").val($('#supplier').combobox('getText'));
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

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;">
		<form id="form" method="post">
			<input type="hidden" name="supplierName" id="supplierName">
			<table class="table table-hover table-condensed">
				<tr>
					<th>项目名称</th>
				    <td>
					     <select id="project" name="projectId"  class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120,required:true">
							<c:forEach items="${projects}" var="de">
								<option value="${de.id}">${de.projName}</option>
							</c:forEach>
					   	 </select>
				    </td>
				    <th>供应商</th>
					<td>
					  	<select id="supplier" name="adminSupplierUserId" class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120,required:true">
					   	</select>
					</td>
				</tr>
				<tr>
					<th>任务名称</th>
				    <td>
				    	<input name="taskName" type="text"  style="width:184px;" class="easyui-validatebox span2" data-options="required:true">
				    </td>
					<th>目的地</th>
				    <td>
					   	<select id="destPlace" name="transportDestId" class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120,required:true"  >
					   	 </select>
				    </td>
				</tr>
				<tr>
					<th>货物</th>
				    <td>
				    	<input name="merchandise" type="text"  style="width:184px;" class="easyui-validatebox span2" data-options="required:true">
				    </td>
				</tr>
			 </table>
		</form>
	</div>
</div>