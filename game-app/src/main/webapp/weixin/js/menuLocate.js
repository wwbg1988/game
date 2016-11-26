/**
 * 要先引入百度地图的js
 * http://api.map.baidu.com/api?v=1.5&ak=7d4f25ba814088e01c850f47f91c40e1
 */
$(window).load(function(){
	var myCity = new BMap.LocalCity();
	myCity.get(BaiduCallBackFunction);
});

//根据ip定位城市
function locateCity()
{
	var myCity = new BMap.LocalCity();
	myCity.get(BaiduCallBackFunction);
}


//百度地图的回调函数
function BaiduCallBackFunction(result){
	var cityName = result.name;
	showCafetoriums(cityName);
	//解析xml
	$.get("xml/cities.xml",function(xml){
		var city = $(xml).find("Cities").find("City");
		//alert($(country[0]).attr("id"));
		$(city).each(function(index, item){
			var name = $(item).attr('CityName');
			$("#consigneeCity").append("<option value ='"+name+"'>"+name+"</option>");
			
			if(name == cityName)
			{
				$("#consigneeCity").append("<option value ='"+name+"' selected>"+name+"</option>");
			}
		});
	});
}




function showCafetoriums(cityName)
{
	$.ajax({
		url:'/weixin/getCafetoriumListByCityName.do',
		type:'POST',
		dataType:'json',
		data:{
//			openId:getUrlParameter("openId"),
			cityName:cityName
		},
		success:function(data){
			if(200==data.status){
				//空
				if(!data.data){
					alert("数据为空");
				}
				
				var lis = "";
				$.each(data.data,function(index, item){
					//$("#cafetoriumList").
//					alert(JSON.stringify(item));
					
					if(item.id==getUrlParameter("cafetoriumId")){
						lis = lis + '<li style="display:none">'+
				  		'<div class="wrap clearfix">'+
				  		showDetailCafetoriumName(item.cafeName) + '<span class="default" id="localCityCafetorium'+ item.id +'" cafeCode='+item.cafeCode+ ' style="cursor:pointer;">关注</span>'+
				  		'</div>'+
				  		'</li>';
//						
//						//默认
						$('.js-currentCafetorium').text(showCafetoriumName(item.cafeName));
						$('.js-currentCafetorium-detail').text(showDetailCafetoriumName(item.cafeName));

					}
					else
					{
						lis = lis + '<li>'+
				  		'<div class="wrap clearfix">'+
				  		showDetailCafetoriumName(item.cafeName) + '<span class="default" id="localCityCafetorium'+ item.id +'" cafeCode='+item.cafeCode + ' style="cursor:pointer;">关注</span>'+
				  		'</div>'+
				  		'</li>';
					}
					
					
					
				});
				
				$("#localCityCafetoriumList").append(lis);
				
				
				
				//绑定事件到“设置默认”
				$("#localCityCafetoriumList").on("click","li div span", function(){
					//排除已经设为默认的情况
					if("none"==$(this).parent().parent().css("display")){
						return;
					}
					
//					$('#currentCafetorium').text($(this).parent().text().replace('设置默认',''));
					
					$("#localCityCafetoriumList li").css('display','');
					
					$(this).parent().parent().css('display','none');
					
					
					var newCafetoriumId = $(this).attr("id");
					if(!newCafetoriumId){
						alert("餐厅id为空");
						return;
					}
					//去掉餐厅id的前缀cafetorium
					newCafetoriumId = newCafetoriumId.replace('localCityCafetorium','');
					
					cafeCode = $(this).attr("cafeCode");
						
					//更新数据库
					$.ajax({
						url:'/weixin/followCafetorium1.do',
						type:'POST',
						dataType:'json',
						data:{
							openId:getUrlParameter("openId"),
//							cafetoriumId:newCafetoriumId,
							cafeCode:cafeCode
						},
						success:function(data2){
							if(200 == data2.status){
//								$('#currentCafetorium1').text(data.data.cafeName);
//								$('#currentCafetorium0').text(data.data.cafeName);
//								$('.js-currentCafetorium').text(data2.data.cafeName);
								
								window.location.replace(
									window.location.href.split("?")[0]+"?openId="+getUrlParameter("openId")+"&cafetoriumId="+data2.data.id
									);
//								alert($('#currentCafetorium0').text());
//								$('#currentCafetorium0').text(data.data.cafeName);
//								alert($('#currentCafetorium0').text());
//								$('#currentCafetorium1').text(data.data.cafeName);
							}
							else
							{
								$('.js-currentCafetorium').text(showCafetoriumName(data2.message));
								$('.js-currentCafetorium-detail').text(showDetailCafetoriumName(data2.message));
							}
						}
					});
				});
				
					
			}
			else{
				alert(data.message);
			}
			
		}
	});
}