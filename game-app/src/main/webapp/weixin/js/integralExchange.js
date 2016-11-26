$(document).ready(
	function(){
		jQuery.ajax({
			url:'/http/api/carte/shopUser/getCarteUserByWxUniqueIdAndCafetoriumId.do',
			type:'POST',
			dataType:'json',
			data:{
				'openId':getUrlParameter("openId"),
				'cafetoriumId':getUrlParameter("cafetoriumId")
			},
			success:function(data)
			{
				if(200==data.status)
				{
					//alert(JSON.stringify(data.data));
					$("#integral").html(data.data.integral);
					$("#carteUserId").val(data.data.id);
					
					/**
					 * 获取uuid
					 */
					jQuery.ajax({
						url:'/http/api/carte/getUUID.do',
						type:'POST',
						dataType:'json',
						data:{
							'openId':getUrlParameter("openId")
						},
						success:function(data2)
						{
							if(200 == data2.status)
							{
								$("#userIntegralExchangeId").val(data2.data);
							}
							else
							{
								alert(data2.message);
							}
						}
					});
					
					//第二次ajax请求
					jQuery.ajax({
						url:'/http/api/carte/integral/getExchangesByCafetoriumId.do',
						type:'POST',
						dataType:'json',
						data:{'cafetoriumId':getUrlParameter("cafetoriumId")},
						success:function(data3)
						{
							if(200==data3.status)
							{
								jQuery.each(data3.data,function(index,item){
									
									var action;
									if($("#integral").html() >= item.exchangeIntegral)
									{
										action = "<div class='bt_wrap active' style='cursor:pointer;' onclick='saveUserExchange("+JSON.stringify(item)+")'"+
								 		"<span class='score'>"+item.exchangeIntegral+"分</span>" +
									 	"</div>";
									}
									else
									{
										action = "<div class='bt_wrap'>" +
								 		"<span class='score'>"+item.exchangeIntegral+"分</span>" +
									 	"</div>";
									}
									
									$("#allExchanges").html(	
											$("#allExchanges").html()+						
											 "<div class='row_wrap clearfix'>" +
											 	"<img src='"+urlPre+item.exchangeIcon+"'/>" +
											 	"<div class='row_info'>" +
											 	"<span>"+item.exchangeName+"</span>" +
											 	"<p>"+item.exchangeDescribe+"</p>" +
											 "</div>"+
											 	action+
											 "</div>"					
									);
								});
							}
							else
							{
								alert(data3.message);
							}
						}
					});
				}
				else
				{
					alert("您未进行评论");
					window.location.href="comment.html?cafetoriumId="+getUrlParameter("cafetoriumId")+"&openId="+getUrlParameter("openId");
				}
			}
		});
		
		
});


function saveUserExchange(item)
{
	//微信id
	var openId = getUrlParameter("openId");
	if(isEmpty(openId))
	{
		alert("请使用微信登录");
		return;
	}
	
	//积分兑换的物品id
	var integralExchangeId = item.id;
	if(isEmpty(integralExchangeId))
	{
		alert("请选中要兑换的物品");
		return;
	}
	
	//要兑换的物品名字
	var exchangeName = encodeURIComponent(item.exchangeName);
	if(isEmpty(exchangeName))
	{
		alert("获取兑换物品名字出错，请刷新");
		return;
	}
	
	//要兑换的物品图片
	var exchangeIcon = item.exchangeIcon;
	if(isEmpty(exchangeIcon))
	{
		alert("获取兑换物品图片出错，请刷新");
		return;
	}
	
	//要兑换的物品积分
	var exchangeIntegral = item.exchangeIntegral;
	if(isEmpty(exchangeIntegral))
	{
		alert("获取兑换物品所需的积分出错，请刷新");
		return;
	}
	
	//用户出错
	var carteUserId = $("#carteUserId").val();
	if(isEmpty(exchangeIntegral))
	{
		alert("获取用户信息出错，请刷新");
		return;
	}
	
	
	//生成uuid
	var userIntegralExchangeId = $("#userIntegralExchangeId").val();
	if(isEmpty(userIntegralExchangeId))
	{
		alert("生成UUID出错，请刷新");
		return;
	}
	
	jQuery.ajax({
		url:'/http/api/carte/integral/findExchangeTypeById.do',
		type:'POST',
		dataType:'json',
		data:{
			'id':item.exchangeTypeId
		},
		success:function(data)
		{
			if(200 == data.status)
			{
				window.location.href = data.data.target+
						"?openId="+openId+
						"&cafetoriumId="+getUrlParameter("cafetoriumId")+
						"&integralExchangeId="+integralExchangeId+
						"&exchangeName="+exchangeName+
						"&exchangeIcon="+exchangeIcon+
						"&exchangeIntegral="+exchangeIntegral+
						"&carteUserId="+carteUserId+
						"&userIntegralExchangeId="+userIntegralExchangeId;
			}
			else
			{
				alert("物品兑换");
			}
		}
	});
		
}