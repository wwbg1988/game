$(function(){

  $(".checkbox").click(function(){
     $(this).toggleClass("active");
  });

	var orderId = getUrlParameter("orderId");
  //动态生成订单商品信息列表
  jQuery.ajax({
		url:'/ourOrder/ourOrderDetail.do?orderId='+orderId,
		type:'POST',
		dataType:'json',
//		async:false,
		success:function(data){
			
			if(!data)
			{
				alert("订单为空");
				return;
			}
			
			//var myObject = data;
			var t = '';
			with(data) {
			var p = '<li><div class="wrap clearfix">订单编号 <span class="orderNo" >'+orderNo+'</span></div></li>'+
         '<li><div class="wrap clearfix">下单时间 <span class="time">'+createTime+'</span></div></li>'+
         '<li><div class="wrap clearfix">支付方式 <span>';
           if(payWay=='2')p +='货到付款';
           if(payWay=='1')p +='微信支付';
           p +='</span></div></li><li><div class="wrap clearfix">订单金额 <span class="price">￥'+parseFloat(goodsAmount).toFixed(2)+'</span></div></li>'+
         '<li><div class="wrap clearfix">包裹数量 <span>'+accountGoodsType+'个（含'+(accountGoodsOne + accountGoodsTwo)+'件商品）</span></div></li>';
			$(".order_info").append(p);

			if(orderDetailOneList==''){
				t ='<h3>包裹1</h3><div class="parcel"><h4><div class="wrap">';
				if(shippingStatus==0) t +='未发货';
				if(shippingStatus==1) t +='已发货';
				if(shippingStatus==2) t +='已收货';
				if(shippingStatus==3) t +='备货中';
				if(shippingStatus==null) t +='发货中';
				t +='</div></h4><div class="pic_wrap"><div class="wrap">';
				var len = Math.min(orderDetailTwoList.length, 4);
				for (var j = 0 ; j < len ; j++) {
					if (!!(orderDetailTwoList[j].icon)) {
						t += '<img src="'+orderDetailTwoList[j].icon+'"/>'
					}
				}
				t +='</div></div><div class="total_wrap"><div class="wrap">共'+accountGoodsTwo+'件商品 合计：<span class="total color">￥'+parseFloat(goodsIdAmountTwo).toFixed(2)+'</span></div></div></div>';  
			}	
			if(orderDetailTwoList==''){
				t +='<h3>包裹1</h3><div class="parcel"><h4><div class="wrap">请前往食堂提货</div></h4><div class="pic_wrap"><div class="wrap">';
				var len = Math.min(orderDetailOneList.length, 4);
				for (var j = 0 ; j < len ; j++) {
					if (!!(orderDetailOneList[j].icon)) {
						t += '<img src="'+orderDetailOneList[j].icon+'"/>'
					}
				}
				t +='</div></div><div class="total_wrap"><div class="wrap">共'+accountGoodsOne+'件商品 合计：<span class="total color">￥'+parseFloat(goodsIdAmountOne).toFixed(2)+'</span></div></div></div>';  
			}	
			if(orderDetailOneList!='' && orderDetailTwoList!=''){
				t +='<h3>包裹1</h3><div class="parcel"><h4><div class="wrap">请前往食堂提货</div></h4><div class="pic_wrap"><div class="wrap">';
				var len = Math.min(orderDetailOneList.length, 4);
				for (var j = 0 ; j < len ; j++) {
					if (!!(orderDetailOneList[j].icon)) {
						t += '<img src="'+orderDetailOneList[j].icon+'"/>'
					}
				}
				t +='</div></div><div class="total_wrap"><div class="wrap">共'+accountGoodsOne+'件商品 合计：<span class="total color">￥'+parseFloat(goodsIdAmountOne).toFixed(2)+'</span></div></div></div>';
				//包裹二
				t +='<h3>包裹2</h3><div class="parcel"><h4><div class="wrap">';
				if(shippingStatus==0) t +='未发货';
				if(shippingStatus==1) t +='已发货';
				if(shippingStatus==2) t +='已收货';
				if(shippingStatus==3) t +='备货中';
				if(shippingStatus=='') t +='发货中';
				t +='</div></h4><div class="pic_wrap"><div class="wrap">';
				var len = Math.min(orderDetailTwoList.length, 4);
				for (var j = 0 ; j < len ; j++) {
					if (!!(orderDetailTwoList[j].icon)) {
						t += '<img src="'+orderDetailTwoList[j].icon+'"/>'
					}
				}
				t +='</div></div><div class="total_wrap"><div class="wrap">共'+accountGoodsTwo+'件商品 合计：<span class="total color">￥'+parseFloat(goodsIdAmountTwo).toFixed(2)+'</span></div></div></div>'; 
			}	
			$(".parcel").after(t);
	
			
//			var status = da.status;
//			if(status!=200){
//				return;
//			}
//			//展示订单详情属性
//			$(".order_info span.orderId").text(orderNo);//订单编号
//			$(".order_info span.time").text(createTime);//订单创建时间
//			$(".order_info span.price").text(goodsAmount);//订单总金额
//			$(".leader_info p.location").text(data.addressUser.addressCafetorium);//商品数量
//			$(".leader_info p.company_name").text(data.addressUser.cafetorium);//包裹数量
           
            
		}}		
  });		
});
