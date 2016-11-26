$(function(){
	
	//进入购物车页面
	$("#shopCart1,#submit-order").click(function(){
		window.location.href = "/weixin/cart.html?openId="+getQueryString('openId')+"&cafetoriumId="+getUrlParameter("cafetoriumId");
	});
	
	//限时抢购的更多按钮绑定click事件
	$("#limitedmore").click(function(){
		window.location.href = "/weixin/whatoeat_list.html?cafetoriumId="+getQueryString('cafetoriumId')+
				                 "&openId="+getQueryString('openId')+"&typeId="+1;
	});
	
	//进入订单页面
	$("#iconright").click(function(){
		window.location.href = "/weixin/my_order.html?openId=" + getQueryString('openId')+"&cafetoriumId="+getUrlParameter("cafetoriumId");
	});
	
	//查询商品详情页面头部的轮播广告图片
	jQuery.ajax({
			url:'/http/api/shop/searchGoodsImage.do?shopId='+getQueryString('shopId'),
			type:'POST',
			dataType:'json',
			success:function(data){
				var myObject = data;
				for (var i = 0; i < myObject.data.length; i++) {
					var u = $('<div id='+myObject.data[i].id+' class="wrap"/>');
					var p = $('<img src="'+myObject.data[i].imageId+'" border="0" alt=""/>');
					u.append(p);
					$('#swipe-wrap').append(u);
				}
				//设置轮播广告操作
				whatoeat();
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
		  url:"/http/api/shop/searchCountShoppingCart.do",
	  	  type:'POST',
	  	  dataType:'json',
	  	  data:{userId:getQueryString('openId')},
	  	  success:function(data){
	  		 $("#shopCart1").text("");
	  		 $("#shopCart1").text(JSON.stringify(data));
	  	  }
	 });
	 
	//商品详情页面下方限时抢购商品添加购物车
	 function addShopCart(id){
		 var datas ={
	  		  shopId:id,
	  		  userId:getQueryString('openId'),
	  		  shopNum:$("#count").text()
		  }
		  $.ajax({
		  	  url:"/http/api/shop/searchInsertShoppingCart.do",
		  	  type:'POST',
		  	  dataType:'json',
		  	  data:{data:JSON.stringify(datas)},
		  	  success:function(data){
		  		var oldcount = $("#shopCart1").text();
		  		var newcount = JSON.stringify(data);
		  		 if(newcount == 0){
		  			 $("#shopCart1").text("");
		  			 $("#shopCart1").text(oldcount);
		  			 alert("该商品超过限购上线!");
		  		 }else{
	  			    $("#shopCart1").text("");
	  			    $("#shopCart1").text(JSON.stringify(data));
	  			    cartBubble("count");
		  		 }
		  	  }
		  });
	 }
	//跳转到单件商品的详情页面
	 function commodityDetails(id){
		 var shopIdStr = window.location.href.substring(window.location.href.indexOf("=") + 1, window.location.href.indexOf("&"));
		 window.location = window.location.href.replace(shopIdStr, id);
	 }
	
	 //商品详情页面下方限时抢购商品信息
	 jQuery.ajax({
			url:'/http/api/shop/searchlimitStick.do?cafetoriumId='+getQueryString('cafetoriumId'),
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
	 
	 
	 //查询商品详情页面下方限时抢购商品信息
	 jQuery.ajax({
			url:'/http/api/shop/searchFindGoodsId.do?shopId='+getQueryString('shopId')+'&cafetoriumId='+getQueryString('cafetoriumId'),
			type:'POST',
			dataType:'json',
			success:function(data){
				var myObject = data;
				
				//商品Id
				$("#goodsId").text(myObject.data.id);
				//商品名称
				$("#sname").text(myObject.data.goodsName + "   " + myObject.data.unitsize);
				//商品价格
				$("#price").text(myObject.data.salesPrice);
				if(myObject.data.purchase != null){
					//限购
					$("#purchase").text("每人限购" + myObject.data.purchase + "件");
				}else{
					$("#purchase").text("");
				}
				
				//月销量 monthlysales
				$("#monthlysales").text("月销量" + myObject.data.monthlysales + "件");
				
				//进口/国产
				$("#importdomestic").text("进口/国产：" + myObject.data.importdomestic);
				//产地
				$("#placeorigin").text("产地：" + myObject.data.placeorigin);
				//配送方式
				$("#distributionmode").text("配送方式：" + myObject.data.distributionmode);
				//单位规格
				$("#unitsize").text("单位规格：" +  myObject.data.unitsize);
				//生产日期
				$("#productiondate").text("生产日期：" + myObject.data.productiondate);
				//保质期
				$("#shelflife").text("保质期：" + myObject.data.shelflife);
				//详情图片
				$("#imginfo").attr('src',myObject.data.detailsimgsrc);
				
				if(myObject.data.goodsTypeId == '1'){
					//商品限购的数量
					$("#purchaseNumber").val(myObject.data.purchase);
				}else{
					//商品限购的数量
					$("#purchaseNumber").val("");
				}
		  }
	 });
	 
		$(".add-cart").click(function(){
			 var datas ={
			  		  shopId:$("#goodsId").text(),
			  		  userId:getQueryString('openId'),
			  		  shopNum:$("#count").text()
				  }
				  $.ajax({
				  	  url:"/http/api/shop/searchInsertShoppingCart.do",
				  	  type:'POST',
				  	  dataType:'json',
				  	  data:{data:JSON.stringify(datas)},
				  	  success:function(data){
				  		var oldcount = $("#shopCart1").text();
				  		var newcount = JSON.stringify(data);
				  		 if(newcount == 0){
				  			 $("#shopCart1").text("");
				  			 $("#shopCart1").text(oldcount);
				  			 alert("该商品超过限购上线!");
				  		 }else{
			  			    $("#shopCart1").text("");
			  			    $("#shopCart1").text(JSON.stringify(data));
			  			    cartBubble("count");
				  		 }
				  	  }
				  });
	    });
});
