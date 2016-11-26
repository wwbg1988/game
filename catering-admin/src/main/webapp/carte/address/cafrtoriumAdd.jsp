<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form')
				.form(
						{
							url : '${pageContext.request.contextPath}/cafrtoriumController/add',
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
									var urls='/cafrtoriumController/manager';	
									var iframe = '<iframe src="'+ urls + '" frameborder="0" style="border:0;width:100%;height:98%;"></iframe>';
									var t = $('#index_tabs');
										var opts = {
												title : "食堂管理",
												closable : true,
												iconCls :'',
												content : iframe,
												border : false,
												fit : true
											};
											if (t.tabs('exists',"食堂管理")) {
												
												t.tabs('select', "食堂管理");
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
									
								/* 	
									parent.$.modalDialog.openner_dataGrid
											.datagrid('reload'); *///之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
									parent.$.modalDialog.handler
											.dialog('close');
									//parent.$.messager.alert('提示', result.msg, 'info');
									parent.$.messager.show({
										title : '提示',
										msg : result.msg
									});
								} else {
									parent.$.messager.alert('错误', result.msg,
											'error');
								}
							}
						});
	});

	jQuery.ajax({
		url : '${pageContext.request.contextPath}/companyController/findAll',
		type : 'POST',
		dataType : 'json',
		async : false,
		success : function(data) {
			var myObject = data;
			var a = "";
			//var a='';
			for (var i = 0; i < myObject.length; i++) {
				a += "<option value=\'"+myObject[i].id+"\'>"
						+ myObject[i].companyName + "</option>";
			}
			$("#companyId").html(a);
		}
	});
	
    //负责人拉框查询

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
				
				<tr style="display: none;">
					<th>区域id</th>
					<td><input name="addressId" type="text" class="span2"
						value="${cafetoriumDto.addressId}" readonly="readonly"
						style="width: 160px;"></td>
				</tr>
				<tr>
					<th>食堂名称</th>
					<td><input name="cafeName" type="text" style="width: 160px;"
						class="easyui-validatebox span2" placeholder="请输入食堂名称"
						data-options="required:true"></td>
					<th>食堂电话</th>
					<td><input name="mobileNo" type="text" class="easyui-validatebox span2"
						style="width: 160px;" placeholder="请输入食堂电话" data-options="required:true,validType:'phoneRex'"></td>
				</tr>
				<tr>
					<th>食堂地址</th>
					<td><input name="address" type="text" style="width: 160px;"
						class="easyui-validatebox span2" placeholder="请输入食堂地址"
						data-options="required:true"></td>
					<th>食堂邮箱</th>
					<td><input name="email" type="text" class="easyui-validatebox span2" data-options="required:true,validType:'email'"
						style="width: 160px;" placeholder="请输入食堂邮箱"></td>
				</tr>
				<tr>
					<th>所属区域</th>
					<td><input name="addressName" type="text" class="span2"
						value="${cafetoriumDto.addressName}" readonly="readonly"
						style="width: 160px;"></td>
					<th>所属公司</th>
					<td><select id="companyId" name="companyId"
						class="easyui-combobox"
						data-options="width:173,height:29,editable:false,panelHeight:110,required:true"></select>
					</td>
				  </tr>
				  <tr>
				  <c:if test="${1 == superadmin}">
				  	<th>项目名称</th>
				    <td>
					     <select id="projId" name="projId" class="easyui-combobox" data-options="width:154,height:29,editable:false,panelHeight:120,required:true"  >
							<c:forEach items="${listproject}" var="de">
								<option value="${de.id}" >${de.projName}</option>
							</c:forEach>
					   	 </select>
				    </td>
				  </c:if>
				    <c:if test="${0 == superadmin}">
				 
					 <input id="projId" name="projId" value="${projId}"  hidden="true"  data-options="width:154,height:29,editable:false,panelHeight:120,required:true,hidden:true"  >
				   
				  </c:if>
				 
				  	<th>食堂负责人</th>
				<td><select id="userId" name="userId"
						class="easyui-combobox"
						data-options="width:173,height:29,editable:false,panelHeight:110,required:true">
					</select>
				</td>	
				<th></th>
				<td></td>
				  </tr>
			</table>
		</form>
	</div>
</div>