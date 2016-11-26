<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
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
	jQuery.ajax({
		url:'${pageContext.request.contextPath}/ProjectController/findAll',
		type:'POST',
		dataType:'json',
		async:false,
		success:function(data){
			var myObject =data;
			
			var a= "<option value=\'-1\'>请选择</option>";
			//var a='';
            for (var i = 0; i < myObject.length; i++) {
				 a+="<option value=\'"+myObject[i].id+"\'>"+myObject[i].projName+"</option>";

            }
            $("#projectss").html(a);
		}
	});
	$("#projectss").combobox({
        onSelect:function(n,o){
        	$("#taskIds").combobox('reload',"${pageContext.request.contextPath}/tImsTasksController/findAll?projId="+n.value);
        	$("#nextTaskIds").combobox('reload',"${pageContext.request.contextPath}/tImsTasksController/findAll?projId="+n.value);
        	$("#taskIds").combobox("setValue",'');
        	$("#nextTaskIds").combobox("setValue",'');
        }
    });
	$('#taskIds').combobox({  
	    valueField:'id',  
	    textField:'name'  
	})  
		$('#nextTaskIds').combobox({  
	    valueField:'id',  
	    textField:'name'  
	}) 
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				<tr>
					<th>动作名称</th>
					<td><input name="name" type="text" placeholder="请输入动作名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>					
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th>项目名称</th>
					<!-- <td><select id="projectss" name="projId" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" ></select></td> -->
					<td><input type="text" value="${proName}" readonly="readonly"/></td>
					<th>动作类型</th>
					<td><select id="type" name="type" class="easyui-combobox"  data-options="width:140,height:29,editable:false,panelHeight:'auto'" ><option value="1">发起/启动</option><option value="2">办理</option><option value="3">退回</option><option value="4">办结</option><option value="5">自定义动作</option></select></td>
				</tr>
				<tr>
					<th>下一任务结点</th>
					<td><select id="nextTaskIds" name="nextTaskId" class="easyui-combobox"  data-options="width:140,height:29,editable:false,panelHeight:'auto'" ></select></td>
					<th>url地址</th>
					<td><input name="actionUrl" type="text" placeholder="请输入url地址" class="easyui-validatebox span2" data-options="" ></td>
				</tr>
				<!-- <tr>
				<th>url地址</th>
					<td><input name="actionUrl" type="text" placeholder="请输入url地址" class="easyui-validatebox span2" data-options="" ></td>
					<th></th>
				<td></td>
				</tr>-->
				
			</table>
		</form>
	</div>
</div>