$(function(){
	//获取地址orderNo
	var goodsAmount = getUrlParameter("goodsAmount");
	var orderId= getUrlParameter("orderId");
	//支付页面订单金额
    $(".info_wrap p.price").text(parseFloat(goodsAmount).toFixed(2));
    $("#huodao").removeClass("active");
    $("#weixin").removeClass("active");
    var openId=getUrlParameter("openId");
    var orderNo=getUrlParameter("orderNo");
    $("#weixin").click(function(){
  	 $(this).toggleClass("active");
       $("#huodao").removeClass("active");
       
    });
    $("#huodao").click(function(){
  		 $(this).toggleClass("active");
  	     $("#weixin").removeClass("active");
    });

    $(".submit-bt").click(function(){
  	  if( $("#weixin").hasClass("active")){
  		location.href="pay.html?openId="+openId+"&cafetoriumId="+getUrlParameter("cafetoriumId")+"&orderNo="+orderNo+"&goodsAmount="+goodsAmount+"&orderId="+orderId;
      }
  	  else if( $("#huodao").hasClass("active")){
  		$.ajax({
  			url:appName+'/ourOrder/payDelivery.do',
  			type:'POST',
  			dataType:'json',
  			data:{
  				"openId":openId,
  				"orderNo":orderNo,
  				"payWay":"2"
  			},
  			success:function(data)
  			{
  				if(200 == data.status)
  				{
  	  		      	location.href="pay_confirm.html?orderNo="+orderNo+"&orderId="+orderId+"&openId="+openId+"&cafetoriumId="+getUrlParameter("cafetoriumId");
  				}
  				else
  				{
  					alert(data.message);
  					return;
  				}
  			}		
  		});
      }else{
    	  alert("请选择支付方式!");
      }
    });
    
});
