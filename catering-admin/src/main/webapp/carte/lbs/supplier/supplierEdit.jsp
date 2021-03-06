<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	var map = new BMap.Map("container");
	map.centerAndZoom("宁波", 12);
	map.enableScrollWheelZoom(); //启用滚轮放大缩小，默认禁用
	map.enableContinuousZoom(); //启用地图惯性拖拽，默认禁用

	map.addControl(new BMap.NavigationControl()); //添加默认缩放平移控件
	map.addControl(new BMap.OverviewMapControl()); //添加默认缩略地图控件
	map.addControl(new BMap.OverviewMapControl({
		isOpen : true,
		anchor : BMAP_ANCHOR_BOTTOM_RIGHT
	})); //右下角，打开

	var localSearch = new BMap.LocalSearch(map);
	localSearch.enableAutoViewport(); //允许自动调节窗体大小
	$(function() {
		parent.$.messager.progress('close');
		$('#supplierForm').form({
			url : '${pageContext.request.contextPath}/supplierController/edit',
			onSubmit : function() {

				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid =$('#supplierForm').form('validate');
			    if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					$('#supplierMap').dialog('close');
					parent.$.messager.show({
						title : '提示',
						msg : result.msg
					});
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
		var address = "${supplierDto.address}";
		 var longitude="${supplierDto.longitude}"; 
		var latitude="${supplierDto.latitude}"; 
		$("#address").val(address);
		$("#resultEdit_").val(longitude+","+latitude);
	});

	function searchByStationName2() {
		map.clearOverlays();//清空原来的标注

		var keyword = document.getElementById("address").value;
		localSearch.setSearchCompleteCallback(function(searchResult) {
			var poi = searchResult.getPoi(0);
			if(poi==undefined){
				parent.$.messager.alert('错误', '您输入的地址不存在', 'error');
				$("#address").val('');
				$("#resultEdit_").val('');
			}
			$("#address").val(poi.title);
			document.getElementById("resultEdit_").value = poi.point.lng + ","
					+ poi.point.lat;
			map.centerAndZoom(poi.point, 13);
			var marker = new BMap.Marker(new BMap.Point(poi.point.lng,
					poi.point.lat)); // 创建标注，为要查询的地方对应的经纬度
			map.addOverlay(marker);
			var content = document.getElementById("address").value
					+ "<br/><br/>经度：" + poi.point.lng + "<br/>纬度："
					+ poi.point.lat;
			var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>"
					+ content + "</p>");
			document.getElementById("longitudeEdit").value = poi.point.lng;
			document.getElementById("latitudeEdit").value = poi.point.lat;
			var address_text = $("#address").val();
			$("#address").val(address_text);
			marker.addEventListener("click", function() {
				this.openInfoWindow(infoWindow);
			});
			// marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画

		});
		localSearch.search(keyword);
	}
</script>



<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden; width: 600px">

		<form id="supplierForm" method="post">
			<div style="">
				<div style="width: 578px; margin: auto;">
				
					<div id="container"
						style="dposition: absolute; margin-top: 14px; width: 750px; height: 510px; top: 5; border: 1px solid gray; overflow: hidden; display: none;">
					</div>
				</div>
			</div>
			<table class="table table-hover table-condensed">
				<tr>
					<td><input name="id" type="hidden" value="${supplierDto.id}" />
						<input id="longitudeEdit" name="longitude" type="hidden" /> <input
						id="latitudeEdit" name="latitude" type="hidden" />
				</tr>
				<tr>
					<th>供应商名称</th>
					<td><input id="supplierName" type="text" name="supplierName"
						value="${supplierDto.supplierName}" style="margin-right: 20px;" class="easyui-validatebox span2" />
				</tr>
				<tr>
					<th>供应商地址</th>
					<td><input id="address" type="text" name="address"
						style="margin-right: 2px;" class="easyui-validatebox span2" />	<input type="button" value="查询经纬度" onclick="searchByStationName2();"  class="easyui-linkbutton"/></td>
				
				</tr>
				<tr>
					<th>查询结果(经纬度)</th>
					<td><input id="resultEdit_" type="text" disabled="disabled" class="easyui-validatebox" />
				</td>
				</tr>
			
			</table>
		</form>
	</div>
</div>
