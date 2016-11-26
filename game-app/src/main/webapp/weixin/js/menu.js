$(function(){
    $(".count").hide();
	$("#carte").html("");
	$("#count").html("0");
	$("#carteNum").html("");
	
	$(document).ajaxStop(function(){
		$(".tabmenu li:first-child").trigger("click");
	});
});



// 获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}
function oncli(num,carteType,carteNum){
    $("#tab"+num).children("img.active").show();
    $("#tab"+num).children("img.blank").hide();
    $("#tab"+num).siblings().children("img.blank").show()
    $("#tab"+num).siblings().children("img.active").hide();
    $("#tab"+num).siblings().removeClass("tabmenu_active");//tab类切换
    $("#tab"+num).addClass("tabmenu_active");//tab类切换
    $("#tab"+num+"Cont").siblings().removeClass("tab_content_active");//tab内容切换
    $("#tab"+num+"Cont").addClass("tab_content_active");//tab内容切换
    
    
    $("#carteNum").html(carteType+"最多可选"+carteNum+"个");
}


//function openLottery(){
//	location.href = "prize.html?openId="+getUrlParam("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
//}



$.post("/carte/list.do", {
	"cafetoriumId" : getUrlParam("cafetoriumId"),"userId":getUrlParam("openId")
}, function(data) {
	var status = data.status;
	if (status != 200) {
		alert(data.message);
		$("#carteNum").html(data.message);
		return;
	}

	var carteTypeList = data.data;

	// 菜品类型
	var carteTypeStr = "<ul class='tabmenu'>"

	// 菜品
	var carteStr = "";

	var num=0;
	// 拼接页面内容
	for (var x = 0; x < carteTypeList.length; x++) {
		num=x;
		// 拼接菜单类型
		var carteType = carteTypeList[x];
		var icon=carteType.icon;   //菜单类型图片
		var icon2=carteType.icon2;   //菜单类型背景图片
		if (x == 0) {
			carteTypeStr += "<li id='tab"+x+"' class='tabmenu_active' onclick=oncli("+x+",'"+carteType.carteTypeName+"',"+carteType.upperLimit+")>"+
								"<span class='type_count'></span>"+
					            "<img class='blank'  src='"+urlPre+icon+"' alt='' />"+
					            "<img class='active' src='"+urlPre+icon2+"' alt='' />"+
					            carteType.carteTypeName+
					        "</li>";
			$("#carteNum").html(carteType.carteTypeName+"最多可选"+carteType.upperLimit+"个");
		} else {
			carteTypeStr += "<li id='tab"+x+"' onclick=oncli("+x+",'"+carteType.carteTypeName+"',"+carteType.upperLimit+")>"+
			"<span class='type_count'>34</span>"+
					            "<img class='blank'  src='"+urlPre+icon+"' alt='' />"+
					            "<img class='active' src='"+urlPre+icon2+"' alt='' />"+
					            carteType.carteTypeName+
					        "</li>";
		}

		// 拼接菜单
		var carteList = carteType.carteList;
		if (x == 0) {
			carteStr += "<div id='tab"+x+"Cont' class='tab_content tab_content_active'><ul>";
		}else{
			carteStr += "<div id='tab"+x+"Cont' class='tab_content'><ul>";
		}
		for (var y = 0; y < carteList.length; y++) {
			var carte = carteList[y];
 			var sustenance=carte.sustenance;
			var calorie=carte.calorie;
			var sustenanceCon="";
			var calorieCon="";
			var vote=carte.vote;//菜品投票数量
			if(!vote){
				vote=0;
			}
			var carteDescribe=carte.carteDescribe;//菜品描述
			if(!carteDescribe){
				carteDescribe="";
			}
			var grams=carte.grams;//菜品计量单位：克
			if(!grams){
				grams=0;
			}
			var count=carte.count;//数量
			if(!count){
				count=0;
			}
			var img=carte.img;//菜品图片
			var isNew=carte.isNew;//是否为新品
			if(isNew){
				isNew="<img src='images/new_tag.png' style='position:absolute;'/>";
			}else{
				isNew="<img src='' class='new' style='position:absolute;'/>";
			}
			for(var z=0;z<5;z++){
				if(z<sustenance){
					sustenanceCon+="<li class='single-star star-active'></li>";
				}else{
					sustenanceCon+="<li class='single-star star-link'></li>";
				}
				
				if(z<calorie){
					calorieCon+="<li class='single-star star-active'></li>";
				}else{
					calorieCon+="<li class='single-star star-link'></li>";
				}
			}
			
			
			carteStr +="<li>"+
						isNew+
			            "<div class='row-wrap clearfix'>"+			            
			            "<div class='thumbs'>"+
			              "<img src='"+urlPre+img+"'/>"+
			            "</div>"+
			            "<div class='dish_info'>"+
			              "<div class='row_title cliearfix'>"+
			              carte.carteName+
			                "<span class='sub_info'>已有<span class='person_count'>"+vote+"</span>人选</span>"+
			              "</div>"+
			              "<div class='detail'>"+
			                "<span>"+carteDescribe+"</span> <span>"+grams+"g x"+count+"</span>"+
			              "</div>"+
			              "<div class='icon_wrap clearfix'>"+
			                "<ul class='star-wrap'>  营养"+
			                	sustenanceCon+
			                "</ul></br>"+
			                "<ul class='star-wrap'>  热量"+
			                	calorieCon+
			                "</ul>"+
			                "<span class='dish_check' carteTypeId='"+carteType.id+"' carteName='"+carte.carteName+"' length='"+carteType.upperLimit+"'>"+
			                  "<span class='check-link'></span>"+
			                "</span>"+
			              "</div>"+
			            "</div>"+
			          "</div>"+
			        "</li>";
		}
		carteStr +="</ul></div>";
	}
	carteTypeStr+="</ul>";
	$("#carte").html(carteTypeStr);
	$("#carte").append(carteStr);
	
	
	// 下周菜单__对号点击
	  $(".dish_check").click(function(){
		  	var carteTypeId=$(this).attr("carteTypeId");
		    var onleng=$("span[carteTypeId='"+carteTypeId+"'] span[class='check-active']").length;
	    	var leng=$(this).attr("length");
	    	if(onleng>=leng && !$(this).find("span:first-child").hasClass('check-active')){
	    		alert("本类最多可以选"+leng+"个菜品");
	    		return;
	    	}
	    	
	    	
	    	
	    $(this).children("span").toggleClass("check-link check-active");
	    $(".count").text($(".check-active").length);
	    $(".bottom .left span").text($(".check-active").size());
	    
	    var tab_num = $(this).closest("[id ^= 'tab']").attr("id").substring(3,4);
	    $("#tab" + tab_num + " .type_count").text($(this).closest("[id ^= 'tab']").find(".check-active").size());
	    if(!!$(this).closest("[id ^= 'tab']").find(".check-active").size()){
	    	$("#tab" + tab_num + " .type_count").css("display","inline-block").fadeIn(300);
	    } else {
	    	$("#tab" + tab_num + " .type_count").fadeOut(300);
	    }
	  });
	
	
	
});
$("#submit-order").click(function(){
	if($("span[class='dish_check'] span[class='check-active']").length==0){
		alert("未选择菜品");
		return;
	}
	var suMessage="[";
	$("span[class='dish_check'] span[class='check-active']").each(function(){
		  var node=$(this).parent()
		  var carteTypeId=node.attr("carteTypeId");
		  var dishName=node.attr("carteName");
		  var leng=node.attr("length");
		  suMessage+="{'carteTypeId':'"+carteTypeId+"','dishName':'"+dishName+"'},"
	  });
	suMessage+="]";
	  $.post(appName+"/carte/sucarte.do",{"cafetoriumId" : getUrlParam("cafetoriumId"),"suMessage":suMessage,"userId":getUrlParam("openId")},function(data){
		  if(200 == data.status)
		  {
			  alert(data.message);
			  location.href = "thanks.html?openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
			  return;			  
		  }
		  
		  alert(data.message);
	  });
	  
});