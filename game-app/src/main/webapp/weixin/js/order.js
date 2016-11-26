$("#carteType").html("");
$("#carte").html("");
// 获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}
function oncli(num,carteType,carteNum){
	$("li.tabmenu_active").removeClass("tabmenu_active");
    $(".tab_content_active").removeClass("tab_content_active");
    $("#tab"+num).addClass("tab_content_active");
    $("#tabbt"+num).addClass("tabmenu_active");
    $("#carteNum").html(carteType+"最多可选"+carteNum+"个");
}
$.post(appName+"/carte/list.do", {
	"cafetoriumId" : getUrlParam("cafetoriumId"),"userId":getUrlParam("userId")
}, function(data) {
	var status = data.status;
	if (status != 200) {
		alert(data.message);
		return;
	}

	var carteTypeList = data.data;

	// 菜品类型
	var carteTypeStr = ""

	// 菜品
	var carteStr = "";

	var num=0;
	// 拼接页面内容
	for (var x = 0; x < carteTypeList.length; x++) {
		num=x;
		// 拼接菜单类型
		var carteType = carteTypeList[x];
		if (x == 0) {
			carteTypeStr += "<li id='tabbt" + x + "' class='tabmenu_active' onclick=oncli("+x+",'"+carteType.carteTypeName+"',"+carteType.upperLimit+")>"
					+ carteType.carteTypeName + "</li>";
			$("#carteNum").html(carteType.carteTypeName+"最多可选"+carteType.upperLimit+"个");
		} else {
			carteTypeStr += "<li id='tabbt" + x + "' class='' onclick=oncli("+x+",'"+carteType.carteTypeName+"',"+carteType.upperLimit+")>"
					+ carteType.carteTypeName + "</li>";
		}

		// 拼接菜单
		var carteList = carteType.carteList;
		if (x == 0) {
			carteStr += "<div id='tab" + x + "' class='tab_content tab_content_active'><ul>";
		}else{
			carteStr += "<div id='tab" + x + "' class='tab_content'><ul>";
		}
		for (var y = 0; y < carteList.length; y++) {
			var carte = carteList[y];
			carteStr +="<li>"+
			            "<span class='fname'>"+carte.carteName+"</span>"+
			            "<span class='fdaka'>1212</span>"+
			            "<span class='fcheck'>"+
			              "<label for='item1' class='check-link' carteTypeId='"+carteType.id+"' carteName='"+carte.carteName+"' length='"+carteType.upperLimit+"'></label>"+
			              "<input type='checkbox' class='checkbox' value='1' id='item1' name='' />"+
			            "</span>"+
			          "</li>";
		}
		carteStr +="</ul></div>";
	}
	$("#carteType").html(carteTypeStr);
	$("#carte").html(carteStr);
	
	                                                                                                                                             
	//进行菜品类型选择效果切换
//	for(var z=0;z<=num;z++){
//		$("#tabbt"+z).click(function(){
//		    $("li.tabmenu_active").removeClass("tabmenu_active");
//		    $(".tab_content_active").removeClass("tab_content_active");
//		    $("#tab"+z).addClass("tab_content_active");
//		    $(this).addClass("tabmenu_active");
//		  });
//	}
	
	//进行菜品选中数量判断
	$(".fcheck label").click(function(){
	    $(this).toggleClass("check-link check-active");
	    var carteTypeId=$(this).attr("carteTypeId");
	    if($(this).attr("class")=="check-active"){
	    	var onleng=$("label[carteTypeId='"+carteTypeId+"'][class='check-active']").length;
	    	var leng=$(this).attr("length");
	    	if(onleng>leng){
	    		alert("最多可以选"+leng+"个菜品");
	    		$(this).toggleClass("check-active check-link");
	    		return;
	    	}
	    }
	    
	  });
	  
	  $("#comment-clean span").click(function(){
	    var commentClean = $(this).index("span") + 1;
	    $("#comment-clean").data("clean",commentClean);
	    $(this).addClass("star-active");
	    $(this).prevAll().removeClass("star-link").addClass("star-active");
	    $(this).nextAll().removeClass("star-active").addClass("star-link");
	  });

	  $("#comment-service span").click(function(){
	    var commentClean = $(this).index("span") + 1;
	    $("#comment-service").data("service",commentClean);
	    $(this).addClass("star-active");
	    $(this).prevAll().removeClass("star-link").addClass("star-active");
	    $(this).nextAll().removeClass("star-active").addClass("star-link");
	  });

	  $("#comment-price span").click(function(){
	    var commentClean = $(this).index("span") + 1;
	    $("#comment-price").data("price",commentClean);
	    $(this).addClass("star-active");
	    $(this).prevAll().removeClass("star-link").addClass("star-active");
	    $(this).nextAll().removeClass("star-active").addClass("star-link");
	  });


	  $(".single-tag").click(function(){
	    var commentTag = $(this).text();
		var va = $("textarea").val();
	    $("textarea").val(va + commentTag);		
	  });
});
$("#submit-order").click(function(){
	var suMessage="[";
	$("label[class='check-active']").each(function(){
		  var carteTypeId=$(this).attr("carteTypeId");
		  var dishName=$(this).attr("carteName");
		  var leng=$(this).attr("length");
		  suMessage+="{'carteTypeId':'"+carteTypeId+"','dishName':'"+dishName+"'},"
	  });
	suMessage+="]";
	  $.post(appName+"/carte/sucarte.do",{"cafetoriumId" : getUrlParam("cafetoriumId"),"suMessage":suMessage,"userId":getUrlParam("userId")},function(data){
		  alert(data.message);
	  });
});