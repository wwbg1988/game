<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
parent.$.messager.progress('close');
	$(function() {
	
		$('#form').form({
			url : '${pageContext.request.contextPath}/fieldsController/edit',
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
					//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为resource.jsp页面预定义好了
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');
			        parent.$.messager.show({
						title : '提示',
						msg : result.msg
					});
					parent.$.modalDialog.handler.dialog('close');
				}
			}
		});
	});
	
	

	$("#isdiys").combobox({
		onSelect : function(n, o) {

		},
		onLoadSuccess : function() {

			var s = $("#types").val();
			if (s == 1) {
				$('#isdiys').combobox('enable');
			} else {
				$('#isdiys').combobox('disable');
			}
		}
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				<tr>
					<th>编号</th>
					<td><input name="id" type="text" class="span2" value="${fieldsDto.id}" readonly="readonly"></td>
					<th>参数描述</th>
					<td><input name="paramDesc"  value="${fieldsDto.paramDesc}" type="text" placeholder="请输入参数描述" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<th>长度</th>
					<td><input name="length" type="text" class="span2" value="${fieldsDto.length}" placeholder="请输入字段长度"></td>
				
					<th>高度</th>
					<td><input name="height"  value="${fieldsDto.height}" type="text" placeholder="请输入字段高度" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				
				</tr>
			
			    <tr>
					<th>正则校验</th>
					<td><input name="pattern" type="text" class="span2" value="${fieldsDto.pattern}" placeholder="请输入正则校验"></td>
						<th>参数名称</th>
					<td>
				    <input name="paramName"  value="${fieldsDto.paramName}" type="text" placeholder="请输入参数名称" class="easyui-validatebox span2" data-options="required:true" value="">
					</td>
				
					
				
				</tr>
				   <tr>
				   	<th>字段类型</th>
					    <td><select  id="types" name="type" class="easyui-combobox" data-options="width:140,height:29,editable:false" readonly="readonly">
						    <option value="1" <c:if test="${1 == fieldsDto.type}">selected="selected"</c:if> >text</option>
						    <option value="2" <c:if test="${2 == fieldsDto.type}">selected="selected"</c:if> >checkbox</option>
						    <option value="3" <c:if test="${3 == fieldsDto.type}">selected="selected"</c:if> >hidden</option>
						    <option value="4" <c:if test="${4 == fieldsDto.type}">selected="selected"</c:if> >img</option>
						    <option value="5" <c:if test="${5 == fieldsDto.type}">selected="selected"</c:if> >password</option>
						    <option value="6" <c:if test="${6 == fieldsDto.type}">selected="selected"</c:if> >radio</option>
						    <option value="7" <c:if test="${7 == fieldsDto.type}">selected="selected"</c:if> >reset</option>
						    <option value="8" <c:if test="${8 == fieldsDto.type}">selected="selected"</c:if> >file</option>
						    <option value="9" <c:if test="${9 == fieldsDto.type}">selected="selected"</c:if> >label</option>
						    <option value="10" <c:if test="${10 == fieldsDto.type}">selected="selected"</c:if> >select</option>
						    <option value="11" <c:if test="${11 == fieldsDto.type}">selected="selected"</c:if> >textarea</option>
						    </select>
						</td>
					<th>是否自定义字段</th>
					<td><select id="isdiys" name="isdiy" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:55">
						  <option value="1" <c:if test="${true == fieldsDto.isdiy}">selected="selected"</c:if> >是</option>
						  <option value="0" <c:if test="${false == fieldsDto.isdiy}">selected="selected"</c:if> >否</option>
						</select></td>
					
				</tr>
				<tr>
						<th>是否必填</th>
					<td><select name="isneed" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:55">
						    <option value="1" <c:if test="${true == fieldsDto.isneed}">selected="selected"</c:if> >是</option>
						    <option value="0" <c:if test="${false == fieldsDto.isneed}">selected="selected"</c:if> >否</option>
					    </select></td>
						<th>是否加密</th>
					<td>
						<select name="isencrypt" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:55">
						  <option value="1" <c:if test="${true == fieldsDto.isencrypt}">selected="selected"</c:if> >是</option>
						  <option value="0" <c:if test="${false == fieldsDto.isencrypt}">selected="selected"</c:if> >否</option>
						</select>
				    </td>
				</tr>
					<tr>
				<th>是否独占一行</th>
					<td>
						<select name="isuniline" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:55">
						  <option value="1" <c:if test="${true == fieldsDto.isuniline}">selected="selected"</c:if> >是</option>
						  <option value="0" <c:if test="${false == fieldsDto.isuniline}">selected="selected"</c:if> >否</option>
						</select>
				    </td>
					<th>字段数据类型</th>
					 <td><select name="dataType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:106" >
						    <option value="1" <c:if test="${1 == fieldsDto.dataType}">selected="selected"</c:if> >int</option>
						    <option value="2" <c:if test="${2 == fieldsDto.dataType}">selected="selected"</c:if> >string</option>
						    <option value="3" <c:if test="${3 == fieldsDto.dataType}">selected="selected"</c:if> >float</option>
						    <option value="4" <c:if test="${4 == fieldsDto.dataType}">selected="selected"</c:if> >date</option>
					    </select>
					 </td>
				 </tr>
				    <tr>
					<th>排序号</th> 
					<td><input name="orderId" type="text" class="easyui-validatebox span2" value="${fieldsDto.orderId}"  placeholder="请输入排序号" data-options="required:true"></td>
					<th></th>
					<td>
				    </td>
				</tr>
			</table>
		</form>
	</div>
</div>
