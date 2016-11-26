<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form')
				.form(
						{
							url : '${pageContext.request.contextPath}/goodsController/add',
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
									var urls='/goodsController/manager';	
									var iframe = '<iframe src="'+ urls + '" frameborder="0" style="border:0;width:100%;height:98%;"></iframe>';
									var t = $('#index_tabs');
										var opts = {
												title : "商品管理",
												closable : true,
												iconCls :'',
												content : iframe,
												border : false,
												fit : true
											};
											if (t.tabs('exists',"商品管理")) {
												
												t.tabs('select', "商品管理");
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
									parent.$.messager.alert('错误', result.msg,
											'error');
								}
							}
						});
	});
	
	
	  //食堂下拉框查询
	jQuery.ajax({
		url:'${pageContext.request.contextPath}/cafrtoriumController/findAll',
		type:'POST',
		dataType:'json',
		async:false,
		success:function(data){
			var myObject =data;
			var a= "";
		
            for (var i = 0; i < myObject.length; i++) {
				 a+="<option value=\'"+myObject[i].id+"\'>"+myObject[i].cafeName+"</option>";
				 console.log(a);
            }
            $("#cafetoriumId").html(a);
		}
	});
	  
	  //商品类型
	jQuery.ajax({
		url:'${pageContext.request.contextPath}/goodsTypeController/findAll',
		type:'POST',
		dataType:'json',
		async:false,
		success:function(data){
			var myObject =data;
			var a= "";
		
            for (var i = 0; i < myObject.length; i++) {
				 a+="<option value=\'"+myObject[i].id+"\'>"+myObject[i].typeName+"</option>";
				 console.log(a);
            }
            $("#goodsTypeId").html(a);
		}
	});
	  //商品类型选择下拉框级联判断：如果是限时特购，则需要填写最大购买量;
	$("#goodsTypeId").combobox({
        onSelect:function(n,o){
        	if(n.value==1 ){
			    $('#countLimitTr').removeAttr("hidden");
			   
			}else if (n.value==2){	
			    $('#countLimitTr').attr('hidden','true'); 
			}
        }
    });
	  
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				<tr style="display:none">
					<th>编号</th>
					<td><input name="id" type="text" class="span2"
						value="${goodsDto.id}" readonly="readonly" style="width: 160px;"></td>
				</tr>
				<tr><th style="width: 180px;">商品名称</th>
					<td ><input name="goodsName" type="text" style="width: 170px;"
						class="easyui-validatebox span2" data-options="required:true"></td>
						    <th style="">库存量</th>
					<td style="height:170;"><input name="goodsStocks" value="1" class="easyui-numberspinner" style="width: 220px; height: 29px;"
					 required="required" data-options="editable:true,min:1"></td>
					
				</tr>
				<tr>
					<th>是否轮转</th>
					<td>
					<select name="isTurn" class="easyui-combobox" data-options="width:180,height:29,editable:false,panelHeight:60">
					      <option value="1" >是</option>
						  <option value="0" >否</option>
				    </select>
					</td>
					 <th>所属食堂</th>
					<td>
						<select id="cafetoriumId" name="cafetoriumId" class="easyui-combobox span2"  style="width: 220px;"  data-options="height:29,editable:false,panelHeight:120,required:true"></select>
				    </td>
				</tr>
				<tr>	
				<th style="width: 150px;">商品价格</th>
					<td><input name="price" type="text" style="width: 167px;" class="easyui-numberbox span2"  precision="2" max="99999.99" 
						data-options="required:true"></td>	
					<th style="width: 150px;">促销价格</th>
					<td><input name="salesPrice" type="text" style="width: 208px;" class="easyui-numberbox span2"   precision="2" max="99999.99" 
						data-options="required:true"></td>	
				
				<!-- 	 <th style="padding-top: 15px;">商品折扣(百分比)</th>
					<td style="height:170;padding-top: 15px;"><input class="easyui-slider" name="percent"  style="width:220px;"  
                     data-options="showTip:true,rule:[0,'|',25,'|',50,'|',75,'|',100]" /></td> -->
				  
				</tr>
				<tr>
				    <th>商品类型</th>
					<td><select id="goodsTypeId" name="goodsTypeId" class="easyui-combobox span2"  style="width: 180px;" data-options="height:29,editable:false,panelHeight:58,required:true"></select></td>
				    <th>商品介绍</th>
					<td ><textarea name="introduce"  rows="6" cols="18"  style="width: 275px;" class="easyui-validatebox" ></textarea></td>
				</tr>
				<tr  hidden="true" id="countLimitTr">
					
				    <th>最大购买量</th>
					<td><input  name="countLimit" value="1" class="easyui-numberspinner" style="width: 180px; height: 29px;"
					 required="required" data-options="editable:true,min:1" ></td>
					 <th></th>
					 <td></td>
				
				</tr>
			</table>
		</form>
	</div>
</div>