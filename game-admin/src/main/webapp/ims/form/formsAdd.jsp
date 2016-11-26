<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/formsController/add',
			onSubmit : function() {
				/*var age = $("#age").val();
				if(age==''||isNaN(age)){
					parent.$.messager.alert('错误', '年龄必须为数字', 'error');
				}
				var depts*/
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
				
					//$('#formDataGrid').datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					var urls='/formsController/manager';	
					var iframe = '<iframe src="'+ urls + '" frameborder="0" style="border:0;width:100%;height:98%;"></iframe>';
					var t = $('#index_tabs');
						var opts = {
								title : "表单管理",
								closable : true,
								iconCls :'',
								content : iframe,
								border : false,
								fit : true
							};
							if (t.tabs('exists',"表单管理")) {
								
								t.tabs('select', "表单管理");
								parent.$.messager.progress('close');
							} else {
								t.tabs('add', opts);
							}
					var href = t.tabs('getSelected').panel('options').href;
			
					if(href){/*说明tab是以href方式引入的目标页面*/
						var index = t.tabs('getTabIndex', t.tabs('getSelected'));
						t.tabs('getTab', index).panel('refresh');
					}else{/*说明tab是以content方式引入的目标页面*/
						var frame = t.tabs('getSelected').panel('panel').find('iframe');
						try {
							if (frame.length > 0) {
								for ( var i = 0; i < frame.length; i++) {
									frame[i].contentWindow.document.write('');
									frame[i].contentWindow.close();
									frame[i].src = frame[i].src;
								}
								if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
									try {
										CollectGarbage();
									} catch (e) {
									}
								}
							}
						} catch (e) {
						}
					}
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');
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
	
/*    //项目下拉框查询
	jQuery.ajax({
		url:'${pageContext.request.contextPath}/ProjectController/findAll',
		type:'POST',
		dataType:'json',
		async:false,
		success:function(data){
			var myObject =data;
			var a= "";
		
            for (var i = 0; i < myObject.length; i++) {
				 a+="<option value=\'"+myObject[i].id+"\'>"+myObject[i].projName+"</option>";
				 console.log(a);
            }
            $("#projects").html(a);
		}
	}); */
   
	//任务节点下拉框
/*  	jQuery.ajax({
		url:'${pageContext.request.contextPath}/tImsTasksController/findAll',
		type:'POST',
		dataType:'json',
		async:false,
		success:function(data){
			var myObject =data;
			var a= "<option value=\'-1\'>请选择</option>";
			for (var i = 0; i < myObject.length; i++) {
				 a+="<option value=\'"+myObject[i].id+"\'>"+myObject[i].name+"</option>";
			 }
            $("#tasks").html(a);
		}
	});  */
	
/* 	//级联下拉框
	$("#projects").combobox({
        onSelect:function(n,o){
        	$("#tasks").combobox('reload',"${pageContext.request.contextPath}/tImsTasksController/findAll?projId="+n.value);
        	$("#tasks").combobox("setValue",'请选择');
        }
    });
	$('#tasks').combobox({  
	    valueField:'id',  
	    textField:'name'  
	})  */ 


</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				<tr style="display: none;">
					<th>编号</th>
					<td><input name="id" type="text" class="span2" value="${formsDto.id}" readonly="readonly"></td>
					
				</tr>
				<tr>
		
					<th>表单名称</th>
					<td><input name="name" type="text" placeholder="请输入表单名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
			<!-- 	<tr>
			        <th>表单类型</th>
					<td><select name="type" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:80">
					      <option value="1" >单人表单</option>
						  <option value="2" >多人表单</option>
				          <option value="3" >重复提交表单</option>
						</select>
					</td>
				</tr> -->
				<tr>
				
					<th>任务节点</th>
					<td>
					<!-- data-options="width:140,height:29,editable:false,panelHeight : 'auto'" -->
					<input name="taskName" type="text" value="${formsDto.taskName}" readonly="readonly" class="easyui-validatebox span2" >
					</td>
				</tr>
				<tr style="display: none;">
					<th>项目id</th>
					<td><input name="projId" type="text" class="span2" value="${formsDto.projId}" readonly="readonly"></td>
					<th>流程id</th>
					<td><input name="procId" type="text" class="span2" value="${formsDto.procId}" readonly="readonly"></td>
				</tr>
				<tr style="display: none;">
					<th>任务节点id</th>
					<td><input name="taskId" type="text" class="span2" value="${formsDto.taskId}" readonly="readonly"></td>
					
				</tr>
			 </table>
		</form>
	</div>
</div>