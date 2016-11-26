$(document).ready(function(){
	$.ajax({
		url:'/weixin/getfollowCafetoriumList.do',
		type:'POST',
		dataType:'json',
		data:{
			openId:getUrlParameter("openId")
		},
		success:function(data){
			if(200==data.status){
				//空
				if(!data.data){
					alert("数据为空");
				}
				
				//绑定事件到“设置默认”
				$("#cafetoriumList").on("click","li div span", function(){
					//排除已经设为默认的情况
					if("none"==$(this).parent().parent().css("display")){
						return;
					}
					
//					$('#currentCafetorium').text($(this).parent().text().replace('设置默认',''));
					
					$("#cafetoriumList li").css('display','');
					
					$(this).parent().parent().css('display','none');
					
					
					var newCafetoriumId = $(this).attr("id");
					if(!newCafetoriumId){
						alert("餐厅id为空");
						return;
					}
					//去掉餐厅id的前缀cafetorium
					newCafetoriumId = newCafetoriumId.replace('cafetorium','');
						
					//更新数据库
					$.ajax({
						url:'/weixin/setDefaultCafetorium.do',
						type:'POST',
						dataType:'json',
						data:{
							openId:getUrlParameter("openId"),
							cafetoriumId:newCafetoriumId
						},
						success:function(data){
							if(200 == data.status){
//								$('#currentCafetorium1').text(data.data.cafeName);
//								$('#currentCafetorium0').text(data.data.cafeName);
								$('.js-currentCafetorium').text(showCafetoriumName(data.data.cafeName));
								$('.js-currentCafetorium-detail').text(showDetailCafetoriumName(data.data.cafeName));
								window.location.replace(
									window.location.href.split("?")[0]+"?openId="+getUrlParameter("openId")+"&cafetoriumId="+data.data.id
									);
//								alert($('#currentCafetorium0').text());
//								$('#currentCafetorium0').text(data.data.cafeName);
//								alert($('#currentCafetorium0').text());
//								$('#currentCafetorium1').text(data.data.cafeName);
							}
							else
							{
								$('.js-currentCafetorium').text(showCafetoriumName(data.message));
								$('.js-currentCafetorium-detail').text(showDetailCafetoriumName(data.message));
							}
						}
					});
				});
				
				
				
				var lis = "";
				$.each(data.data,function(index, item){
					//$("#cafetoriumList").
//					alert(JSON.stringify(item));
					
					if(1==item.isDefault){
						lis = lis + '<li style="display:none">'+
				  		'<div class="wrap clearfix">'+
				  		showDetailCafetoriumName(item.cafeName) + '<span class="default" id="cafetorium'+ item.id + '" style="cursor:pointer;">设置默认</span>'+
				  		'</div>'+
				  		'</li>';
//						
						$('.js-currentCafetorium').text(showCafetoriumName(item.cafeName));
						$('.js-currentCafetorium-detail').text(showDetailCafetoriumName(item.cafeName));
					}
					else
					{
						lis = lis + '<li>'+
				  		'<div class="wrap clearfix">'+
				  		showDetailCafetoriumName(item.cafeName) + '<span class="default" id="cafetorium'+ item.id + '" style="cursor:pointer;">设置默认</span>'+
				  		'</div>'+
				  		'</li>';
					}
					
					
					
				});
				
//				alert(lis);
				
				$("#cafetoriumList").append(lis);
				
//				alert($("#cafetorium"+getUrlParameter("cafetoriumId")).text());
				
//				$("#cafetorium"+getUrlParameter("cafetoriumId")).trigger("click");
				
					
			}
			else{
				alert(data.message);
			}
			
		}
	});
});