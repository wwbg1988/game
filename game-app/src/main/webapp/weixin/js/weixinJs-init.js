$(document).ready(function(){
	$.getScript("http://res.wx.qq.com/open/js/jweixin-1.0.0.js", function(){
//		alert('ok')
		if(initWeixinJs('onMenuShareAppMessage')){
			wexinOnMenuShareAppMessage();
		}
	});
	
});

//weinJS初始化成功
function wx_success()
{
	var wxTitle='团餐';//标题
	var wxLink='www.baidu.com';//链接
	var wxImgUrl='';//图片
	var wxDesc='团餐';
	
	//分享到朋友圈
	wx.onMenuShareTimeline({
	    title: wxTitle, // 分享标题
	    link: wxLink, // 分享链接
	    imgUrl: imgUrl, // 分享图标
	    success: function () { 
	        // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	    }
	});
	
	//分享到朋友
	wx.onMenuShareAppMessage({
	    title: wxTitle, // 分享标题
	    desc: wxDesc, // 分享描述
	    link: wxLink, // 分享链接
	    imgUrl: wxImgUrl, // 分享图标
	    type: 'link', // 分享类型,music、video或link，不填默认为link
	    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
	    success: function () { 
	        // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	    }
	});
	
	//分享到QQ
	wx.onMenuShareQQ({
	    title: wxTitle, // 分享标题
	    desc: wxDesc, // 分享描述
	    link: wxLink, // 分享链接
	    imgUrl: wxImgUrl, // 分享图标
	    success: function () { 
	       // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	       // 用户取消分享后执行的回调函数
	    }
	});
	
	//分享到腾讯微博
	wx.onMenuShareWeibo({
	    title: wxTitle, // 分享标题
	    desc: wxDesc, // 分享描述
	    link: wxLink, // 分享链接
	    imgUrl: wxImgUrl, // 分享图标
	    success: function () { 
	       // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	    }
	});
	
	//分享到QQ空间
	wx.onMenuShareQZone({
	    title: wxTitle, // 分享标题
	    desc: wxDesc, // 分享描述
	    link: wxLink, // 分享链接
	    imgUrl: wxImgUrl, // 分享图标
	    success: function () { 
	       // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	    }
	});
}

//参数:需要使用的JS接口列表，所有JS接口列表见附录2
function initWeixinJs(method)
{
	$.ajax({
		url:'/weixin/getWeiXinJSAuthority.do',
		type:'POST',
		data:{
			'url':location.href.split('#')[0]
		},
		dataType:'json',
		success:function(data)
		{
			if(200 == data.status)
			{
				
			    try{
					//微信js初始化
					var wx_config = wx.config({
//						    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
						    appId: data.data.appId, // 必填，公众号的唯一标识
						    timestamp: data.data.timestamp, // 必填，生成签名的时间戳
						    nonceStr: data.data.nonceStr, // 必填，生成签名的随机串
						    signature: data.data.signature,// 必填，签名，见附录1
						    jsApiList: [method], // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
						});
				
					wx.ready(function(){
						wx_success();
					    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
					});
					
					wx.error(function(res){
					    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
	//						wx_first_error(res);
						
						if(!!res && "config:invalid signature" == res.errMsg)
					    {
					    	getNewWeiXinJS(method);
					    	return;
					    }
						
						alert(data.message);
					});
				}catch (e) {
					// TODO: handle exception
				}
			}
			else if(405 == data.status)
			{
				//微信功能未开启
				alert(data.message);
			}
			else
			{
				alert(data.message);
			}
			
		}
	});
}



function getNewWeiXinJS(method)
{
	$.ajax({
		url:'/weixin/getNewWeiXinJSAuthority.do',
		type:'POST',
		data:{
			'url':location.href.split('#')[0]
		},
		dataType:'json',
		success:function(data)
		{
			if(200 == data.status)
			{
				try{
					wx.config({
					    //debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
					    appId: data.data.appId, // 必填，公众号的唯一标识
					    timestamp: data.data.timestamp, // 必填，生成签名的时间戳
					    nonceStr: data.data.nonceStr, // 必填，生成签名的随机串
					    signature: data.data.signature,// 必填，签名，见附录1
					    jsApiList: [method] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
					});
					
					wx.ready(function(){
						wx_success();
					    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
					});
					
					wx.error(function(res){
						alert(res.errMsg);
					    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
					});
				}catch(e){
					// TODO: handle exception
				}
				
				
			}
			else if(405 == data.status)
			{
				//微信功能未开启
				alert(data.message);
			}
			else
			{
				alert(data.message);
			}			
		}
	});
	
}