<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
	/* 	$('#nextId').css('display','none');
		$('#actionUrlDiv').css('display','none'); */
		
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/actionsController/add',
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
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});

	});
	
	$("#type").combobox({
        onSelect:function(n,o){
        	if(n.value==4 ){
			    $('#actionUrlDiv').removeAttr("hidden");
			    $('#nextId').attr('hidden','true');
			}else if (n.value==3 || n.value==2 ||n.value==6){	
			
				$('#actionUrlDiv').attr('hidden','true'); 
				$('#actionUrl').val('');
				$('#nextId').attr('hidden','true');
			}else{
				$('#nextId').removeAttr("hidden");
			    $('#actionUrlDiv').attr('hidden','true');
				$('#actionUrl').val('');
			}
        }
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
			<tr>
					<th width="104px;">动作名称</th>
					<td><input name="name" type="text" placeholder="请输入动作名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>	
					
			</tr>
			<tr>
					<th>项目名称</th>
					<td><input type="text" value="${proName}" readonly="readonly"/><input type="hidden" id="projId" name="projId" value="${proId}"/></td>				
			</tr>
			<tr>
			
					<th>动作类型</th>
					<td><select id="type" name="type" class="easyui-combobox"  data-options="width:140,height:29,editable:false,panelHeight:'auto'" ><option value="2">更新</option><option value="3">回退</option><option value="4">自定义动作</option><option value="5">提交</option><option value="6">拒绝</option></select></td>
		    </tr>
		    <tr id="nextId" hidden="true">			
					<th >下一任务结点</th>
					<td><select id="nextTaskIds" name="nextTaskId" class="easyui-combobox"  data-options="width:140,height:29,editable:false,panelHeight:'auto'" >
					
					<c:forEach items="${taskList}" var="de">
								<option value="${de.id}" >${de.name}</option>
							</c:forEach>
					</select></td>
		    </tr>
		    <tr id="actionUrlDiv"  hidden="false">
				<th >url地址</th>
					<td><input id="actionUrl" name="actionUrl" type="text" placeholder="请输入url地址" class="easyui-validatebox span2" ></td>
				</tr>
		
				
			</table>
		</form>
	</div>
</div>