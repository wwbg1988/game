<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
parent.$.messager.progress('close');
$(function() {

	parent.$.messager.progress('close');
	$('#form').form({
		url : '${pageContext.request.contextPath}/addressController/addUserAddress',
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
			result = $.parseJSON(result);
			parent.$.messager.progress('close');
			if (result.success) {
			    
		        parent.$.modalDialog.openner_treeGrid.treegrid('reload');
				parent.$.modalDialog.handler.dialog('close');

				parent.$.messager.show({
					title : '提示',
					msg : "编辑负责人成功！"
				});
			}else{
				parent.$.messager.alert('错误', result.msg,
				'error');
			}
			
		}
	});
	
	});
	
    //负责人拉框查询
/* 
	jQuery.ajax({
		url : '${pageContext.request.contextPath}/imsUserController/findAll',
		type : 'POST',
		dataType : 'json',
		async : false,
		success : function(data) {
			var myObject = data;
			var a = "";

			for (var i = 0; i < myObject.length; i++) {
				a += "<option value=\'"+myObject[i].id+"\'>"
						+ myObject[i].name + "</option>";
				console.log(a);
			}
			$("#userId").html(a);
		}
	}); */
</script>


<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;">
	<form id="form" method="post">
		<table class="table table-hover table-condensed">
				<tr style="display: none;">
					<th>编号</th>
					<td><input name="id" type="text" class="span2"
						value="${addressUserDto.id}" readonly="readonly" style="width:160px;"></td>
				</tr>
			    <tr style="display: none;">
					<th>所属区域id</th>
					<td><input name="addressId" type="text" style="width:160px;" readonly="readonly"
						class="easyui-validatebox span2"   value="${addressUserDto.addressId}"
						data-options="required:true"></td>
				</tr>
				<tr>
				<th>负责人</th>
				<td><select id="userId" name="userId"
						class="easyui-combobox"
						data-options="width:150,height:29,editable:false,panelHeight:110">
						  <c:forEach items="${userList}" var="pr">
								<option value="${pr.id}" <c:if test="${pr.id == addressUserDto.userId}">selected="selected"</c:if>>${pr.name}</option>
							</c:forEach>
					</select>
				</td>	
				</tr>
				<tr>
				<th>区域类型</th>
				<td><select  name="addressType" class="easyui-combobox"  data-options="width:150,height:29,editable:false,panelHeight:40" >
					       <c:if test="${0 == addressUserDto.addressType}">
					        <option value="0" selected="selected">总公司</option>
					       </c:if>
					          <c:if test="${1 == addressUserDto.addressType}">
					        <option value="1" selected="selected">大区</option>
					       </c:if>
					          <c:if test="${2 == addressUserDto.addressType}">
					        <option value="2" selected="selected">省</option>
					       </c:if>
					      <c:if test="${3 == addressUserDto.addressType}">
					       <option value="3" selected="selected">市</option>
					      </c:if>
				
				    </select></td>
				</tr>
			</table>

  </form>
  </div></div>