$(function(){
	
	function add_total_Price(){
		var _total_price = $('.t_total_price')
		var _cur_price1 = 0, _cur_price2 = 0
		if($('.take_box').length > 0){
			if($('.take_box h3 > .totalprice').length != 0){
				_cur_price1 = parseFloat($('.take_box h3 > .totalprice').text()).toFixed(2)
			}
		}
		if($('.deliver_box').length > 0){
			if($('.deliver_box h3 > .totalprice').length != 0){
				_cur_price2 = parseFloat($('.deliver_box h3 > .totalprice').text()).toFixed(2)
			}
		}
		_total_price.text('¥' + (parseFloat(_cur_price1) + parseFloat(_cur_price2)).toFixed(2));
	}
	
	function add_orderPrice(p){
		var _total_price = $('.' + p + ' h3 > .totalprice'), _cur_price = 0;
		$('.' + p + ' > ul > li').each(function(){
			_cur_price += parseInt($(this).find('.count').text()) * parseFloat($(this).find('.price').text()).toFixed(2)
		});
		_total_price.text(parseFloat(_cur_price).toFixed(2))
		add_total_Price()
	}
	
	$(document).ajaxStop(function(){
		 //初始化也页面包裹数量 
		 $("#boxnum").text($("span[name='bagnum']").length);
	});
	
	 //动态生成购物车限时抢购商品信息列表
	 jQuery.ajax({

			url:appName+'/http/api/shop/searchShoppingCartLimitInfo.do?userId='+getQueryString('openId'),
			type:'POST',
			dataType:'json',
			success:function(data){
				var myObject = data;
				var p = '';
				if(myObject.data != undefined){
					if(myObject.data.length > 0){
						var h = $('<h3><span name="bagnum">包裹1</span><span>自行提货</span><span class="totalprice"></span></h3>');
						$(".take_box").append(h);
					}
					for (var i = 0; i < myObject.data.length; i++) {
						p = $('<li><img src="'+myObject.data[i].icon+'" border="0">'
								+'<span class="title">'+myObject.data[i].goodsName+'</span>'
								+'<span class="price">'+myObject.data[i].price+'</span>'
								+'<ul>'
									+'<li id='+myObject.data[i].goodsId+'>-</li>'
									+'<li class="count">'+myObject.data[i].count+'</li>'
									+'<li id='+myObject.data[i].goodsId+'>+</li>'
								+'</ul>'
								+'<a href="javascript:void(0)" class="del" id ='+myObject.data[i].goodsId+'>删除</a>'
								+'</li>');
						$('#limite').append(p);
					}
					var q = $('<div class="form_row">'
							+'<label id="name_label" for="name">姓名</label>'
							+'<input type="text" id="name" name="name" value="" placeholder="请输入收货人姓名">'
							+'</div>'
							+'<div class="form_row">'
							+'<label id="phone_label" for="phone">手机</label>'
							+'<input type="text" id="phone" name="phone" value="" placeholder="请输入手机号码">'
							+'</div>');
					$('#limite').append(q);
					
					$('.take_box').append($('#limite'));
				}
				add_orderPrice('take_box');
		  },
		  complete:function(){
			  	//给添加购物车按钮绑定click
			    $('.del').unbind('click').bind('click',function(){
					 var id = $(this).attr("id");
					 deleteShopInfoById(id,this);
				});
			    if(!isBinded) {
			    	isBinded = true;
			    } else
			    	bindClickEvent();
		  }
	 });
	
	 var isBinded = false;
	 
	//动态生成购物车精品推荐商品信息列表
	 jQuery.ajax({
			url:appName+'/http/api/shop/searchShoppingCartFineInfo.do?userId='+getQueryString('openId'),
			type:'POST',
			dataType:'json',
			success:function(data){
				var myObject = data;
				var p = '';
				if(myObject.data != undefined){
					var h = $('<h3><span name="bagnum">包裹2</span><span>送货上门</span><span class="totalprice"></span></h3>');
					$(".deliver_box").append(h);
				
				for (var i = 0; i < myObject.data.length; i++) {
					p = $('<li><img src="'+myObject.data[i].icon+'" border="0">'
							+'<span class="title">'+myObject.data[i].goodsName+'</span>'
							+'<span class="price">'+myObject.data[i].price+'</span>'
							+'<ul>'
								+'<li id='+myObject.data[i].goodsId+'>-</li>'
								+'<li class="count">'+myObject.data[i].count+'</li>'
								+'<li id='+myObject.data[i].goodsId+'>+</li>'
							+'</ul>'
							+'<a href="javascript:void(0)" id ='+myObject.data[i].goodsId+' class="dele">删除</a>'
							+'</li>');
					$('#fine').append(p);
				}
				var q = $('<div class="form_row">'
				          +'<label id="name_label2" for="name2">姓名</label>'
				          +'<input type="text" id="name2" name="name2" value="" placeholder="请输入收货人姓名">'
				          +'</div>'

				          +'<div class="form_row">'
				          +'<label id="phone_label2" for="phone2">手机</label>'
				          +'<input type="text" id="phone2" name="phone2" value="" placeholder="请输入手机号码">'
				          +'</div>'

				          +'<div class="form_row">'
				          +'<label id="floor_label2" for="floor2">楼层</label>'
				          +'<input type="text" id="floor2" name="floor2" value="" placeholder="请输入具体楼层">'
				          +'</div>'

				          +'<div class="form_row">'
				          +'<label id="department_label2" for="department2">部门</label>'
				          +'<input type="text" id="department2" name="department2" value="" placeholder="请输入部门名称">'
				          +'</div>');
				$('#fine').append(q);
				$(".deliver_box").append($('#fine'));
			}
				add_orderPrice('deliver_box');
		  },
		  complete:function(){
			  	//给添加购物车按钮绑定click
			    $('.dele').unbind('click').bind('click',function(){
					 var id = $(this).attr("id");
					 deleteShopInfoById(id,this);
				});
			    if(!isBinded) {
			    	isBinded = true;
			    } else
			    	bindClickEvent();
		  }
	 });
	 

	 //删除购物车某件商品
	 function deleteShopInfoById(id,obj){
			  $.ajax({
			  	  url:appName+"/http/api/shop/searchDeleteShoppingCart.do?userId="+getQueryString('openId')+"&shopId="+id,
			  	  type:'POST',
			  	  dataType:'json',
			  	  success:function(data){
			  		var flag = JSON.stringify(data);
			  		if(flag > 0){
			  			//后台数据删除成功之后移除页面元素
			  			delShop(obj);
			  			//成功移除页面元素之后对页面包裹数量进行更新 
			  			$("#boxnum").text($("span[name='bagnum']").length);
			  		}else{
			  			alert("删除失败请稍候再试!")
			  		}
			  	  }
			  });
	 }
	 
	 //修改商品的库存量
	 function updateShopIdNum(id,count,marktext,obj){
			  $.ajax({
			  	  url:appName+"/http/api/shop/searchupdateShopIdNum.do?shopId="+id+"&userId="+getQueryString('openId')+"&shopNum="+count+"&marktext="+marktext,
			  	  type:'POST',
			  	  dataType:'json',
			  	  beforeSend:function(){//触发ajax请求开始时移除事件绑定
			  		  $('.take_box ul > li > ul, .deliver_box ul > li > ul').off('click','li');
			  	  },
			  	  success:function(data){
			  		var flag = JSON.stringify(data);
			  		 if(flag == 0){
			  			 alert("该商品超过限购上线!");
			  		 }else if(flag == -1){
			  			 alert("变更商品数量失败!");
			  		 }else{
			  			var i = $(obj).index()
			  			var d = $(obj).parent('ul').find('li.count')
			  			if(i == 0){
			  				if(parseInt(d.text()) > 1){
			  					d.text(parseInt(d.text()) - 1)
			  				}
			  			}else if(i == 2){
			  				d.text(parseInt(d.text()) + 1)
			  			}
			  			add_orderPrice($(obj).closest('div').attr('class'))
			  		 }
			  	  },
			  	  complete:function(){//ajax请求完成时绑定加减click事件
			  		  bindClickEvent();
			  	  } 
			  });
	 }
	
	
//	//初始总价格
//	add_orderPrice('take_box')
//	add_orderPrice('deliver_box')
//	add_package()
	//修改数量
	function bindClickEvent() {
	$('.take_box ul > li > ul, .deliver_box ul > li > ul').on('click','li',function(){
		
		var id = $(this).attr("id");
		var count =$(this).siblings(".count").text();
		var sign = $(this).html();
		var marktext = '-1';
		if(sign == "-"){
			marktext = '0';
		}
		if(sign == "+"){
			marktext = '1';
		}
		updateShopIdNum(id,count,marktext,this);
		
	})
}
	
	//删除商品
	function delShop(obj){
		var _list = $(obj).closest('div').attr('class');
		$(obj).closest('li').remove();
		add_orderPrice(_list)
		if($('.' + _list + ' >ul > li').length <= 0){
			$('.' + _list).remove()
		}
	}
});

/*function add_package(){
	var _package = $('.take_box').length + $('.deliver_box').length
	$('.p_box > .left > span').text(_package)
}*/

