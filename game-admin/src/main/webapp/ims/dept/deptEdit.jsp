<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
parent.$.messager.progress('close');
var deptId='${deptDto.id}';
var projId='${deptDto.projId}';
	$(function() {
       $('#pid').combotree({
			url : '${pageContext.request.contextPath}/deptController/tree?deptId='+deptId+'&projId='+projId,
			parentField : 'pid',
			value : '${deptDto.pid}',
			lines : true,
			//panelHeight : '100px',
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});
		
		$('#form').form({
			url : '${pageContext.request.contextPath}/deptController/edit',
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
					parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为resource.jsp页面预定义好了
				    parent.$.modalDialog.handler.dialog('close');
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
				<tr style="display: none;">
					<th>编号</th>
					<td><input name="id" type="text" class="span2" value="${deptDto.id}" readonly="readonly"></td>
					
				</tr>
				
				<tr>
					<th>部门编号</th>
					<td><input name="deptCode" type="text" class="span2" value="${deptDto.deptCode}" readonly="readonly"></td>
			    </tr>
				
				<tr>
				<th>部门名称</th>
					<td><input name="deptName"  value="${deptDto.deptName}" type="text" placeholder="请输入部门名称" class="easyui-validatebox span2" data-options="required:true" value=""></td></tr>
				<tr>
					<tr style="display: none;">
					<th>部门编号</th>
					<td><input name="projId" type="text" class="span2" value="${deptDto.projId}" readonly="readonly"></td>
					
				</tr>
				<th>上级部门</th>
					<td><select id="pid" name="pid" style="width: 140px; height: 29px;"></select><img src="${pageContext.request.contextPath}/style/images/extjs_icons/cut_red.png" onclick="$('#pid').combotree('clear');" /></td>
				</tr>
			   
			</table>
		</form>
	</div>
</div>
