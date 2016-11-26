<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>配送管理</title>

<c:if test="${fn:contains(sessionInfo.resourceList, '/transportTaskController/dispatching/manager')==false}">	
	<% 
		request.getRequestDispatcher("../../../error/403.jsp").forward(request, response);
	%>
</c:if>

<jsp:include page="../../../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/transportTaskController/dispatching/addPage')}">
	<script type="text/javascript">
		$.canDispatch = true;
	</script>
</c:if>	


<script type="text/javascript">


	var dataGrid;
	$(function() {
		parent.$.messager.progress('close');

		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/transportTaskController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			rownumbers : true,
			model : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			//sortName : 'deptName',
			//sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect : true,
			nowrap : false,
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 80,
				hidden : true
			} ] ],
			columns : [ [ {
				field : 'projectName',
				title : '项目',
				width : 100
			}, {
				field : 'adminUserName',
				title : '发布人',
				width : 100
			}, {
				field : 'adminSupplierUserName',
				title : '供应商',
				width : 100
			},{
				field : 'taskName',
				title : '任务名称',
				width : 100
			}, {
				field : 'state',
				title : '状态',
				width : 100,
				formatter:formateTaskState
			}, {
				field : 'transportDriverName',
				title : '配送人',
				width : 100
			}, {
				field : 'departPlace',
				title : '出发地',
				width : 100
			}, {
				field : 'destPlace',
				title : '目的地',
				formatter:formatNull,
				width : 100
			}, {
				field : 'merchandise',
				title : '货物',
				formatter:formatNull,
				width : 100
			}, {
				field : 'createTime',
				title : '创建时间',				
				formatter:formatDate,
				width : 100
			}, {
				field : 'lastUpdateTime',
				title : '最后修改时间',
				formatter:formatDate,
				width : 100
			} , {
				field : 'flag',
				title : '动作',
				width : 100,
				formatter : function(value, row, index) {
					var str = '&nbsp;&nbsp;';
					if ($.canDispatch) {
						if(1==row.state){
							str += $.formatString(
									'<img onclick="editFun(\'{0}\');" src="{1}" title="配送"/>',
									row.id,
									'${pageContext.request.contextPath}/style/images/extjs_icons/computer_edit.png');
						}
						else if(2==row.state){
							str += "正在运送中";
						}
						else if(3==row.state){
							str += "已完成";
						}
						else
						{
							str += "未知状态";
						}
					}
					
					return str;
				}
				
			}] ],
			toolbar : [ {
				iconCls : 'brick_add',
				text : '过滤查询',
				handler : function() {
					searchFun();
				}
			}, '-', {
				iconCls : 'brick_delete',
				text : '清除过滤',
				handler : function() {
					clearFun();
				}
			} ]
		});	
		
		$('#project').combobox({
	         onSelect: function (record) {
	        	 $('#supplier').combobox('clear');

	        	//供应商
	             $('#supplier').combobox({
	                 disabled: false,
	                 url: '${pageContext.request.contextPath}/supplierController/findSuppliersBy?projectId=' + record.value,
	                 valueField: 'id',
	                 textField: 'supplierName'
	             });

	         }
	     });
		
		$('#project').combobox('setValue',null);
		$('#supplier').combobox('setValue',null);
		$('#state').combobox('setValue',null);
		
	});
	
	//配送
	function editFun(id){
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		
		parent.$.modalDialog({
			title : '配送任务',
			width : 800,
			height : 230,
			href : '${pageContext.request.contextPath}/transportTaskController/dispatching/addPage?id='+id,
			buttons : [ {
				text : '配送',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}
	
	//替换空
	function formatNull(value, row, index) {
		if (value == null) {
			return "空";
		}
		else{
			return value;
		}
		
	}

	//格式化时间
	function formatDate(value, row, index) {
		if (value != null) {
			var unixTimestamp = new Date(value);
			return unixTimestamp.toLocaleString();
		} else {
			return "";
		}
	}
	
	function formateTaskState(value, row, index){
		switch(value){
			case 1:
				return "未开始";
			case 2:
				return "进行中";
			case 3:
				return "已结束";
			default:
				return "未开始";
		}
	}	
	
	//查询
	function searchFun(){
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	
	//清空条件
	function clearFun(){
		$('#searchForm input').val('');
		$('#searchForm select').val('');
	}
	
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false"
			style="height: 120px">
			<form id="searchForm">
				<table class="table table-hover table-condensed">
					<tr>
						<th>项目名称</th>
						<td>
							<select id="project" name="projectId"  class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120">
								<c:forEach items="${projects}" var="de">
									<option value="${de.id}">${de.projName}</option>
								</c:forEach>
						   	 </select>
						</td>
						<th>供应商</th>
						<td>
							<select id="supplier" name="adminSupplierUserId" class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120">
					   		</select>
						</td>
					</tr>
					<tr>
						<th>状态</th>
						<td>
							<select id="state" name="state" class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120">
								<option value="1">未开始</option>
								<option value="2">进行中</option>
								<option value="3">已结束</option>
					   		</select>
						</td>
						<th></th>
						<td>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',title:''">
			<table id="dataGrid"></table>
		</div>
	</div>
</body>
</html>

