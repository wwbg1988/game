<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		//父菜单
		$('#pid').combotree({
			url : '${pageContext.request.contextPath}/workMenuController/tree',
			parentField : 'pid',
			lines : true,
			//panelHeight : '100px',
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});

		$('#form').form({
			url : '${pageContext.request.contextPath}/workMenuController/insertMenu',
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
					parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
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
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
	
			<table class="table table-hover table-condensed">
				<tr>
					<th>菜单名称</th>
					<td><input id="name" name="name" type="text" placeholder="菜单名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>
		
				</tr>
				<tr>
					<th>菜单备注</th>
					<td><input id="remark" name="remark" type="text" placeholder="请输入菜单名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>
		
				</tr>
			<tr>
					<th>上级菜单</th>
					<td><select id="pid" name="pid"
						style="width: 140px; height: 29px;"></select><img
						src="${pageContext.request.contextPath}/style/images/extjs_icons/cut_red.png"
						onclick="$('#pid').combotree('clear');" /></td>
				</tr>
			</table>
		</form>
	</div>
</div>