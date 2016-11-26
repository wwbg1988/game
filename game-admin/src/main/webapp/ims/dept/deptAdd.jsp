<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
var projId='${deptDto.projId}';
	$(function() {
		parent.$.messager.progress('close');
		
		$('#pid').combotree({
			url : '${pageContext.request.contextPath}/deptController/tree?projId='+projId,
			parentField : 'pid',
			lines : true,
			//panelHeight : '100px',
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});
		
		$('#form').form({
			url : '${pageContext.request.contextPath}/deptController/add',
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
					parent.$.modalDialog.handler.dialog('close');
				   //parent.layout_west_tree.tree('reload');
					var urls='/deptController/manager';	
					var iframe = '<iframe src="'+ urls + '" frameborder="0" style="border:0;width:100%;height:98%;"></iframe>';
					var t = $('#index_tabs');
						var opts = {
								title : "部门管理",
								closable : true,
								iconCls :'',
								content : iframe,
								border : false,
								fit : true
							};
							if (t.tabs('exists',"部门管理")) {
								
								t.tabs('select', "部门管理");
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
					parent.$.messager.show({
						title : '提示',
						msg : result.msg
					});
				}else{
					parent.$.messager.alert('错误',result.msg, 'error');
				}
			}
		});
	});

	jQuery.ajax({
		url : '${pageContext.request.contextPath}/deptLevelController/findAll',
		type : 'POST',
		dataType : 'json',
		async : false,
		success : function(data) {
			var myObject = data;
			//var a= "<option value=\'-1\'>请选择</option>";
			var a = '';
			for (var i = 0; i < myObject.length; i++) {
				a += "<option value=\'"+myObject[i].id+"\'>"
						+ myObject[i].levelName + "</option>";
			}
			$("#deptLevels").html(a);
		}
	});
	jQuery.ajax({
		url : '${pageContext.request.contextPath}/ProjectController/findAll',
		type : 'POST',
		dataType : 'json',
		async : false,
		success : function(data) {
			var myObject = data;
			//var a= "<option value=\'-1\'>请选择</option>";
			var a = '';
			for (var i = 0; i < myObject.length; i++) {
				a += "<option value=\'"+myObject[i].id+"\'>"
						+ myObject[i].projName + "</option>";
				console.log(a);
			}
			$("#projects").html(a);
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
						value="${deptDto.id}" readonly="readonly"></td>

				</tr>
				<tr>
					<th>部门名称</th>
					<td><input name="deptName" type="text" placeholder="请输入部门名称"
						class="easyui-validatebox span2" data-options="required:true"
						value=""></td>
				</tr>
				<tr>
				<th>上级部门</th>
					<td><select id="pid" name="pid" style="width: 140px; height: 29px;"></select><img src="${pageContext.request.contextPath}/style/images/extjs_icons/cut_red.png" onclick="$('#pid').combotree('clear');" /></td>
				</tr>
				<tr>
					<th>所属项目</th>
					<td>
					<input name="projName" type="text" class="easyui-validatebox span2" readonly="readonly" value="${deptDto.projName}"> 
		<!-- 				<select id="projects" name="projId" class="easyui-combobox span2" 
						data-options="width:140,height:32,editable:false,panelHeight:100"></select> -->
					</td>
				</tr>
					<tr style="display: none;">
					<th>项目编号</th>
					<td><input name="projId" type="text" class="span2"
						value="${deptDto.projId}" readonly="readonly"></td>

				</tr>
			</table>
		</form>
	</div>
</div>