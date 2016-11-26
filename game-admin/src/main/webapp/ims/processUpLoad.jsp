<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/tImsProcessController/create',
			items : [
		   	         {
		               	 xtype: 'filefield',	        
			            emptyText: 'Select an image',
			            fieldLabel: '图片',
			            name: 'image',
			            buttonText: '请选择',
			            anchor:'95%',
			            buttonConfig: {
			                iconCls: 'upload-icon'
			            }
			        }, {
		                   fieldLabel: '名称',
		                   name: 'name',
		                   anchor:'95%'
		               }
		    	],
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
					parent.$.messager.alert('提示', result.msg, 'info');
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
		<form id="form" method="post"  enctype="multipart/form-data">
			<input name="projid" type="hidden" value="${projid}" />
			<table class="table table-hover table-condensed">

			    
			     <tr>
			     <th>图片上传</th>
			     <td>
			      <input type="file" name="image" id="image"  class="easyui-validatebox" />  
			      </td>
			    </tr>
			    
			   
				
			</table>
		</form>
	</div>
</div>