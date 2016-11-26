function save()
{
	//防止重复提交
	var submitFlag = $("#submitFlag").val();
	if("true" == submitFlag)
	{
		alert("生日已设置，请勿重复提交");
		return;
	}
	
	//微信标识
	var openId = getUrlParameter("openId");
	if(isEmpty(openId))
	{
		alert("请使用微信登录");
		return;
	}
	
	//姓名
	var name = $('#name').val();
	if(isEmpty(name))
	{
		alert("姓名不能为空");
		return;
	}
	else if(!isLengthBetweenAndEqual(name,1, 16))
	{
		alert("姓名的长度必须在0~16之间");
		return;
	}
	
	
	//手机号码
	var phone = $('#cellphone').val();
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
	
	//生日
	var birthday = $('#dates').val();
	var currentDate = new Date();
	var now = currentDate.getFullYear()+'-'+(currentDate.getMonth()+1)+'-'+currentDate.getDate()
	if(isEmpty(birthday))
	{
		alert("生日不能为空");
		return;
	}
	else if(dateCompareTo(birthday, now) > 0)
	{
		alert("请输入合法的日期");
		return;
	}
	
	var cafetoriumId = getUrlParameter("cafetoriumId");
	if(!cafetoriumId)
	{
		alert("食堂id不能为空");
		return;
	}
	
	$.ajax({
		url:'/http/api/carte/getUUID.do',
		type:'POST',
		dataType:'json',
		data:{
//			'openId':getUrlParameter("openId"),
//			'cafetoriumId':getUrlParameter("cafetoriumId")
		},
		success:function(data)
		{
			if(200 == data.status)
			{
				$.ajax({
					url:'/http/api/carte/shopUser/recordBirthday.do',
					type:'POST',
					data:{
						'openId':openId,
						'name':name,
						'phone':phone,
						'birthday':birthday,
						'id':data.data,
						'cafetoriumId':cafetoriumId
					},
					dataType:'json',
					success:function(data2){
						if(200==data2.status)
						{
							$("#submitFlag").val("true");
							location.href = 'birthday_welcome.html?openId='+openId+"&cafetoriumId="+getUrlParameter("cafetoriumId");
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