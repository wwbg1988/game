/*
$(document).ready(function(){
	initWx();
});
*/
/**
 * 通过微信获取地址经纬度
 */
function getCityNameByWeiXin(){
	
}

function getWeatherClassName(weather)
{
	switch (weather) {
	case '晴':
		return 'qing';
//		break;
	case '多云':
		return 'duoyun';
//      break;
	case '阴':
		return 'yin';
//		break;
	case '阵雨':
		return 'zhengyu';
//		break;
	case '雷阵雨':
		return 'leizhenyu';
//		break;
	case '雷阵雨伴有冰雹':
		return 'leizhenyubanyoubingbao';
//		break;
	case '雨夹雪':
		return 'yujiaxue';
//		break;
	case '小雨':
		return 'xiaoyu';
//		break;
	case '中雨':
		return 'zhongyu';
//		break;
	case '大雨':
		return 'dayu';
//		break;
	case '暴雨':
		return 'baoyu';
//		break;
	case '大暴雨':
		return 'dabaoyu';
//		break;
	case '特大暴雨':
		return 'tedabaoyu';
//		break;
	case '阵雪':
		return 'zhenxue';
//		break;
	case '小雪':
		return 'xiaoxue';
//		break;
	case '中雪':
		return 'zhongxue';
//		break;
	case '大雪':
		return 'daxue';
//		break;
	case '暴雪':
		return 'baoxue';
//		break;
	case '雾':
		return 'wu';
//		break;
	case '冻雨':
		return 'dongyu';
//		break;
	case '沙尘暴':
		return 'shachenbao';
//		break;
	case '小雨转中雨':
		return 'zhongxue';
//		break;
	case '中雨转大雨':
		return 'dayu';
//		break;
	case '大雨转暴雨':
		return 'baoyu';
//		break;
	case '暴雨转大暴雨':
		return 'dabaoyu';
//		break;
	case '大暴雨转特大暴雨':
		return 'tedabaoyu';
//		break;
	case '小雪转中雪':
		return 'zhongxue';
//		break;
	case '中雪转大雪':
		return 'daxue';
//		break;	
	case '大雪转暴雪':
		return 'baoxue';
//		break;	
	case '浮尘':
		return 'fuchen';
//		break;	
	case '扬沙':
		return 'yangsha';
//		break;	
	case '强沙尘暴':
		return 'qiangshachenbao';
//		break;	
	case '霾':
		return 'mai';
//		break;	
	default:
		return 'qing';
		break;
	}
}


//授权getLocation方法
//显示天气，这个只更新温度和天气情况
function showWeather()
{
	//默认上海
	var latitude = 31.216312;//维度
	var longitude = 121.45619;//经度
	
	//调用微信的getLocation方法
	var getLocation = wx.getLocation({
		    type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
		    success: function (res) {
		       latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
		       longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
		    }
		});
	
	//判断微信的getLocation方法是否可用
	if(!getLocation)
	{
		latitude = 31.216312;//维度
		longitude = 121.45619;//经度
	}

	//获取天气信息
	$.ajax({
		url : '/weather/getWeatherByCity.do',
		type : 'POST',
		dataType : 'json',
		data : {
			'city' : longitude + ',' + latitude
		},
		success : function(data) {
			if (!!data.data) {
				$('#temperature').text(data.data.nowTemperature);//温度
				$('#status').text(data.data.weather);//天气情况
				$('#weather_icon').attr('class',getWeatherClassName(data.data.weather));
			} else {
				alert('获取天气失败');
			}
		}
//		,
//		complete : function(XMLHttpRequest, textStatus)
//		{
//		}
	});
}

//ws签名验证成功
function wx_ready()
{
	//显示天气，这个只更新温度和天气情况
	showWeather();
}

//第一次请求签名
function wx_first_error(res)
{
	if(!!res && "config:invalid signature" == res.errMsg)
    {
    	sendNew();
    }
   
}

//使用新的access_token和jsapi_ticket(处理过期的情况)
function wx_second_error(res)
{
	if(!!res)
    {
//    	alert("微信js授权失败，请联系开发者。错误信息：second:"+res.errMsg);
		showWeather();
    }
}

function initWx()
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
				//微信js初始化
				var wx_config = wx.config({
//					    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
					    appId: data.data.appId, // 必填，公众号的唯一标识
					    timestamp: data.data.timestamp, // 必填，生成签名的时间戳
					    nonceStr: data.data.nonceStr, // 必填，生成签名的随机串
					    signature: data.data.signature,// 必填，签名，见附录1
					    jsApiList: ['getLocation'], // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
					});
			
				if(!!wx_config)
				{
					wx.ready(function(){
						wx_ready();
					    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
					});
					
					wx.error(function(res){
					    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
						wx_first_error(res);
					});
				}
				else
				{//默认情况，兼容电脑
					showWeather();
				}
			}
			else if(405 == data.status)
			{
				//微信功能未开启
				showWeather();
			}
			else
			{
				alert(data.message);
			}
			
		}
	});
}

//使用新的access_token和jsapi_ticket(处理过期的情况)
function sendNew()
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
				wx.config({
				    //debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				    appId: data.data.appId, // 必填，公众号的唯一标识
				    timestamp: data.data.timestamp, // 必填，生成签名的时间戳
				    nonceStr: data.data.nonceStr, // 必填，生成签名的随机串
				    signature: data.data.signature,// 必填，签名，见附录1
				    jsApiList: ['getLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});
				
				wx.ready(function(){
					wx_ready();
				    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
				});
				
				wx.error(function(res){
					wx_second_error(res);
				    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
				});
				
				//默认情况，兼容电脑
				if(!wx_config)
				{
					showWeather();
				}
			}
			else if(405 == data.status)
			{
				//微信功能未开启
				showWeather();
			}
			else
			{
				alert(data.message);
			}			
		}
	});
	
}