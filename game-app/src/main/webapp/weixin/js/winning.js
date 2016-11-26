function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}
function open_comment_thank(){
       //更新抽奖记录表的兑换字段为1（已兑换）以及更新兑换时间
	   $.post(appName+"/luckDraw/updateIsExchange.do", {
 			"openId" : getUrlParam("openId"),
 			 "exchargePhone" : $("#phone").val(),
 			 "cafetoriumId"  : getUrlParam("cafetoriumId")
 		},function(data) {
 		   if(data.status==500){
  			  alert(data.message);
  				}
  			}); 
	
	location.href = "comment_thank.html?openId="+getUrlParam("openId")+"&cafetoriumId="+getUrlParam("cafetoriumId")+"&phone="+$("#phone").val();
}

$(function(){

  $(".body").css("min-height",$(window).height() - $(".header").height());
  //var telePhone= $(".cellphone").val();
  $("#bt").click(function(){
	
	  open_comment_thank();
  });

});
