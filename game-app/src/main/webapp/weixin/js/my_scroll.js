var myScroll,
	pullDownEl, pullDownOffset,
	pullUpEl, pullUpOffset,
	generatedCount = 0,
	page = 1,
	rows = 2;

/**
 * 下拉刷新 （自定义实现此方法）
 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
 */

//进入购物车页面
$("#shopCart2,#submit-order").click(function(){
	window.location.href = "cart.html?openId="+getQueryString('openId')+"&cafetoriumId="+getUrlParameter("cafetoriumId");
});

//进入订单页面
$("#iconright").click(function(){
	window.location.href ="my_order.html?openId=" + getQueryString('openId')+"&cafetoriumId="+getUrlParameter("cafetoriumId");
});

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


//分页查询相应食堂的限时抢购商品信息
function pageLimited(){
	 jQuery.ajax({
			url:appName+'/http/api/shop/searchGoodsLimited.do?cafetoriumId='+getQueryString('cafetoriumId')+"&page="+page+"&rows="+rows,
			type:'POST',
			dataType:'json',
			success:function(data){
				var myObject = data;
				if(myObject.data != null){
					for (var i = 0; i < myObject.data.goodslimitList.length; i++) {
						var u = $('<li><img src="'+myObject.data.goodslimitList[i].icon+'" border="0" class="img1" id='+myObject.data.goodslimitList[i].id+'><p>'
								+'<span class="title">'+myObject.data.goodslimitList[i].introduce+'</span>'
								+'<span class="onsale">￥'+myObject.data.goodslimitList[i].salesPrice+'</span>'
								+'<span class="price">￥'+myObject.data.goodslimitList[i].price+'</span>'
								+'<a class="add" href="javascript:void(0)" id='+myObject.data.goodslimitList[i].id+' ></a>'
								+'</p></li>');
						$('#thelist').append(u);
					}
					page = page + 1;
				}else{
					$(function(){
						$(".pullUpIcon").hide();
						$(".pullUpLabel").hide();
						$("#pullUp").append("span").text("已经加载全部");
					});	
					
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
}
//分页查询相应食堂的商品信息
function pagefine(){
	 jQuery.ajax({
			url:appName+'/http/api/shop/searchGoodsFine.do?cafetoriumId='+getQueryString('cafetoriumId')+"&page="+page+"&rows="+rows,
			type:'POST',
			dataType:'json',
			success:function(data){
				var myObject = data;
				if(myObject.data != null){
					for (var i = 0; i < myObject.data.goodsFineList.length; i++) {
						var u = $('<li><img src="'+myObject.data.goodsFineList[i].icon+'" border="0" class="img1" id='+myObject.data.goodsFineList[i].id+'><p>'
								+'<span class="title">'+myObject.data.goodsFineList[i].introduce+'</span>'
								+'<span class="onsale">￥'+myObject.data.goodsFineList[i].salesPrice+'</span>'
								+'<span class="price">￥'+myObject.data.goodsFineList[i].price+'</span>'
								+'<a class="add" href="javascript:void(0)" id='+myObject.data.goodsFineList[i].id+' ></a>'
								+'</p></li>');
						$('#thelist').append(u);
					}
					page = page + 1;
				}else{					
					$(function(){
						$(".pullUpIcon").hide();
						$(".pullUpLabel").hide();
						$("#pullUp").append("span").text("已经加载全部");
					});	
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
}


//初始化页面数据
var flag = location.search.substr(location.search.lastIndexOf('=')+1);
if(flag == 1){
	pageLimited();
}
if(flag == 2){
	pagefine();
}

/**
 * 滚动翻页 （自定义实现此方法）
 * myScroll.refresh();		// AJAX数据加载完成后，调用界面更新方法
 */
function pullUpAction () {
	setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
		//调用分页查询对应食堂商品信息的函数 1:限时抢购 2:精品推荐
		if(flag == 1){
			pageLimited();
		}
		if(flag == 2){
			pagefine();
		}
		myScroll.refresh();		// 数据加载完成后，调用界面更新方法 Remember to refresh when contents are loaded (ie: on ajax completion)
	}, 1000);	// <-- Simulate network congestion, remove setTimeout from production!
}

/**
 * 初始化iScroll控件
 */
function loaded() {
	pullDownEl = document.getElementById('pullDown');
	pullDownOffset = pullDownEl.offsetHeight;
	pullUpEl = document.getElementById('pullUp');
	pullUpOffset = pullUpEl.offsetHeight;

	myScroll = new iScroll('scroll_wrapper', {
		scrollbarClass: 'myScrollbar', /* 重要样式 */
		useTransition: false, /* 此属性不知用意，本人从true改为false */
		topOffset: pullDownOffset,
		onRefresh: function () {
			if (pullUpEl.className.match('loading')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
			}
		},
		onScrollMove: function () {
			if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '松手开始更新...';
				this.maxScrollY = this.maxScrollY;
			} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
				this.maxScrollY = pullUpOffset;
			}
		},
		onScrollEnd: function () {
			if (pullUpEl.className.match('flip')) {
				pullUpEl.className = 'loading';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
				pullUpAction();	// Execute custom function (ajax call?)
			}
		}
	});

	setTimeout(function () { document.getElementById('scroll_wrapper').style.left = '0'; }, 800);
}

//初始化绑定iScroll控件
document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', loaded, false);

