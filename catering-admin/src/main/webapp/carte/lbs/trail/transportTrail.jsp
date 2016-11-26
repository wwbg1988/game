<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>轨迹管理</title>
<c:if test="${fn:contains(sessionInfo.resourceList, '/transportTrailController/manager')==false}">	
	<% 
		request.getRequestDispatcher("../../../error/403.jsp").forward(request, response);
	%>
</c:if>

<jsp:include page="../../../inc.jsp"></jsp:include>


<script type="text/javascript">

	var dataGrid;
	$(function() {
		parent.$.messager.progress('close');

		dataGrid = $('#dataGrid').datagrid({          
			url : '${pageContext.request.contextPath}/transportTrailController/dataGrid',
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
				field : 'driverName',
				title : '配送人',
				width : 100
			}, {
				field : 'taskName',
				title : '任务',
				width : 100
			}, {
				field : 'typeId',
				title : '轨迹类型',
				width : 100
			}, {
				field : 'coordinateAddress',
				title : '坐标名称',
				width : 100
			}, {
				field : 'coordinateType',
				title : '坐标类型',
				width : 100
			}, {
				field : 'longitude',
				title : '经度',
				width : 100
			}, {
				field : 'latitude',
				title : '纬度',
				width : 100
			},{
				field : 'imei',
				title : '设备编号',
				width : 100
			},			
			{
				field : 'createTime',
				title : '创建时间',				
				formatter:formatDate,
				width : 100
			}, {
				field : 'lastUpdateTime',
				title : '最后修改时间',
				formatter:formatDate,
				width : 100
			} ] ],
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
	        	 $('#task').combobox('clear');

	        	//供应商
	             $('#supplier').combobox({
	                 disabled: false,
	                 url: '${pageContext.request.contextPath}/supplierController/findSuppliersBy?projectId=' + record.value,
	                 valueField: 'id',
	                 textField: 'supplierName'
	             });
	        	
	           	 //任务
	             $('#task').combobox({
	                 disabled: false,
	                 url: '${pageContext.request.contextPath}/transportTaskController/findTransportTaskDtoBy?projectId=' + record.value,
	                 valueField: 'id',
	                 textField: 'taskName'
	             });

	         }
	     });
		
		$('#project').combobox('setValue',null);
		$('#supplier').combobox('setValue',null);
		$('#task').combobox('setValue',null);
		
	});
	
	
	//替换空
	function formatNull(value, row, index) {
		if (value == null) {
			return "空";
		}
		else{
			return value;
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

	//格式化时间
	function formatDate(value, row, index) {
		if (value != null) {
			var unixTimestamp = new Date(value);
			return unixTimestamp.toLocaleString();
		} else {
			return "";
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
						<th>任务</th>
						<td>
							<select id="task" name="transportTaskId"  class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:120">
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

