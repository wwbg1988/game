<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/tImsTasksController/insertTask',
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

					
					//$('#formDataGrid').datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					var urls='/tImsTasksController/manager';	
					var iframe = '<iframe src="'+ urls + '" frameborder="0" style="border:0;width:100%;height:98%;"></iframe>';
					var t = $('#index_tabs');
						var opts = {
								title : "任务节点管理",
								closable : true,
								iconCls :'',
								content : iframe,
								border : false,
								fit : true
							};
							if (t.tabs('exists',"任务节点管理")) {
								
								t.tabs('select', "任务节点管理");
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
	
 	jQuery.ajax({
		url:'${pageContext.request.contextPath}/ProjectController/findAll',
		type:'POST',
		dataType:'json',
		async:false,
		success:function(data){
			var myObject =data;
			var a= "<option value=\'\'>请选择项目ID</option>";
			//var a='';
            for (var i = 0; i < myObject.length; i++) {
				 a+="<option value=\'"+myObject[i].id+"\'>"+myObject[i].projName+"</option>";
			 }
            $("#projId").html(a);
		}
	}); 
 	
    
	jQuery.ajax({
		url:'${pageContext.request.contextPath}/tImsProcessController/findProcess?projId='+$("#projId").val(),
		type:'POST',
		dataType:'json',
		async:false,
		success:function(data){
			var myObject =data;
			var a= "<option value=\'\'>请选择</option>";
			//var a='';
            for (var i = 0; i < myObject.length; i++) {
				 a+="<option value=\'"+myObject[i].id+"\'>"+myObject[i].procName+"</option>";
			 }
            $("#procId").html(a);
		}
	}); 
 
	jQuery.ajax({
		url:'${pageContext.request.contextPath}/formsController/findAll',
		type:'POST',
		dataType:'json',
		async:false,
		success:function(data){
			var myObject =data;
			var a= "<option value=\'\'>请选择</option>";
			//var a='';
            for (var i = 0; i < myObject.length; i++) {
				 a+="<option value=\'"+myObject[i].id+"\'>"+myObject[i].name+"</option>";
			 }
            $("#formId").html(a);
		}
	}); 
 
 	//级联下拉框
	//$("#projId").combobox({
    //    onSelect:function(n,o){
    //    	$("#procId").combobox('reload',"${pageContext.request.contextPath}/tImsProcessController/findProcess?projId="+n.value);
    //    	$("#procId").combobox("setValue",'请选择流程ID');
    //    }
    // });
	//$('#procId').combobox({  
	//    valueField:'id',  
	//    textField:'procName'  
	//})  

 	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
		 <input type="hidden" id="proid"  name="proid" value="${proid}"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th>任务节点名称</th>
					<td><input id="name" name="name" type="text" placeholder="请输入任务节点名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				    <th>表单类型</th>
				        <td>
				        <select id="countersign" name="countersign" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
				        <option value="">请选择表单类型</option>
				        <option value="0">0_不会签</option>
				        <option value="1">1_会签</option>
				        <option value="2">2_多人重复提交表单</option>
				        </select>
				     </td>
				</tr>
			<!-- 	<tr>
				    <th>所属项目ID</th>
				    <td>
				    <select id="projId" name="projId" class="easyui-combobox"  data-options="width:140,height:29,editable:false"></select>
				    </td>
					<th>所属项目流程ID</th>
					<td>
					<select id="procId" name="procId" class="easyui-combobox"  data-options="width:140,height:29,editable:false,panelHeight:'auto'" ></select>
					</td>
				</tr>   -->
				<tr>
				<!--    <th>任务节点对应表单</th>
					<td>
					<select id="formId" name="formId" class="easyui-combobox"  data-options="width:140,height:29,editable:false" ></select>
				    </td>  --> 
				    <th>任务节点类型</th>
					<td>
					<select id="type" name="type" class="easyui-combobox"  data-options="width:140,height:29,editable:false,panelHeight:'auto'" >
					      <option value="">请选择节点类型</option>
					    <c:if test="${0 == star_flag}"> <option value="1">1_开始节点</option></c:if>
					      <option value="2">2_任务节点</option>
					      <option value="3">3_结束</option>
					</select>
					</td>
				    <th>&nbsp;</th>
				    <td>&nbsp;</td>
				</tr>
				<tr>
				<!--    <th>节点状态</th>
				     <td>
				        <select id="state" name="state" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'"> 
				        <option value="">请选择节点状态</option>
				        <option value="0">0—未激活</option>
				        <option value="1">1—办理中(激活)</option>
				        <option value="2">2—已办</option>
				        <option value="3">3-否决</option>
				        <option value="4">4-退回</option>
				        </select>
				     </td>    -->  
				    
				
				</tr>
			</table>
		</form>
	</div>
</div>