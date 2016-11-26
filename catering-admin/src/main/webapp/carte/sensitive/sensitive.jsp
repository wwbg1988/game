<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>敏感词管理</title>
<jsp:include page="../../inc.jsp"></jsp:include>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/sensitiveController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if
	test="${fn:contains(sessionInfo.resourceList, '/sensitiveController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/sensitiveController/addPage')}">
		<script type="text/javascript">
		$.canAddCarteType = true;
	</script>	
</c:if>
<script type="text/javascript">
	var dataGrid;
	parent.$.messager.progress('close');
	$(function() {
		dataGrid = $('#sendataGrid')
				.datagrid(
						{
							url : '${pageContext.request.contextPath}/sensitiveController/dataGrid',
							fit : true,
							fitColumns : true,
							border : false,
							pagination : true,
							idField : 'id',
							rownumbers : true,
							singleSelect : true,
							pageSize : 10,
							pageList : [ 10, 20, 30, 40, 50 ],
							//sortName : 'deptName',
							//sortOrder : 'asc',
							checkOnSelect : false,
							selectOnCheck : false,
							nowrap : false,
							frozenColumns : [ [ {
								field : 'id',
								title : '编号',
								width : 150,
								hidden : true
							} ] ],
							columns : [ [
									{
										field : 'cafetoriumName',
										title : '食堂名称 ',
										width : 130
									},
									{
										field : 'sensitiveName',
										title : '敏感词名称',
										width : 130,
										formatter : function(value, row, index) {
											if(value!=null){
											return '<font style="font-style: normal;font-weight: bolder;">'
													+ value + '</font>';
											}else{
											return "";
													}
										}
									},
									{
										field : 'warning',
										title : '是否预警',
										width : 130,
										formatter : function(value, row, index) {
											if(value==1){
												return "<font color='red'>是</font>";
											}else{
												return "<font color='green'>否</font>";
											}
										}
									},
									{
										field : 'valveCount',
										title : '条数触发的阀值 ',
										width : 130,
										formatter : function(value, row, index) {
											if(value!=null){
											return '<font style="font-style: normal;font-weight: bolder;">'
													+ value + '</font>';
											}else{
											return "";
													}
										}
									},
									{
										field : 'valvePercent',
										title : '百分比触发的阀值 ',
										width : 130,
										formatter : function(value, row, index) {
											if(value!=null){
											return '<font style="font-style: normal;font-weight: bolder;">'
													+ value + '</font>';
											}else{
											return "";
													}
										}
									},
								    {
										field : 'createTime',
										title : '创建时间',
										width : 150,
										formatter : function(value, row, index) {
										 	if(value!=null){
							                      var unixTimestamp = new Date(value);  
							                  	return '<font style="font-style: normal;font-weight: bolder;">'
												+ (unixTimestamp
														.toLocaleString())
												+ '</font>'; 
							                      } else{
							                    	  return "";
							                      }
										}

									},
									{
										field : 'lastUpdateTime',
										title : '修改时间',
										width : 150,
										formatter : function(value, row, index) {
										 	if(value!=null){
							                      var unixTimestamp = new Date(value);  
							                  	return '<font style="font-style: normal;font-weight: bolder;">'
												+ (unixTimestamp
														.toLocaleString())
												+ '</font>'; 
							                      } else{
							                    	  return "";
							                      }
										}

									},
									{
										field : 'action',
										title : '操作',
										width : 130,
										formatter : function(value, row, index) {
											var str = '&nbsp;&nbsp;';
											if ($.canEdit) {
												str += $
														.formatString(
																'<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>',
																row.id,
																'${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');
											}
											//只有预警词语才可以设置预警阀值
											if(row.warning == 1){
											str += '&nbsp;&nbsp;&nbsp;';
											str += $.formatString(
													'<img onclick="editSensitiveFun(\'{0}\');" src="{1}" title="预警阀值"/>',
													row.id,
													'${pageContext.request.contextPath}/style/images/extjs_icons/lock/lock_go.png');
											}
											
											str += '&nbsp;&nbsp;&nbsp;';
											if ($.canDelete) {
												str += $
														.formatString(
																'<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>',
																row.id,
																'${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
											}
											return str;
										}
									} ] ],
							toolbar : '#toolbar',
							onLoadSuccess : function() {
								$('#searchForm table').show();
								parent.$.messager.progress('close');

								$(this).datagrid('tooltip');
							},
							onRowContextMenu : function(e, rowIndex, rowData) {
								e.preventDefault();
								$(this).datagrid('unselectAll').datagrid(
										'uncheckAll');
								$(this).datagrid('selectRow', rowIndex);
								$('#menu').menu('show', {
									left : e.pageX,
									top : e.pageY
								});
							}
						});
	});

	function deleteFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager
				.confirm(
						'询问',
						'您是否要删除当前敏感词？',
						function(b) {
							if (b) {
								var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
								if (currentUserId != id) {
									parent.$.messager.progress({
										title : '提示',
										text : '数据处理中，请稍后....'
									});
									$
											.post(
													'${pageContext.request.contextPath}/sensitiveController/delete',
													{
														id : id
													},
													function(result) {
														if (result.success) {
															parent.$.messager
																	.show({
																		title : '提示',
																		msg : result.msg
																	});
															dataGrid
																	.datagrid('reload');
														}
														parent.$.messager
																.progress('close');
													}, 'JSON');
								} else {
									parent.$.messager.show({
										title : '提示',
										msg : '不可以删除自己！'
									});
								}
							}
						});
	}

   //编辑食堂
	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$
				.modalDialog({
					title : '编辑敏感词',
					width : 400,
					height : 200,
					href : '${pageContext.request.contextPath}/sensitiveController/editPage?id='
							+ id,
					buttons : [ {
						text : '编辑',
						handler : function() {
							parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
							var f = parent.$.modalDialog.handler.find('#form');
							f.submit();
						}
					} ]
				});
	}
   
   
	//给敏感词设置预警阀值
	function editSensitiveFun(id) {
		parent.$.modalDialog.openner_dataGrid = dataGrid;
		parent.$.modalDialog({
					title : '预警阀值',
					width : 630,
					height : 400,
					href : '${pageContext.request.contextPath}/sensitiveController/SensitiveVcEdit?id='+ id
				});
	}

	function addFun() {
		parent.$.modalDialog({
					title : '添加敏感词',
					width : 400,
					height : 200,
					href : '${pageContext.request.contextPath}/sensitiveController/addPage',
					buttons : [ {
						text : '添加',
						handler : function() {
							parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
							var f = parent.$.modalDialog.handler.find('#form');
							f.submit();
						}
					} ]
				});
	}

	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false"
			style="height:65px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed"
					style="display: none;">
					<tr>
						<th>敏感词名称</th>
						<td>
							<input name="sensitiveName" placeholder="输入敏感词 名称" class="span2" />
						</td>
					   <th>是否预警</th>
					   <td>
						   <select  name="warning" class="easyui-combobox" style="width:100px;" data-options="width:140,height:29,editable:false,panelHeight:72">
						      <option value=""></option>
						      <option value="1">是</option>
						      <option value="0">否</option>
						   </select>
					   </td>
					   <th>所属食堂</th>
					   <td>
						   <select id="cafetoriumId" name="cafetoriumId" class="easyui-combobox" data-options="width:130,height:29,editable:false,panelHeight:120"  >
						    		<option value=""></option>
						    <c:forEach items="${cafeList}" var="de">
									<option value="${de.id}" >${de.cafeName}</option>
								</c:forEach>
						   </select>
					   </td>
					</tr>


				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="sendataGrid" title="敏感词表单" data-options="collapsible:true"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
	
       <a onclick="addFun();" href="javascript:void(0);"
			class="easyui-linkbutton"
			data-options="plain:true,iconCls:'transmit'">添加</a>
		<a href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a><a
			href="javascript:void(0);" class="easyui-linkbutton"
			data-options="iconCls:'brick_delete',plain:true"
			onclick="cleanFun();">清空条件</a>
	</div>

</body>
</html>