<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/fieldsController/add',
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
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
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
			}
		});
	
	});
	
	//级联下拉框
	$("#type").combobox({
        onSelect:function(n,o){
        
      if(n.value==1){
         
        	$('#isdiy').combobox('enable');
            $("#isdiy").combobox("setValue","1");
        }else{
        	$('#isdiy').combobox('disable');
            $("#isdiy").combobox("setValue","0");
       }
      }
     });
	


</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
					<tr>
				    <th>排序号</th>
					<td><input name="orderId" type="text" class="easyui-validatebox span2" placeholder="请输入排序号" data-options="required:true"></td>
					<th>正则校验</th>
					<td><input name="pattern" type="text" class="easyui-validatebox span2" placeholder="请输入正则校验" data-options="required:false"></td>
					
				</tr>
				<tr>
				<th>长度</th>
					<td><input name="length" type="text" class="easyui-validatebox span2" placeholder="请输入字段长度" data-options="required:true"></td>
				
					<th>高度</th>
					<td><input name="height"  type="text" placeholder="请输入字段高度" class="easyui-validatebox span2" data-options="required:true" value=""></td>
					
				</tr>
			
			    <tr>
					<th>参数描述</th>
					<td><input name="paramDesc" type="text" placeholder="请输入参数描述" class="easyui-validatebox span2" data-options="required:true" value=""></td>
					<th>参数名称</th>
					<td>
				    <input name="paramName"  type="text" placeholder="请输入参数名称" class="easyui-validatebox span2" data-options="required:true" value="">
					</td>
				
				
				
				</tr>
				   <tr>
				   	<th>字段类型</th>
					    <td><select id="type" name="type" class="easyui-combobox" data-options="width:140,height:29,editable:false">
						    <option value="1" >text</option>
						    <option value="2" >checkbox</option>
						    <option value="3" >hidden</option>
						    <option value="4" >img</option>
						    <option value="5" >password</option>
						    <option value="6" >radio</option>
						    <option value="7" >reset</option>
						    <option value="8" >file</option>
						    <option value="9" >label</option>
						    <option value="10">select</option>
						    <option value="11">textarea</option>
						    </select>
						</td>
					<th>是否自定义字段</th>
					<td><select id="isdiy" name="isdiy" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:55" >
					      <option value="0" >否</option>
					      <option value="1" >是</option>
					    
					     </select></td>
				
				</tr>
				<tr>
					<th>是否必填</th>
					<td><select name="isneed" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:55">
						    <option value="1">是</option>
						    <option value="0">否</option>
					    </select></td>
					    	<th>是否加密</th>
					<td>
						<select name="isencrypt" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:55">
						  <option value="0">否</option>
						  <option value="1">是</option>
						
						</select>
				    </td>
					
				</tr>
					<tr>
					<th>是否独占一行</th>
					<td>
						<select name="isuniline" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:55">
						   <option value="1" >是</option>
						   <option value="0" >否</option>
						
						
						</select>
				    </td>
					<th>字段数据类型</th>
					 <td><select name="dataType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:106">
						    <option value="1" >int</option>
						    <option value="2" >string</option>
						    <option value="3" >float</option>
						    <option value="4" >date</option>
					    </select>
					 </td>
				 </tr>
			  <tr style="display: none">
					<th>所属表单id</th>
					<th>编号</th>
					<td><input name="id" type="text" class="span2" value="${fieldsDto.id}" readonly="readonly"></td>
					<td>
					<input name="formId" type="text" class="span2" value="${fieldsDto.formId}" readonly="readonly">
				    </td>
				</tr>
				<tr style="display: none">
					<th>项目id</th>
					<td><input name="projId" type="text" class="span2" value="${fieldsDto.projId}" readonly="readonly"></td>
					<th>流程id</th>
					<td>
					<input name="procId" type="text" class="span2" value="${fieldsDto.procId}" readonly="readonly">
				    </td>
				</tr>
			</table>
		</form>
	</div>
</div>