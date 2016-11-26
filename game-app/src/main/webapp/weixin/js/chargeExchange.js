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
//		
//		//解码
//		exchangeName = decodeURIComponent(exchangeName);

		
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
		
	
});

/**
 * commit
 */
function save()
{
	var submitFlag = $("#submitFlag").val();
	if("true" == submitFlag)
	{
		alert("充值已完成，请不要重复提交");
		return;
	}
	
	//微信标识
	var openId = $("#openId").val();
	if(isEmpty(openId))
	{
		alert("请使用微信登录");
		return;
	}
	
	//要兑换的物品的id
	var integralExchangeId = $("#integralExchangeId").val();
	if(isEmpty(integralExchangeId))
	{
		alert("获取要兑换的物品的id出错，请重试");
		return;
	}
	
	//获取用户信息
	var carteUserId =  $("#carteUserId").val();
	if(isEmpty(carteUserId))
	{
		alert("获取用户信息出错，请重试");
		return;
	}
	
	//生成的id
	var userIntegralExchangeId = $("#userIntegralExchangeId").val();
	if(isEmpty(userIntegralExchangeId))
	{
		alert("获取UUID出错，请重试");
		return;
	}
	
	//充值的手机号码
	var phone = $("#phone").val();
	if(isEmpty(phone))
	{
		alert("请输入手机号码");
		return;
	}
	else if(!isPhoneValid(phone))
	{
		alert("手机号码不合法");
		return;
	}
	
	
	jQuery.ajax({
		url:'/http/api/carte/integral/chargeExchange.do',
		type:'POST',
		dataType:'json',
		data:{
			'openId':openId,
			'integralExchangeId':integralExchangeId,
			'carteUserId':carteUserId,
			'id':userIntegralExchangeId,
			'phoneNumber':phone
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
				//充值未实现
				alert(data.message);
			}
		}
	});
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