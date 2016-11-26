$(function(){
	
	
	//限时抢购的更多按钮绑定click事件
	$("#limitedmore").click(function(){
		window.location.href = appName+"/weixin/whatoeat_list.html?cafetoriumId="+getQueryString('cafetoriumId')+
				                 "&openId="+getQueryString('openId')+"&typeId="+1;
	});
	
	//精品推荐的更多按钮绑定click事件
	$("#finemore").click(function(){
		window.location.href = appName+"/weixin/whatoeat_list.html?cafetoriumId="+getQueryString('cafetoriumId')+
								"&openId="+getQueryString('openId')+"&typeId="+2;
	});
	
	
	//进入购物车页面
	$("#shopCart2").click(function(){
		window.location.href = appName+"/weixin/cart.html?openId="+getQueryString('openId')+"&cafetoriumId="+getUrlParameter("cafetoriumId");
	});
	
	//进入订单页面
	$("#iconright").click(function(){
		window.location.href = appName+"/weixin/my_order.html?openId=" + getQueryString('openId')+"&cafetoriumId="+getUrlParameter("cafetoriumId");
	});
	
	
	//查询首页轮播广告
	jQuery.ajax({
			url:appName+'/http/api/shop/searchGoodsIsTurn.do?cafetoriumId='+getQueryString('cafetoriumId'),
			type:'POST',
			dataType:'json',
			success:function(data){
				var myObject = data;
				for (var i = 0; i < myObject.data.goodsIsTurnList.length; i++) {
					var u = $('<div id='+myObject.data.goodsIsTurnList[i].id+' class="wrap"/>');
					var p = $('<img id='+myObject.data.goodsIsTurnList[i].id+' class="turnid" src="'+myObject.data.goodsIsTurnList[i].turnIcon+'" border="0" alt="'+myObject.data.goodsIsTurnList[i].introduce+'" />');
					u.append(p);
					$('#swipe-wrap').append(u);
				}
				
				//设置第一张轮播广告的title
				if(myObject.data.goodsIsTurnList.length>0){
					$("#mes").text(myObject.data.goodsIsTurnList[0].introduce);
				}
				//设置轮播广告操作
				whatoeat();
		  },
		  complete:function(){
			    //给图片绑定click事件跳转到单件商品的详情页面
			    $(".turnid").click(function(){
			    	 var id = $(this).attr("id");
			    	 commodityDetails(id);
			    });
		  }
	 });
	 
	 //设置轮播广告的宽度
	 function whatoeat(){
		 	var bullets = document.getElementById('position').getElementsByTagName('li');
			var banner = Swipe(document.getElementById('mySwipe'), {
				auto: 2000,
				continuous: true,
				disableScroll:false,
				callback: function(pos) {
					var t = $('.swipe-wrap').find('.wrap').eq(pos).find('img').attr('alt')
					$('#mes').text(t)
					var i = bullets.length;
					while (i--) {
					  bullets[i].className = ' ';
					}
					bullets[pos].className = 'cur';
					
				}
			});
	 }
	 
	 //初始化页面中的购物车商品数量
	 jQuery.ajax({
		  url:appName+"/http/api/shop/searchCountShoppingCart.do",
	  	  type:'POST',
	  	  dataType:'json',
	  	  data:{userId:getQueryString('openId')},
	  	  success:function(data){
	  		 $("#shopCart1").text("");
	  		 $("#shopCart2").text("");
	  		 $("#shopCart1").text(JSON.stringify(data));
			 $("#shopCart2").text(JSON.stringify(data));
	  	  }
	 });
	 
	 
	 
	 //添加购物车
	 function addShopCart(id){
		 var datas ={
	  		  shopId:id,
	  		  userId:getQueryString('openId'),
	  		  shopNum:1
		  }
		  $.ajax({
		  	  url:appName+"/http/api/shop/searchInsertShoppingCart.do",
		  	  type:'POST',
		  	  dataType:'json',
		  	  data:{data:JSON.stringify(datas)},
		  	  success:function(data){
		  		 var oldcount = $("#shopCart1").text();
		  		 var newcount = JSON.stringify(data);
		  		 if(newcount == 0){
		  			$("#shopCart1").text("");
	  			    $("#shopCart2").text("");
	  			    $("#shopCart1").text(oldcount);
	  			    $("#shopCart2").text(oldcount);
		  			alert("该商品超过限购上线!");
		  		 }else{
	  			    $("#shopCart1").text("");
	  			    $("#shopCart2").text("");
	  			    $("#shopCart1").text(JSON.stringify(data));
	  			    $("#shopCart2").text(JSON.stringify(data)); 
	  			    cartBubble("count");
		  		 }
		  	  }
		  });
	 }
	 
	 //跳转到单件商品的详情页面
	 function commodityDetails(id){
		 window.location.href = appName+"/weixin/whatoeat_detail.html?shopId="+id+"&openId="+getQueryString('openId')+"&cafetoriumId="+getQueryString('cafetoriumId');
	 }

	 
	 //查询首页限时特购商品信息
	 jQuery.ajax({
			url:appName+'/http/api/shop/searchlimitStick.do?cafetoriumId='+getQueryString('cafetoriumId'),
			type:'POST',
			dataType:'json',
			success:function(data){
				var myObject = data;
				for (var i = 0; i < myObject.data.goodslimitStickList.length; i++) {
					var u = $('<li><img src="'+myObject.data.goodslimitStickList[i].icon+'" border="0" class="img1" id='+myObject.data.goodslimitStickList[i].id+'><p>'
							+'<span class="title">'+myObject.data.goodslimitStickList[i].introduce+'</span>'
							+'<span class="onsale">￥'+myObject.data.goodslimitStickList[i].salesPrice+'</span>'
							+'<span class="price">￥'+myObject.data.goodslimitStickList[i].price+'</span>'
							+'<a class="add" href="javascript:void(0)" id='+myObject.data.goodslimitStickList[i].id+' ></a>'
							+'</p></li>');
					$('#panic_buying_limit').append(u);
				}
		  },
		  complete:function(){
			  	//给添加购物车按钮绑定click
			    $('.add').unbind('click').bind('click',function(){
					 var id = $(this).attr("id");
					 addShopCart(id);
				});
			    
			    //给图片绑定click事件跳转到单件商品的详情页面
			    $(".img1").click(function(){
			    	 var id = $(this).attr("id");
			    	 commodityDetails(id);
			    });
		  }
	 });

	 //查询首页精品推荐商品信息
	 jQuery.ajax({
			url:appName+'/http/api/shop/searchFineStick.do?cafetoriumId='+getQueryString('cafetoriumId'),
			type:'POST',
			dataType:'json',
			success:function(data){
				var myObject = data;
				for (var i = 0; i < myObject.data.goodsFineStickList.length; i++) {
					var u = $('<li><img src="'+myObject.data.goodsFineStickList[i].icon+'" border="0"  class="img2" id='+myObject.data.goodsFineStickList[i].id+'><p>'
							+'<span class="title">'+myObject.data.goodsFineStickList[i].introduce+'</span>'
							+'<span class="onsale">￥'+myObject.data.goodsFineStickList[i].salesPrice+'</span>'
							+'<span class="price">￥'+myObject.data.goodsFineStickList[i].price+'</span>'
							+'<a class="add" href="javascript:void(0)" id='+myObject.data.goodsFineStickList[i].id+'></a>'
							+'</p></li>');
					$('#panic_buying_fine').append(u);
				}
		  },
		  complete:function(){
			  //给添加购物车按钮绑定click
			  $('.add').unbind('click').bind('click',function(){
				 var id = $(this).attr("id");
				 addShopCart(id);
			  });
			 //给图片绑定click事件跳转到单件商品的详情页面
			 $(".img2").click(function(){
		    	 var id = $(this).attr("id");
		    	 commodityDetails(id);
			 });
		  }
	 });
	 
});
