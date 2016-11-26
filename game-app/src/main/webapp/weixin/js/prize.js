function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}

function openWinPhoneFee(){
	location.href = "winning.html?openId="+getUrlParam("openId")+"&cafetoriumId="+getUrlParam("cafetoriumId");
}

function openLoseHtml(){
	location.href = "lose.html?openId="+getUrlParam("openId")+"&cafetoriumId="+getUrlParam("cafetoriumId");
}


$(function(){
	$(document).ajaxStop(function(){
		var arrow_hight = $(".circle_body").height();
		  var arrow_hight_self = $(".circle_bg_arrow").height();
		  var arrow_top_part = (arrow_hight_self/1000)*275;
		  // alert(arrow_hight);
		  $(".circle_bg_arrow").css("top",((arrow_hight/2) - (((arrow_hight_self - arrow_top_part)/2) + arrow_top_part) ) + "px");
		
	});
	  
	  
	  $.post(appName+"/luckDraw/getPonitsAngles.do", {
		"openId":getUrlParam("openId"),
		"cafetoriumId":getUrlParam("cafetoriumId")
	}, function(data) {
		var status = data.status;
		if (status != 200) {
			alert(data.message);
			return;
		}

	


	$.post(appName+"/luckDraw/initTotalLuckyCount.do", {
		"openId" : getUrlParam("openId"),
		"cafetoriumId":getUrlParam("cafetoriumId")
	}, function(data) {
		var status = data.status;
		if (status != 200) {
			alert(data.message);
			return;
		}

		if(data.data.totalLuckyCount==null){
	     $("#left_time").text("当前剩余抽奖机会0次");
		}else{
	     //更新剩余抽奖次数提示文字：当前剩余抽奖机会：N次
		 $("#left_time").text("当前剩余抽奖机会"+ data.data.totalLuckyCount+"次");
		 }
	});

  
  //转盘指针
  var randomPoint = data.data.points;
  //总评论条数
  var totalCommentCount = data.data.totalCommentCount;
  //总抽奖次数
  var totalLuckyCount = data.data.totalLuckyCount;
  //当天是否抽过奖
  var isNowLuckyDraw = data.data.isNowLuckyDraw;

  var angle = 0;
  $(".circle_bg_arrow").click(function(){
	  if(isLoginWeiXin()==false){
		 return ;
	  }
	
	  //总的评论次数>=总的抽奖次数,且总的抽奖次数不为0即可抽奖;
    if (totalCommentCount >= totalLuckyCount  && totalLuckyCount!=0){
    	isNowLuckyDraw++;
    	totalLuckyCount--;
      $(".circle_sm_dot").addClass("dot_run");
      $(".circle_bg_dot").addClass("dot_run2");

      // var num_box = [30,50,69];
      // var radom_num = num_box[Math.floor(Math.random()*3)];

      var random_num = randomPoint;

      angle = 3600;//旋转角度
      var rotate = document.getElementById("rotate")
      rotate.innerHTML = "@-webkit-keyframes myfirst{ from {-webkit-transform:rotate(0deg)} to {-webkit-transform:rotate("+(angle+random_num)+"deg)}} ";

      $(".circle_body").css({
        "-webkit-animation":"myfirst 5s",
      });

      // rotate.innerHTML = "@-webkit-keyframes myfirst{ from {-webkit-transform:rotate(0deg)} to {-webkit-transform:rotate("+(angle + 60)+"deg)}} ";
      //
      $(".circle_body").css({
        "-webkit-transform":"rotate("+ (angle + random_num) +"deg)"
      });

      var result = (angle + random_num);

      // 转盘旋转后处发动作
      document.querySelector(".circle_body").addEventListener("webkitAnimationEnd",function(){
        $(".circle_sm_dot").removeClass("dot_run");
        $(".circle_bg_dot").removeClass("dot_run2");

        if (result ==3900){
          alert("恭喜您中奖了");
          //更新中奖记录到转盘抽奖历史表中(t_ctr_lucky_draw_history);is_ lottery更新为1,更新中奖时间
          $.post(appName+"/luckDraw/updateLottery.do", {
    			"openId" : getUrlParam("openId"),
    			"cafetoriumId" : getUrlParam("cafetoriumId"),
    			"drawId" : "1"
    		},function(data) {
    			if(data.status==500){
    			  alert("转盘抽奖异常");
    				}
    			}); 
          openWinPhoneFee();
        } else {
           //更新用户积分+1
           $.post(appName+"/luckDraw/updateOrSaveUserIntegral.do", {
  			"openId" : getUrlParam("openId"),// "A94zREuHffcl9H2eM_DSA"
  			'cafetoriumId':getUrlParam("cafetoriumId")
  		}); 
           //更新剩余抽奖次数
   		   $.post(appName+"/luckDraw/updateRestCount.do", {
   			"openId" :getUrlParam("openId"),
   			'cafetoriumId' : getUrlParam("cafetoriumId")
   		});
            openLoseHtml();
          
    	
          alert("没有中奖");
        }
     
	
      //更新剩余抽奖次数提示文字：当前剩余抽奖机会：N次
		 $("#left_time").text("当前剩余抽奖机会"+totalLuckyCount+"次");
      },false);

    } else {
    	 $("#left_time").text("当前剩余抽奖机会0次");
      alert("您的抽奖次数为0或者当天已经抽过奖，无法再次抽奖");
    }

  });
	});

});
