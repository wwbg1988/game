<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		$('#iconCls').combobox({
			data : $.iconData,
			formatter : function(v) {
				return $.formatString('<span class="{0}" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>{1}', v.value, v.value);
			},
			value : '${menuDto.icon}'
		});

		$('#pid').combotree({
			url : '${pageContext.request.contextPath}/resourceController/allsTree',
			parentField : 'pid',
			lines : true,
			//panelHeight : 'auto',
			value : '${menuDto.pid}',
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});

		$('#form').form({
			url : '${pageContext.request.contextPath}/resourceController/edit',
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
					parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为resource.jsp页面预定义好了
					parent.layout_west_tree.tree('reload');
				    parent.$.modalDialog.handler.dialog('close');
					parent.layout_west_race_tree.tree('reload');
			        parent.$.messager.show({
							title : '提示',
							msg : result.msg
						});
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				
			    <tr hidden="true">
				<th>编号</th>
					<td><input name="id" type="text" class="span2" value="${menuDto.id}" readonly="readonly"></td>
				</tr>
				<tr>
				    <th>菜单名称</th>
					<td><input name="name" type="text" placeholder="请输入菜单名称" class="easyui-validatebox span2" data-options="required:true" value="${menuDto.name}"></td>
				    <th >所属导航</th>
				    <td><select id="tabType" name="tabType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:55" >
					 	    <option value="1" <c:if test="${1 == menuDto.tabType}">selected="selected"</c:if> >赛事流程菜单</option>
						    <option value="0" <c:if test="${0 == menuDto.tabType}">selected="selected"</c:if> >系统菜单</option>
					   </select></td>
				</tr>
				<tr>
					<th>菜单路径</th>
					<td><input name="url" type="text" placeholder="请输入资源路径" class="easyui-validatebox span2" value="${menuDto.url}"></td>
					<th>菜单类型</th>
					<td><select name="menuTypeId" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<c:forEach items="${menuTypeList}" var="menuType">
								<option value="${menuType.id}" <c:if test="${menuType.id == menuDto.menuTypeId}">selected="selected"</c:if>>${menuType.name}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<th>排序</th>
					<td><input name="seq" value="${menuDto.seq}" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false"></td>
					<th>上级菜单</th>
					<td><select id="pid" name="pid" style="width: 140px; height: 29px;"></select><img src="${pageContext.request.contextPath}/style/images/extjs_icons/cut_red.png" onclick="$('#pid').combotree('clear');" /></td>
				</tr>
				<tr>
					<th>菜单图标</th>
					<td colspan="3"><input id="iconCls" name="icon" style="width: 375px; height: 29px;" data-options="editable:false" /></td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3"><textarea name="remark" rows="" cols="" class="span5">${menuDto.remark}</textarea></td>
				</tr>
			</table>
		</form>
	</div>
</div>
