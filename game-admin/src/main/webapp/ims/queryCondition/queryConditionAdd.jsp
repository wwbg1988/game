<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/queryConditionController/add',
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
					parent.$.modalDialog.openner_dataGrid.datagrid('reload')
				    //parent.$.modalDialog.handler.dialog('close');
					 $('#dd').dialog('close');
					//parent.$.messager.alert('提示', result.msg, 'info');
					parent.$.messager.show({
						title : '提示',
						msg : result.msg
					});
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
				parent.$.modalDialog.openner_dataGrid.datagrid('reload');
			}
		});
	});
	$('#opt').combobox({  
	    valueField:'0',  
	    textField:'1'  
	}) 
  //  字段描述 查询条件  级联下拉框
	$("#fields").combobox({
		onLoadSuccess : function() {
		
		},
        onSelect:function(n,o){
        
        	var strs= new Array(); //定义一数组 
        	strs=n.text.split(":"); //字符分割 
        	var type=strs[0];
        	 var a='';
           if(type=='请选择过滤字段'){
       		$("#opt").combobox("loadData",""); 
       		$("#opt").combobox('setValues','');
           } else if(type==1||type==3||type==4){//类型如果是int(1),float(3),date(4)
              var data = [['1', 'EQ'], ['2', 'LT'], ['3', 'LTE'], ['4', 'GT'], ['5', 'GTE'],['7','ORDERBY_DESC'],['8','ORDERBY_ASC']];  
        		$("#opt").combobox("loadData",data);  
        	}else{
        		//类型:string(2)
        		var data = [['1', 'EQ'], ['6', 'LIKE'],['7','ORDERBY_DESC'],['8','ORDERBY_ASC']];  
        		$("#opt").combobox("loadData",data); 
        	
        	}
        }
     });
  
	 
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				<tr style="display: none;">
					<th>编号</th>
					<td><input name="id" type="text" class="span2" value="${conditionDto.id}" readonly="readonly"></td>
					
				</tr>
					<tr>
				
					<th>所属项目</th>
				    <td><input name="projName" type="text" class="span2" value="${conditionDto.projName}" readonly="readonly"></td>
			   </tr>
				<tr>
		
					<th>过滤名称</th>
					<td><input name="name" type="text" placeholder="请输入过滤名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
		       <tr>
				    <th>排序号</th>
				
				    <td><input name="serialNum" value="1" placeholder="请输入排序号" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false,min:1"></td>
			   </tr>
			
				
				<tr  style="display: none;">
				   <th>项目id</th>
				   <td><input name="projectId" type="text" class="span2" value="${conditionDto.projectId}" readonly="readonly"></td>
			    </tr>
			    	<tr  style="display: none;">
				   <th>queryid</th>
				   <td><input name="queryId" type="text" class="span2" value="${conditionDto.queryId}" readonly="readonly"></td>
			    </tr>
				<tr>
				    <th>字段描述</th>
					<td>
				    <select id="fields" name="fieldsId" class="easyui-combobox"  data-options="width:140,height:29,editable:false,panelHeight:120" >
							<option value=''>请选择过滤字段</option>
							<c:forEach items="${fields}" var="field">
							   <option value="${field.id}">${field.dataType}:${field.paramDesc}</option>
							</c:forEach>
							</select>
					</td>
				</tr>
				
				<tr>
				   	<th>查询条件</th>
					    <td><select id="opt" name="opt" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:120,required:true">
				 	        </select>
						</td>
				</tr>
			 </table>
		</form>
	</div>
</div>