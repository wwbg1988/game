$(function(){
	var cafetoriumId = getUrlParameter("cafetoriumId");
	if(isEmpty(cafetoriumId))
	{
		alert("参数不能为空（食堂id）");
		return;
	}
	
	 jQuery.ajax({
			url:'/http/api/carte/visitService/getDinnerSeries.do',
			data:{'cafetoriumId':cafetoriumId},
			type:'POST',
			dataType:'json',
			success:function(data){
				//alert(JSON.stringify(data));
				//alert(data.status);				
				if(200==data.status)
				{
					jQuery.each(data.data,function(index,item){
						jQuery("#dinnerSeries").html(jQuery("#dinnerSeries").html()+
								"<ul class='menu-ul'>" +
									"<li class='dish unselected' id="+item.id+">" +
										"<img class='img-big' src='"+urlPre+item.defaultImage+"' />" +
										"<div class='dish-right dish-unselected'>" +
											"<h1>"+item.name+"</h1>" +
											"<div class='dish-info'>" +
												"<span class='dish-price'><b id='price'>￥"+item.prince+"</b>起</span>" +
												"<span class='dish-person'><i>"+item.minPerson+"</i>~<i>"+item.maxPerson+"</i></span>" +
											"</div>" +
											"<div class='img-detail-d'>" +
												"<img class='img-detail' src='"+urlPre+item.image1+"' />" +
												"<img class='img-detail' src='"+urlPre+item.image2+"' />" +
												"<img class='img-detail' src='"+urlPre+item.image3+"' />" +
											"</div>" +
										"</div>" +
									"</li>" +
								"</ul>");
					});
					
					//$("#dinnerSeriesId").val(item);
				}
				else
				{
					alert(data.message);
				}
		  },
		  error:function(jqXHR, textStatus, errorThrown)
		  {
			  alert("网络出错");
		  }
	 });
	 
})

function next()
{
	var openId = getUrlParameter("openId");
	if(isEmpty(openId))
	{
		alert("请使用微信登录");
		return;
	}
	
	var dinnerSeriesId = jQuery(".selected");
	if(!isEmpty(dinnerSeriesId.attr("id")))
	{
		location.href = 'chef-fill-info.html?openId='+openId+"&cafetoriumId="+getUrlParameter("cafetoriumId")+"&dinnerSeriesId="+dinnerSeriesId.attr("id");
		return;
	}
	else
	{
		alert("未选中套餐");
	}
	
}