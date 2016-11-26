<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	parent.$.messager.progress('close');
	$(function() {

		$('#form').form({
			url : '${pageContext.request.contextPath}/cafrtoriumController/edit',
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
				}else{
					
					parent.$.messager.alert('错误', result.msg,
					'error');
				}
			}
		});
	});
	
	   //自定义验证
	  $.extend($.fn.validatebox.defaults.rules, {
	  phoneRex: {
	    validator: function(value){
	    var rex=/^1[3-8]+\d{9}$/;
	    //var rex=/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	    //区号：前面一个0，后面跟2-3位数字 ： 0\d{2,3}
	    //电话号码：7-8位数字： \d{7,8
	    //分机号：一般都是3位数字： \d{3,}
	     //这样连接起来就是验证电话的正则表达式了：/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/		 
	    var rex2=/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	    if(rex.test(value)||rex2.test(value))
	    {
	      // alert('t'+value);
	      return true;
	    }else
	    {
	     
	       return false;
	    }
	      
	    },
	    message: '请输入正确电话或手机格式'
	  }
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
			<tr style="display: none;">
					<th>编号</th>
					<td><input name="id" type="text" class="span2"
						value="${cafetoriumDto.id}" readonly="readonly"
						style="width: 160px;"></td>
				</tr>

				<tr>
					<th>食堂名称</th>
					<td><input name="cafeName" type="text" style="width: 160px;" 	value="${cafetoriumDto.cafeName}"
						class="easyui-validatebox span2" placeholder="请输入食堂名称"
						data-options="required:true"></td>
					<th>食堂电话</th>
					<td><input name="mobileNo" type="text" class="easyui-validatebox span2" 	value="${cafetoriumDto.mobileNo}"
						style="width: 160px;" placeholder="请输入食堂电话" data-options="required:true,validType:'phoneRex'"></td>
				</tr>
				<tr>
					<th>食堂地址</th>
					<td><input name="address" type="text" style="width: 160px;" 	value="${cafetoriumDto.address}"
						class="easyui-validatebox span2" placeholder="请输入食堂地址"
						data-options="required:true"></td>
					<th>食堂邮箱</th>
					<td>
						<input  name="email"   type="text" value="${cafetoriumDto.email}" class="easyui-validatebox span2"  placeholder="请输入食堂邮箱" data-options="validType:'email',required:true" style="width:160px;">
					</td>
				</tr>
				<tr>
				 <th>所属公司</th>
					<td>
						<select id="companyId" name="companyId" class="easyui-combobox"  data-options="width:170,height:29,editable:false,panelHeight:100">
					        <c:forEach items="${companyList}" var="pr">
								<option value="${pr.id}" <c:if test="${pr.id == cafetoriumDto.companyId}">selected="selected"</c:if>>${pr.companyName}</option>
							</c:forEach>
					    </select>
				    </td>
				    <th>所属区域</th>
				    <td><input name="addressName" type="text" class="span2"
						value="${cafetoriumDto.addressName}" readonly="readonly"
						style="width: 160px;"></td>
				</tr>
				<tr>
				<th>食堂负责人</th>
				<td>	
				    <select id="userId" name="userId" class="easyui-combobox"  data-options="width:170,height:29,editable:false,panelHeight:100">
					        <c:forEach items="${userList}" var="pr">
								<option value="${pr.id}" <c:if test="${pr.id == cafetoriumDto.userId}">selected="selected"</c:if>>${pr.name}</option>
							</c:forEach>
					 </select>
					 <img src="${pageContext.request.contextPath}/style/images/extjs_icons/cut_red.png" onclick="$('#userId').combobox('clear');" />
			     </td>
				 </tr>
					<tr style="display: none;">
					<th>区域id</th>
					<td><input name="addressId" type="text" class="span2"
						value="${cafetoriumDto.addressId}" readonly="readonly"
						style="width: 160px;"></td>
						<th></th>
						<td><input id="projId" name="projId" type="hidden" value="${cafetoriumDto.projId}"/></td>
				</tr>
			</table>
		</form>
	</div>
</div>
