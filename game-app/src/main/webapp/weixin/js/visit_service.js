function visitFormSubmit()
{
	//防止重复提交
	var submitFlag = $("#submitFlag").val();
	if("true" == submitFlag)
	{
		alert("请不要重复提交");
		return;
	}
	
	//微信标识符
	var openId = getUrlParameter("openId");
	if(isEmpty(openId))
	{
		alert("请使用微信登录");
		return;
	}
	
	//姓名
	var name = $("#name").val();
	if(isEmpty(name))
	{
		alert("姓名不能为空");
		return;
	}
	else if(!isLengthBetweenAndEqual(name,1, 16))
	{
		alert("姓名的长度必须在1~16之间");
		return;
	}
	
	//手机号码
	var phone = $("#cellphone").val();
	if(isEmpty(phone))
	{
		alert("手机号码不能为空");
		return;
	}
	else if(!isPhoneValid(phone))
	{
		alert("手机号码不合法");
		return;
	}
	
	//地址
	var address = $("#address").val();
	if(isEmpty(address))
	{
		alert("地址不能为空");
		return;
	}
	else if(!isLengthBetweenAndEqual(address,1, 20))
	{
		alert("地址的长度必须在1~20之间");
		return;
	}
	
	
	//时间
	var serviceTime = $("#dates").val();
	var currentDate = new Date();
	var now = currentDate.getFullYear()+'-'+(currentDate.getMonth()+1)+'-'+currentDate.getDate()
	if(isEmpty(serviceTime))
	{
		alert("大厨上门日期不能为空");
		return;
	}
	else if(dateCompareTo(serviceTime, now) < 0)
	{
		alert("大厨上门日期不能早于当前日期");
		return;
	}
	
	//留言
	var message = $("#message").val();
	if(!isEmpty(message)&&!isLengthBetweenAndEqual(message,0, 40))
	{
		alert("留言的长度必须在0~40之间");
		return;
	}
	
	
	//套餐id
	var dinnerSeriesId = getUrlParameter("dinnerSeriesId");
	
	jQuery.ajax({
		url:'/http/api/carte/getUUID.do',
		type:'POST',
		dataType:'json',
		data:{
			//'openId':getUrlParameter("openId")
		},
		success:function(data)
		{
			if(200 == data.status)
			{
				$.ajax({
					url:'/http/api/carte/visitService/saveVisit.do',
					type:'POST',
					data:{
						'name':name,
						'phone':phone,
						'address':address,
						'serviceTime':serviceTime,
						'message':message,
						'dinnerSeriesId':dinnerSeriesId,
						'openId':openId,
						'id':data.data,
						'cafetoriumId':getUrlParameter("cafetoriumId")
					},
					dataType:'json',
					success:function(data2)
					{
						if(200==data2.status)
						{
							$("#submitFlag").val("true");
							window.location.href = '/weixin/chefConfirm.html?openId='+openId+"&cafetoriumId="+getUrlParameter("cafetoriumId");
							return;
						}
						else
						{
							alert(data2.message);
						}
					}
				});
			}
			else
			{
				alert(data.message);
			}
		}
	});
	
	
				
}
