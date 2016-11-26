<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {

	//  var sta = $("#last").val();
	//  alert(sta);
	//  $("#startDateS").datetimebox("setValue",'3/4/2010 02:03:56');  
	  
		
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/queryController/updateQuery',
			onSubmit : function() {
				var levelName = $("#levelName").val();
				if(levelName==''){
					parent.$.messager.alert('错误', '流程名称名称不能为空', 'error');
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
		url:'${pageContext.request.contextPath}/ProjectController/findAll',
		type:'POST',
		dataType:'json',
		async:false,
		success:function(data){
			
			var myObject =data;
			var a= "<option value=\'\'>请选择项目</option>";
		    var projId = $("#projId").val();
            for (var i = 0; i < myObject.length; i++) {
            	
            	if(projId==myObject[i].id){
            		a+="<option  selected=\"selected\"  value=\'"+myObject[i].id+"\'>"+myObject[i].projName+"</option>";
            	}else{
            		a+="<option value=\'"+myObject[i].id+"\'>"+myObject[i].projName+"</option>";
            	}
            	
			 }
            $("#projectId").html(a);
		}
	}); 

	//  流程  级联下拉框
	$("#projectId").combobox({
        onSelect:function(n,o){
        	$("#processId").combobox('reload',"${pageContext.request.contextPath}/tImsProcessController/findProcess?projId="+n.value);
        	$("#processId").combobox("setValue",'请选择流程');
        }
     });
	$('#processId').combobox({  
	    valueField:'id',  
	    textField:'procName'  
	})  
	
	//   节点 级联下拉框
	$("#processId").combobox({
        onChange:function(newValue, oldValue){
        	$("#taskId").combobox('reload',"${pageContext.request.contextPath}/tImsTasksController/findAll?procId="+newValue);
          	$("#taskId").combobox("setValue",'请选择节点');
        }
     });
	$('#taskId').combobox({  
	    valueField:'id',  
	    textField:'name'  
	})
	
	//节点动作  级联下拉框
	
	$("#taskId").combobox({
		  onChange:function(newValue, oldValue){
	        	$("#actionId").combobox('reload',"${pageContext.request.contextPath}/queryController/findAction?taskId="+newValue);
	          	$("#actionId").combobox("setValue",'请选择节点动作');
	        }
     });
	$('#actionId').combobox({  
	    valueField:'id',  
	    textField:'name'  
	})
 	
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
		   <input  id="id" name="id" type="hidden" value="${qDto.id}"/>
		   <input  id="projId" name="projId" type="hidden" value="${qDto.projectId}" />
			<table class="table table-hover table-condensed">
			    <tr>
			        <th >名称</th>
			        <td><input id="name" name="name" type="text" class="asyui-validatebox span2" value="${qDto.name}"  ></td>
			        <th>分组名称</th>
					<td><input id="groupName" name="groupName" value="${qDto.groupName}" type="text"  class="easyui-validatebox span2"  ></td>
			    </tr>
			      <tr>
			       <th>项目名称</th>
			       <td>
			          <select id="projectId" name="projectId" class="easyui-combobox"  data-options="width:140,height:29,editable:false">
			          </select>
			       </td>
			    	<th>所属项目流程</th>
					<td>
					<select id="processId" name="processId" class="easyui-combobox"  data-options="width:140,height:29,editable:false,panelHeight:'auto'" >
					   <option value="">请选择流程</option>
					   <c:forEach items="${listProcess}" var="de">
						 <option value="${de.id}" <c:if test="${qDto.processId == de.id}">selected="selected"</c:if>  >${de.procName}</option>
					   </c:forEach>
					</select>
					</td>
			    </tr>
			    <tr>
			       <th>任务节点</th>
			       <td>
			          <select id="taskId" name="taskId" class="easyui-combobox"  data-options="width:140,height:29,editable:false">
			           <option value="">请选择节点</option>
			             <c:forEach items="${listtasks}" var="det">
						  <option value="${det.id}" <c:if test="${qDto.taskId == det.id}">selected="selected"</c:if>  >${det.name}</option>
					     </c:forEach>
			          </select>
			       </td>
			    	<th>节点动作</th>
					<td>
					  <select id="actionId" name="actionId" class="easyui-combobox"  data-options="width:140,height:29,editable:false,panelHeight:'auto'" >
					    <option value="">请选择节点动作</option>
					    <c:forEach items="${listaction}" var="adet">
						  <option value="${adet.id}" <c:if test="${qDto.actionId == adet.id}">selected="selected"</c:if>  >${adet.name}</option>
					     </c:forEach>
					  </select>
					</td>
			    </tr>
				
			</table>
		</form>
	</div>
</div>