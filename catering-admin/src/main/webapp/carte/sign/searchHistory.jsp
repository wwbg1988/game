<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>签到管理</title>
<jsp:include page="../../inc.jsp"></jsp:include>


<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/caterSignController/searchQiandao',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			rownumbers : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'createTime',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 100,
				hidden : true
			} ] ],
			columns : [ [ {
				field : 'userName',
				title : '用户名称',
				width : 70
			},{
				field : 'deptName',
				title : '部门名称',
				width : 70
			},{
				field : 'userAccount',
				title : '登陆账号',
				width : 70
			},{
				field : 'address',
				title : '签到地址',
				width : 160
			},{
				field : 'createTime',
				title : '签到时间',
				width : 80,
			      formatter:function(value,row,index){  
			    	  if(value!=null){
			    		  var unixTimestamp = new Date(value); 
			    		  return unixTimestamp.toLocaleString();  
			    	     }else{
			    		  return null;
			    	     }
                    } 
			},
			{
				field : 'projectName',
				title : '项目',
				width : 80
			}
			] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');
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
		
		
	    $("#projectid").combobox({
	    	onSelect :function(row){
	    	 var id = row.value;
	    	 $("#deptid").combobox({
	    		  url: '${pageContext.request.contextPath}/deptController/combobox?projId='+id,
              	  valueField : 'id',
              	  textField : 'deptName'
                });
	    	}
	    });
	});

	
	
	function addAddress(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		window.location.href=window.location.href;
		window.open('/caterSignController/addAddress?id='+id,'newWin','modal=yes,width=960,height=720,resizable=no,scrollbars=no'); 
	}
	
	function addMap(id) {
		$('#kk').dialog({    
		    title: 'My Dialog',    
		    width: 400,    
		    height: 200,    
		    closed: false,    
		    cache: false,    
		    href: '/caterSignController/addAddress',    
		    modal: true   
		});  
	}
	
	function deleteFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前公告？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
				if (currentUserId != id) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/notifyController/deleteNotify', {
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
	
	function addFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 810,
			height : 500,
			href : '${pageContext.request.contextPath}/notifyController/addnotify',
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
	     $('#gender').val('');
		dataGrid.datagrid('load', {});
	}

   //导出excel
   function exportExcel(){	  
	   var userName = $("#userName").val();
       var userAccount = $("#userAccount").val();
       var projectid =  $("input[name='projectid']").val() ; 
       var deptid = $("input[name='deptid']").val() ; 
      window.open('${pageContext.request.contextPath}/caterSignController/exportExcel?userName='+userName
				+'&userAccount='+userAccount+'&projectid='+projectid+'&deptid='+deptid);
   }
   
	
</script>
</head>
<body >
	<div  class="easyui-layout" data-options="fit : true,border : false">
		<div   data-options="region:'north',title:'查询条件',border:false" style="height: 140px; overflow: hidden;">
			<form id="searchForm">
			
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
					    <th>名称</th>
					    <td><input  name="userName" id="userName" placeholder="可以查询姓名" class="easyui-validatebox" style="width:279px;"/></td>
					    <th>账号</th>
					    <td>
					       <input id="userAccount" name="userAccount" placeholder="可以查询账号" class="easyui-validatebox" style="width:279px;"/>
					    </td>
					</tr>
					 <tr>
					   <th>项目</th>
					   <td>
					     <select id="projectid" name="projectid" class="easyui-combobox" style="width:282px;height: 26px;">
					      <option value="">请选择项目</option>
					      <c:choose>
					      <c:when test="${not empty pjdos}">
					       <c:forEach var="projectdto" items="${pjdos}" varStatus="vs">
					           <option value="${projectdto.id}">${projectdto.projName}</option>
					       </c:forEach>
					      </c:when>
					      </c:choose>
					   </select>
					   </td>
					   <th>部门</th>
					   <td>
					   <select id="deptid" name="deptid" class="easyui-combobox" style="width:282px;height: 26px;">
					      <option value="">请选择部门</option>
					   </select>
					   </td>
				    </tr>
				      <!-- <h5>excel导出地址：E:/download/Sign_info.xls</h5> -->
				</table>
			</form>
		</div>
		<div   data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<!--
		 	<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>  
		
		<c:if test="${fn:contains(sessionInfo.resourceList, '/deptLevelController/grantPage')}">
			<a onclick="batchGrantFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'tux'">批量授权</a>
		</c:if>-->
	
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	    <a onclick="exportExcel();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'brick_go'">导出excel</a>  
	</div>

	<div id="ww"></div> 
	<div id="kk"></div>
</body>
</html>