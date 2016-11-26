<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>会议室管理</title>
<jsp:include page="../../inc.jsp"></jsp:include>


<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/meetingAddressController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			rownumbers : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'name',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 244,
				checkbox : true,
				hidden : true
			} ] ],
			columns : [ [ {
				field : 'name',
				title : '会议室名称',
				width : 150
			},{
				field : 'largeAreaName',
				title : '大区名称',
				width : 150
			},{
				field : 'provinceName',
				title : '省份名称',
				width : 150
			},{
				field : 'cityName',
				title : '城市名称',
				width : 150
			},{
				field : 'createTime',
				title : '创建时间',
				width : 150,
				 formatter:function(value,row,index){  
                     var unixTimestamp = new Date(value);  
                     return unixTimestamp.toLocaleString();  
                     } 
			},{
				field : 'lastUpdateTime',
				title : '更新时间',
				width : 150,
			      formatter:function(value,row,index){ 
			    	  if(value==null){
			    		  return null;
			    	  }else{
			              var unixTimestamp = new Date(value);  
	                      return unixTimestamp.toLocaleString(); 
			    	  }
                    } 
			},{
				field : 'projectName',
				title : '项目',
				width : 70
			},{
				field : 'action',
				title : '操作',
				width : 150,
				formatter : function(value, row, index) {
					var str = '';
						str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/pencil.png');

					    str += '&nbsp;&nbsp;';
				
						str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/cancel.png');
					
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
				$(this).datagrid('unselectAll').datagrid('uncheckAll');
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
		parent.$.messager.confirm('询问', '您是否要删除当前会议室？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
				if (currentUserId != id) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/meetingAddressController/deleteMeetingA', {
						id : id
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							dataGrid.datagrid('reload');
						}else {
							parent.$.messager.alert('错误', result.msg, 'error');
						}
						parent.$.messager.progress('close');
					}, 'JSON');
				} 
			}
		});
	}

	
	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑会议室',
			width : 700,
			height : 300,
			href : '${pageContext.request.contextPath}/meetingAddressController/editMeetingAddress?id=' + id,
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


	function addFun() {
		parent.$.modalDialog({
			title : '添加会议室',
			width : 700,
			height : 500,
			href : '${pageContext.request.contextPath}/meetingAddressController/addMeetingAddress',
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
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
	

</script>
</head>
<body>
	<div  class="easyui-layout" data-options="fit : true,border : false">
		<div   data-options="region:'north',title:'查询条件',border:false" style="height: 120px; overflow: hidden;">
			<form id="searchForm">
			
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
					    <th>会议室名称</th>
					    <td>
					    <input name="name" placeholder="可以查询会议室名称" class="span2" style="width:279px;"/>
					    </td>
					    <th>所属大区</th>
			            <td>
			              <select id="largeArea" name="largeArea" class="easyui-combobox"  data-options="width:283,height:29,editable:false, onSelect:function(record){
			                  var url = '${pageContext.request.contextPath}/meetingAddressController/findChildId?id='+record.value;    
                              $('#province').combobox('reload', url); 
                              $('#province').combobox('setValue','请选择省份');
                              $('#city').combobox('setValue','请选择城市');
			              }">
			                <option value="">请选择大区</option>
			                   <c:forEach items="${listAddDto}" var="de">
						        <option value="${de.id}"  >${de.addressName}</option>
					           </c:forEach>
					           
			              </select>
			             </td> 
					</tr>
				    <tr>
				        <th>所属省份</th>
					    <td>
					      <select id="province" name="province" class="easyui-combobox"  data-options="width:283,height:29,editable:false,panelHeight:'auto',valueField:'id',textField:'addressName', onSelect:function(record){    
                              var url = '${pageContext.request.contextPath}/meetingAddressController/findChildId?id='+record.id;    
                              $('#city').combobox('reload', url); 
                              $('#city').combobox('setValue','请选择城市');
                         } ">
					        <option value="">请选择省份</option>
					      </select>
					    </td>
					    <th>所属城市</th>
			            <td>
			               <select id="city" name="city" class="easyui-combobox"  data-options="width:283,height:29,editable:false,panelHeight:'auto',valueField:'id',textField:'addressName'">
			               <option value="">请选择城市</option>
			              </select>
			            </td>
				    </tr>
				</table>
			</form>
		</div>
		<div   data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		
		 <!-- 	<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>     -->
		
		<!--<c:if test="${fn:contains(sessionInfo.resourceList, '/deptLevelController/grantPage')}">
			<a onclick="batchGrantFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'tux'">批量授权</a>
		</c:if>-->
	
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	</div>

	
</body>
</html>