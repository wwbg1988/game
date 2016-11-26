<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/tImsProcessController/insertProcess',
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
					var urls='/tImsProcessController/manager';	
					var iframe = '<iframe src="'+ urls + '" frameborder="0" style="border:0;width:100%;height:98%;"></iframe>';
					var t = $('#index_tabs');
						var opts = {
								title : "流程管理",
								closable : true,
								iconCls :'',
								content : iframe,
								border : false,
								fit : true
							};
							if (t.tabs('exists',"流程管理")) {
								
								t.tabs('select', "流程管理");
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
	

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post"  enctype="multipart/form-data">
			<input id="projid"  name="projid"  type="hidden" value="${projid}"/>
			<table class="table table-hover table-condensed">
				<tr>
					
				    <th>流程名称</th>
					<td><input id="procName" name="procName" type="text" placeholder="请输入流程名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				    <th>流程描述</th>
				    <td>
				    <input id="describes" name="describes" class="easyui-validatebox span2" placeholder="请输入流程描述" data-options="required:false"></input>
				    </td>
				</tr>
				
				<tr>
				    <th>流程开始日期</th>
					<td>
			        <select id="startDateS" name="startDateS"  class="easyui-datetimebox"  data-options="width:140,height:29,editable:false" ></select>
				    </td>
				    <th>流程结束日期</th>
					<td>
			        <select id="endDateS" name="endDateS"  class="easyui-datetimebox"  data-options="width:140,height:29,editable:false" ></select>
				    </td>
				</tr>
		
				
			</table>
		</form>
	</div>
</div>