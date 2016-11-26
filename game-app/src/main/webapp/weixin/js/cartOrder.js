

$(function(){
	$(".submit-bt").click(function(){
		
		if($("span[name='bagnum']").length <= 0)
		{
			alert("购物车是空的"); 
			return false;
		}
        if($('#name_label').length) {		
			//包裹一：
			var name = $("#name").val();
			var phone = $("#phone").val();
			var isPhone = isPhoneValid(phone);
			
			if(isEmpty(name)){
				alert("姓名不能为空");
				return false;
			}else if(name.length>=15){
				alert("姓名长度超限");
				return false;
			}else if(isEmpty(phone)){
				alert("手机号码不能为空");
				return false;
			}else if(!isPhone){
	            alert('请输入有效的手机号码！');
	            return false;
	        }
		}
		
		
		if($('#name_label2').length) {  
			//包裹二
			var names = $("#name2").val();
			var phones = $("#phone2").val();
			var myreg = isPhoneValid(phones);
			var floor = $("#floor2").val();
			var department = $("#department2").val();
			if(isEmpty(names)){
				alert("姓名不能为空");
				return false;
			}else if(names.length>=15){
				alert("姓名长度超限");
				return false;
			}else if(isEmpty(phones)){
				alert("手机号码不能为空");
				return false;
			}else if(!myreg){
	            alert('请输入有效的手机号码！');
	            return false;
	        }else if(isEmpty(floor)){
				alert("楼层不能为空");
				return false;
			}else if(floor.length>=11){
				alert("输入长度超限");
				return false;
			}else if(isEmpty(department)){
				alert("部门不能为空");
				return false;
			}else if(department.length>=50){
				alert("输入长度超限");
				return false;
			}
		}
		
		//微信标识
		var openId = getUrlParameter("openId");
		if(isEmpty(openId)){
			alert("请使用微信登录");
			return;
		}
		var cafetoriumId = getUrlParameter("cafetoriumId");
		if(isEmpty(cafetoriumId)){
			alert("食堂id为空");
			return;
		}
		
		jQuery.ajax({
			url:appName+'/submitOrder/submitOrder.do',
			type:'POST',
			data:{
				'openId':openId,
	            'name':name,
	            'phone':phone,
	            'names':names,
	            'phones':phones,
	            'floor':floor,
	            'department':department,
	            'cafetoriumId':cafetoriumId
			},
			dataType:'json',
			success:function(datas){
				if(200==datas.status){
					$("#submitFlag").val("true");
					var orderNo =datas.data.orderNo;
					var goodsAmount=datas.data.goodsAmount;
					var orderId=datas.data.id;
					location.href = 'order_pay.html?orderNo='+orderNo+"&orderId="+orderId+'&openId='+openId+"&cafetoriumId="+getUrlParameter("cafetoriumId")+'&goodsAmount='+goodsAmount;
					return;
				}else{
					alert("保存失败");
				}
			}
		});
		
	});
});




