$(document).ready(function(){
	//绑定“更改”的click事件
	$(document).on("click","#changeCafetorium",function(){
		window.location.href="menu_setup.html?cafetoriumId="+getUrlParameter("cafetoriumId")+"&openId="+getUrlParameter("openId");
	})

	//绑定菜单的事件
	$("#menu_comment").parent().click(function(){
		toComment();
	});
	$("#menu_menu").parent().click(function(){
		toNextWeekMenu();
	});
	$("#menu_transfer").parent().click(function(){
		toSendByTianFang()
	});
	$("#menu_call").parent().click(function(){
		toVisitService();
	});
	$("#menu_birthday").parent().click(function(){
		toBirthdayService();
	});

	//更新菜单
	$('#menu_transfer').html('<b>移动商城</b>');

//	alert($('.current').text());

	//排除设置关注餐厅的页面
	if(window.location.href.indexOf('menu_setup_location.html') < 0){
		getDefaultCafetorium();
	}


	//初始化默认餐厅名字




//  var weather =
//			  '<div class="weather_wrap">'+
//			  	'<div class="wrap">'+
//				    '<div class="weather">'+
//					    '<span id="weather_icon"></span>'+
//					    '<span class="temperature"><span id="temperature"></span></span><span id="status"></span>'+
//				    '</div>'+
//				    '<div class="datetime">'+
//				    	'<span id="today"></span><span id="week"></span>'+
//				    '</div>'+
//			    '</div>'+
//			  '</div>'
//
//
//	$(".menu ul").append(weather);

  	//相对于html
//	$.getScript("js/weixinJs-init.js", function(){
//		if(initWeixinJs('onMenuShareAppMessage')){
//			wexinOnMenuShareAppMessage();
//		}
//	});
});

function getDefaultCafetorium(){
	$.ajax({
		url:'/weixin/getDefaultCafetorium.do',
		type:'POST',
		data:{
			'openId':getUrlParameter("openId")
		},
		dataType:'json',
		success:function(data){
			if(200 == data.status){

				if(!data.data){
//						$('.current #currentCafetorium').text("数据错误");
					window.location.href='menu_setup_location.html?openId='+getUrlParameter("openId");
					return;
				}
				
				$('.js-currentCafetorium').text(showCafetoriumName(data.data.cafeName));
				$('.js-currentCafetorium-detail').text(showDetailCafetoriumName(data.data.cafeName));



			}
			else{
//				alert(data.message);
//				window.location.href='code.html';

				if(confirm(data.message+",你要关注餐厅吗？"))
				{
					window.location.href='menu_setup_location.html?openId='+getUrlParameter("openId");
					return;
				}
			}
		}
	});
}

//取地址栏参数万能函数
function getQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

//判断空，不能判断空格
function isEmpty(str)
{
	//null undefined
	return !str || str.length <=0;
}

//自动解码经过encodeURIComponent加密过的中文
function getUrlParameter(name)
{
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	//window.location.search获取问号(?)+之后的所有参数
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)
	{
		return decodeURIComponent(r[2]); //解码中文
	}
	return null;
}


//菜单跳转


//var global_cafetoriumId = "10";
//今日点评
function toComment()
{
	var erroMsg = isAuthorized();
	if(!!erroMsg){
		alert(erroMsg);
	}

	location.href = "comment.html?cafetoriumId="+getUrlParameter("cafetoriumId")+"&openId="+getUrlParameter("openId");
}


//下周菜单
function toNextWeekMenu()
{
	var erroMsg = isAuthorized();
	if(!!erroMsg){
		alert(erroMsg);
	}
	location.href = "menu.html?openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
}

//天坊配送
function toSendByTianFang()
{
	var erroMsg = isAuthorized();
	if(!!erroMsg){
		alert(erroMsg);
	}
	location.href = "whatoeat.html?openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
}

//上门服务
function toVisitService()
{
	var erroMsg = isAuthorized();
	if(!!erroMsg){
		alert(erroMsg);
	}
	location.href = "chef.html?openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
}


//生日免单
function toBirthdayService()
{
	location.href = "birthday_menu.html?openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
}

//积分兑换
function toExchange()
{
	var erroMsg = isAuthorized();
	if(!!erroMsg){
		alert(erroMsg);
	}
	location.href = "score_exchange.html?openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
}

//查看订单
function toOrderList()
{
	location.href = "my_order_detail.html?orderId="+getUrlParameter("orderId")+"&openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
}

//付款页面
function toPay(order)
{
	location.href = "pay.html?openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId")+"&orderNo="+order.orderNo+"&goodsAmount="+order.goodsAmount+"&orderId="+order.orderId;
}

//判断用户是否微信登录
function isLoginWeiXin(){

	var openId = getUrlParameter("openId");
	if(isEmpty(openId))
	{
		alert("请关注天坊信息科技公众号，才可抽奖哦");
		return false;
	}

}

//授权
function isAuthorized()
{
	if(!getUrlParameter("openId")){
//		return "请先关注上海天坊信息科技";
		window.location.href='code.html';
	}

	if(!getUrlParameter("cafetoriumId")){
//		return "请先关注食堂";

		if(window.location.href.indexOf('menu_setup_location.html') < 0){
			window.location.href='menu_setup_location.html?openId='+getUrlParameter("openId");
		}
	}

	return null;
}

//截取9位字符串+...
function showCafetoriumName(name){
	if(isEmpty(name)){
		return "";
	}
	
	return name.length>9?name.substr(0,9)+"..."+" ":name;
	
}



//截取12位字符串+...
function showDetailCafetoriumName(name){
	if(isEmpty(name)){
		return "";
	}
	
	return name.length>12?name.substr(0,12)+"..."+" ":name;
	
}
