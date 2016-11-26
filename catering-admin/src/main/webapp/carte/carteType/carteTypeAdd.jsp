<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/carteTypeController/add',
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
					var urls='/carteTypeController/manager';	
					var iframe = '<iframe src="'+ urls + '" frameborder="0" style="border:0;width:100%;height:98%;"></iframe>';
					var t = $('#index_tabs');
						var opts = {
								title : "菜单管理",
								closable : true,
								iconCls :'',
								content : iframe,
								border : false,
								fit : true
							};
							if (t.tabs('exists',"菜单管理")) {
								
								t.tabs('select', "菜单管理");
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
					
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
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

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				<tr style="display: none;">
					<th>编号</th>
					<td><input name="id" type="text" class="span2"
						value="${cartTypeDto.id}" readonly="readonly" style="width:160px;"></td>
				</tr>
				   <tr>
					<th>所属食堂</th>
					<td><input name="cafetoriumName" type="text" style="width:160px;" readonly="readonly"
						class="easyui-validatebox span2"   value="${cartTypeDto.cafetoriumName}"
						data-options="required:true"></td>
				</tr>
				<tr>
					<th>菜品类型名称</th>
					<td><input name="carteTypeName" type="text" style="width:160px;"
						class="easyui-validatebox span2" placeholder="请输入菜品类型名称"
						data-options="required:true"></td>
				</tr>
				<tr style="display: none;">
					<th>食堂id</th>
					<td><input name="cafetoriumId" type="text" style="width:160px;" value="${cartTypeDto.cafetoriumId}"
						class="easyui-validatebox span2"
						data-options="required:true"></td>
				</tr>
			
				   <tr>
				    <th>菜品最大数量</th>
				
				    <td><input name="upperLimit" value="1" placeholder="请输入菜品最大数量" class="easyui-numberspinner" style="width: 160px; height: 29px;" required="required" data-options="editable:false,min:1"></td>
			   </tr>
			</table>
		</form>
	</div>
</div>