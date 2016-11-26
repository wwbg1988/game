$(document).ready(function(){
	//微信
	var openId = getUrlParameter("openId");
	if(isEmpty(openId))
	{
		alert("请使用微信登录");
		return;
	}
	
	//积分兑换物品id
	var integralExchangeId = getUrlParameter("integralExchangeId");
	if(isEmpty(integralExchangeId))
	{
		alert("获取积分商品id信息出错，请重试");
		return;
	}
	
	//积分兑换物品名称
	var exchangeName = getUrlParameter("exchangeName");
	if(isEmpty(exchangeName))
	{
		alert("获取积分商品id信息出错，请重试");
		return;
	}
	
	//积分兑换物品图片
	var exchangeIcon = getUrlParameter("exchangeIcon");
	if(isEmpty(exchangeIcon))
	{
		alert("获取积分商品图片信息出错，请重试");
		return;
	}
	
	//物品积分
	var exchangeIntegral = getUrlParameter("exchangeIntegral");
	if(isEmpty(exchangeIntegral))
	{
		alert("获取积分商品积分信息出错，请重试");
		return;
	}
	
	//用户id
    var carteUserId = getUrlParameter("carteUserId");
    if(isEmpty(carteUserId))
	{
		alert("获取积分用户信息出错，请重试");
		return;
	}
    
    //uuid
    var userIntegralExchangeId = getUrlParameter("userIntegralExchangeId");
    if(isEmpty(userIntegralExchangeId))
	{
		alert("获取UUID出错，请重试");
		return;
	}

	$("#exchangeIcon").attr("src",urlPre+exchangeIcon);
	$("#exchangeName").html(exchangeName);
	$("#exchangeIntegral").html(exchangeIntegral);
	
	$("#openId").val(openId);
	$("#integralExchangeId").val(integralExchangeId);
	$("#carteUserId").val(carteUserId);
	$("#userIntegralExchangeId").val(userIntegralExchangeId);
	
	setSelect();
});

function save()
{
	//防止重复提交
	var submitFlag = $("#submitFlag").val();
	if("true" == submitFlag)
	{
		alert("兑换已完成，请不要重复提交");
		return;
	}
	
	//微信标识
	var openId = $("#openId").val();
	if(openId.length <= 0)
	{
		alert("请使用微信登录");
		return;
	}
	
	//要兑换的物品的id
	var integralExchangeId = $("#integralExchangeId").val();
	if(integralExchangeId.length <= 0)
	{
		alert("获取要兑换的物品的id出错，请重试");
		return;
	}
	
	//获取用户信息
	var carteUserId =  $("#carteUserId").val();
	if(carteUserId.length <= 0)
	{
		alert("获取用户信息出错，请重试");
		return;
	}
	
	//生成的id
	var userIntegralExchangeId = $("#userIntegralExchangeId").val();
	if(userIntegralExchangeId.length <= 0)
	{
		alert("获取UUID出错，请重试");
		return;
	}
	
	//收货人姓名
	var consigneeName = $("#name").val();
	if(consigneeName.length <= 0)
	{
		alert("收货人姓名不能为空");
		return;
	}
	else if(!isLengthBetweenAndEqual(consigneeName, 1, 16))
	{
		alert("收货人姓名的长度必须在1~16之间");
		return;
	}
	
	//手机号码
	var phoneNumber = $("#phone").val();
	if(phoneNumber.length <= 0)
	{
		alert("手机号码不能为空");
		return;
	}
	else if(!isPhoneValid(phoneNumber))
	{
		alert("手机号码不合法");
		return;
	}
	
	//收货人地址
	var address = $("#addr").val();
	if(address.length <= 0)
	{
		alert("地址不能为空");
		return;
	}
	else if(!isLengthBetweenAndEqual(address, 1, 20))
	{
		alert("收货人地址的长度必须在1~20之间");
		return;
	}
	
	//收货人城市
	var consigneeCity = $("#consigneeCity").val();
	if(consigneeCity.length <= 0)
	{
		alert("收货人城市不能为空");
		return;
	}
	
	jQuery.ajax({
		url:'/http/api/carte/integral/entityExchange.do',
		type:'POST',
		dataType:'json',
		data:{
			'openId':openId,
			'integralExchangeId':integralExchangeId,
			'carteUserId':carteUserId,
			'id':userIntegralExchangeId,
			'consigneeName':consigneeName,
			'phoneNumber':phoneNumber,
			'address':address,
			'consigneeCity':consigneeCity
		},
		success:function(data)
		{
			if(200 == data.status)
			{
				$("#submitFlag").val("true");
				showDialog();
			}
			else
			{
				alert(data.message);
			}
		}
//		,
//		error:function(jqXHR, textStatus, errorThrown)
//		{
//			alert("网络出错");
//		}
	});
}

function setSelect()
{
	//百度地图的回调函数
	function myFun(result){
		var cityName = result.name;
		
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
	var myCity = new BMap.LocalCity();
	myCity.get(myFun);
}


function closeAndShowScore()
{
	//微信标识
	var openId = $("#openId").val();
	if(isEmpty(openId))
	{
		alert("请使用微信登录");
		return;
	}
	
	closeDialog();
	
	window.location.href = "score_exchange.html?openId="+openId+"&cafetoriumId="+getUrlParameter("cafetoriumId");
}