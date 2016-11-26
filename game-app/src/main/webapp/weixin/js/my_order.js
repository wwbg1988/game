$(function(){
	//动态生成订单商品信息列表
	jQuery.ajax({
		url:appName+'/ourOrder/ourOrder.do?openId='+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId"),
		type:'POST',
		dataType:'json',
//		async:false,
		success:function(data){
			var myObject = data;
			var p = '';
			if(200 == myObject.status){
				if(!!myObject.data&&myObject.data.length > 0){
					for (var i = 0; i < myObject.data.length; i++) {			
					  p = '<ul><li class="parcel clearfix"><h4><div class="wrap">'+myObject.data[i].createTime+
						'<span class="active">';
					    var sta = myObject.data[i].orderStatus;
					    var payWay = myObject.data[i].payWay;
					    if(payWay==2) p += "货到付款";
					    if(payWay==1||payWay==null){ 
					    	if(sta==1) p += "未支付"; 
					    	if(sta==2) p += "已支付";
					    	if(sta==3) p += "已完成";
					    }
					   
					    p += '</span></div></h4>'
					    +'<div class="pic_wrap" orderid="'+myObject.data[i].orderId+'" onclick="toOrderDetail(this)"><div class="wrap">';
					    				   
					    
					    //包裹一
					    var len = Math.min(myObject.data[i].orderDetGoods.orderDetailOneList.length, 4);
						for (var j = 0 ; j < len ; j++) {
							if (!!(myObject.data[i].orderDetGoods.orderDetailOneList[j].icon)) {
							 p += '<img src="'+myObject.data[i].orderDetGoods.orderDetailOneList[j].icon+'"/>'
							}
						}	
						
						//包裹二
						var len2 = 4-len;
						var bag2 =myObject.data[i].orderDetGoods.orderDetailTwoList;
						if(!!bag2){
							if(bag2.length <= len2){
								len2 = bag2.length;
							}
						}
						
						if(len2 > 0){
							for (var j2 = 0 ; j2 < len2 ; j2++) {
								if (!!(myObject.data[i].orderDetGoods.orderDetailTwoList[j2].icon)) {
								 p += '<img src="'+myObject.data[i].orderDetGoods.orderDetailTwoList[j2].icon+'"/>'
								}
							}	
						}
						
						//跳转参数
						var orderStr = JSON.stringify({"orderId":myObject.data[i].orderId,"orderNo":myObject.data[i].orderNo,"goodsAmount":myObject.data[i].goodsAmount});
						p += '</div></div><div class="total_wrap"><div class="wrap">共'+myObject.data[i].accountGoods+
						'件商品 合计：<span class="total color">￥'+parseFloat(myObject.data[i].goodsAmount).toFixed(2)+
						'</span></div></div>';
						if(payWay==1||payWay==null){
							if(sta!=2){
								p+='<span class="pay_bt" orderid="'+myObject.data[i].orderId+'" onclick="toDelete(this)" >删除</span>';
								p+='<span class="pay_bt" onclick=\'toPay('+orderStr+')\'>付款</span></li></ul>';
							}
						}
						//if(Date.parse(myObject.data[i].createTime)-Date.parse(new Date().toLocaleString())<=4){
						//	p += '<span class="pay_bt">付款</span></li>';
						//};
						$("#thelist").append(p);
						// alert("orderId:--"+myObject.data[i].orderId+"----");
					}
				}
			}
			else{
				alert(myObject.message);
			}
			
			
		}
 });
});    

//单击订单,调到订单详情
function toOrderDetail(obj){   
        window.location.href= appName+"/weixin/my_order_detail.html?orderId="+obj.getAttribute('orderid')+"&openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");       
 }

//删除订单
function toDelete(obj){
	if(confirm('确定要删除记录吗?')){
		jQuery.ajax({
			url:appName+'/ourOrder/deleteOrder.do?orderId='+obj.getAttribute('orderid'),
			type:'POST',
			dataType:'json',
			success:function(datas){
				if(200==datas.status){
					window.location.href= appName+"/weixin/my_order.html?openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
					return;
				}else{
					alert("删除失败");
				}
			}
		});
		return true;
		}
	return false;
}


//var myScroll,
//	pullDownEl, pullDownOffset,
//	pullUpEl, pullUpOffset,
//	generatedCount = 0;
//
//function pullUpAction () {
//  setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
//  
////    var el, li, i;
////    el = document.getElementById('thelist');
////
////    for (i=0; i<2; i++) {
////      li = document.createElement('li');
////      li.innerText = 'Generated row ' + (++generatedCount);
////      el.appendChild(li, el.childNodes[0]);
////    }
//    myScroll.refresh();		// 数据加载完成后，调用界面更新方法 Remember to refresh when contents are loaded (ie: on ajax completion)
//  }, 1000);	// <-- Simulate network congestion, remove setTimeout from production!
//}
//
///**
// * 初始化iScroll控件
// */
//function loaded() {
//  pullDownEl = document.getElementById('pullDown');
//  pullDownOffset = pullDownEl.offsetHeight;
//  pullUpEl = document.getElementById('pullUp');
//  pullUpOffset = pullUpEl.offsetHeight;
//
//  myScroll = new iScroll('scroll_wrapper', {
//    scrollbarClass: 'myScrollbar', /* 重要样式 */
//    useTransition: false, /* 此属性不知用意，本人从true改为false */
//    topOffset: pullDownOffset,
//    onRefresh: function () {
//      if (pullUpEl.className.match('loading')) {
//        pullUpEl.className = '';
//        pullUpEl.querySelector('.pullUpLabel').innerHTML = '下拉加载更多...';
//      }
//    },
//    onScrollMove: function () {
//      if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
//        pullUpEl.className = 'flip';
//        pullUpEl.querySelector('.pullUpLabel').innerHTML = '松手开始更新...';
//        this.maxScrollY = this.maxScrollY;
//      } else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
//        pullUpEl.className = '';
//        pullUpEl.querySelector('.pullUpLabel').innerHTML = '下拉加载更多...';
//        this.maxScrollY = pullUpOffset;
//      }
//    },
//    onScrollEnd: function () {
//      if (pullUpEl.className.match('flip')) {
//        pullUpEl.className = 'loading';
//        pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
//        pullUpAction();	// Execute custom function (ajax call?)
//      }
//    }
//  });
//
//  setTimeout(function () { document.getElementById('scroll_wrapper').style.left = '0'; }, 800);
//}
//
////初始化绑定iScroll控件
//document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
//document.addEventListener('DOMContentLoaded', loaded, false);
