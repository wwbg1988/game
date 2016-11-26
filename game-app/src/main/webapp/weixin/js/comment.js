$(function(){
	 jQuery.ajax({
			url:'/http/api/catering/search.do?cafetoriumId='+getUrlParameter("cafetoriumId")+'&userId='+getUrlParameter("openId"),
			type:'POST',
			dataType:'json',
			success:function(data){
			var myObject = data;
				if(myObject.data.flag=='0'){
					$("#uuid").val(myObject.data.uuid);
					for (var i = 0; i < myObject.data.resultConfig.length; i++) {
						var div = $('<div class="row-wrap" id="row-wrap"/>');
						var u = $('<div id="wrap'+myObject.data.resultConfig[i].id+'" class="wrap"/>');
//						var p = $('<span class="comment-name comment_icon_'+myObject.data.resultConfig[i].id+'" id="key'+myObject.data.resultConfig[i].id+'">' + myObject.data.resultConfig[i].configName + '</span>');
						var p = $('<span class="comment-name" id="key'+myObject.data.resultConfig[i].id+'" style="background: url(..'+myObject.data.resultConfig[i].imageUrl+') no-repeat 20% 0%; background-size: 1.5em 1.5em;">' + myObject.data.resultConfig[i].configName + '</span>');
						var h = $('<div class="star-wrap">'
								+'<span class="single-star star-link"></span>'
								+'<span class="single-star star-link"></span>'
								+'<span class="single-star star-link"></span>'
								+'<span class="single-star star-link"></span>'
								+'<span class="single-star star-link"></span>'
								+'</div>');
						u.append(p,h);
						div.append(u);
						$('#row-wrap').last().after(div);
					}
					if(myObject.data.resultSensitive != null){
						for (var j = 0; j < myObject.data.resultSensitive.length; j++) {
							var s = $('<div class="single-tag tag-link" id="'+myObject.data.resultSensitive[j].id+'">'+myObject.data.resultSensitive[j].sensitiveName+'</div>');
							$("#tag-wrap").append(s);
						}
					}
					bindClicks();
				}else{
					
					//已经评论过了
					if("1"==myObject.data.pageConfig.isEnabled){//启用抽奖配置
						if(myObject.data.flag == "1"){
			  		    	location.href = "winning.html?openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
			  		    }

			  		    if(myObject.data.flag == "2"){
			  		    	location.href = "prize.html?openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
			  		    }

				  		if(myObject.data.flag == "3"){
				  			location.href = "lose.html?openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
			  		    }

				  		if(myObject.data.flag == "4"){
				  			location.href = "comment_thank.html?openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
			  		    }

				  		if(myObject.data.flag == "-1"){
			  		    	alert("点坏了!");
			  		    }				  		
				  	
					}
					else{
						location.href=myObject.data.pageConfig.notEnabledUrl+"?openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
					}

				}
		  }
	 });

	function bindClicks() {
		//点评__星星点击
		  $(".single-star").click(function(){
			  	var key =  $(this).parent().siblings('.comment-name').attr('id');
			    var commentClean = $(this).index("span.single-star") % 5 + 1;
			    var data = {};
			    data[key] = commentClean;
			    $(this).parent().siblings('.comment-name').data(data);
			    $(this).addClass("star-active");
			    $(this).prevAll().removeClass("star-link").addClass("star-active");
			    $(this).nextAll().removeClass("star-active").addClass("star-link");
				$(".row-wrap:last").next("p").fadeOut().empty();
		  });

		  //点评__标签点击
		  $(".single-tag").click(function(){
		      var commentTag = $(this).text();
		      var va = $("textarea").val();
		      $("textarea").val(va + commentTag);

		      $("#word_count").html($("textarea").val().length);
		      if($("textarea").val().length > 140){
		        $("#word_count").css("color","#f00") ;
		      } else {
		        $("#word_count").css("color","#000") ;
		      };
		  });

		  //点评__点击验证
		  $(".row-wrap:last").after("<p class='notice-info'></p>");
		  $("#submit-comment").click(function(){
				  var retresult=true;
			      $(".row-wrap").each(function(){
			    	  function isEmptyObject(obj){
			    		    for(var n in obj){return false}
			    		    return true;
			    		}

			    	  	var is_ept = $(this).find(".comment-name").data();
			        	if( isEmptyObject(is_ept) ){
				          var tempname = $(this).find(".comment-name").text();
				          $(".row-wrap:last").next("p").fadeIn().text( tempname + "未填写");
				           retresult=false;
				          return retresult;
				        }
			      });
			      if($("textarea").val().length > 140){
					  return false;
				  } else {
				      if(retresult){
				    	  var obj = {};
				    	  $('.comment-name').each(function(i){$.extend(obj, $(this).data());});
						  var score = JSON.stringify(obj);
						  var textarev = $("#comments").val();
						  var uuid = $("#uuid").val();
						  var datas ={
						  		  cafetoriumId:location.search.substr(location.search.indexOf('=')+1,location.search.indexOf('&')-14),
						  		  userId:location.search.substr(location.search.indexOf('&')+8),
						  		  uuid:uuid,
						  		  configScore: [score],
						  		  comments: [{textareavalue:textarev}]
						  }
						  $.ajax({
						  	  url:"/http/api/catering/submit.do",
						  	  type:'POST',
						  	  dataType:'json',
						  	  data:{data:JSON.stringify(datas)},
						  	  success:function(data){
//						  		  if(JSON.stringify(data)=="1"){
//						  			location.href = appName+"/weixin/prize.html?openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
//						  		  }
						  	  },
						  	  complete:function(data){ //ajax请求完成时跳转页面
//						  		    location.href = "prize.html?openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
						  		  
						  		  
						  		 jQuery.ajax({
						 			url:'/http/api/catering/search.do?cafetoriumId='+getUrlParameter("cafetoriumId")+'&userId='+getUrlParameter("openId"),
						 			type:'POST',
						 			dataType:'json',
						 			success:function(data2){
						 				if("1"==data2.data.pageConfig.isEnabled){
									  		  location.href = data2.data.pageConfig.enabledUrl+"?openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
						 				}
						 				else{
									  		  location.href = data2.data.pageConfig.notEnabledUrl+"?openId="+getUrlParameter("openId")+"&cafetoriumId="+getUrlParameter("cafetoriumId");
						 				}
						 					
						 				
						 			}
						  		 });
						  	  }
						  });
					  }
			     }

		  });
		}

	  $("textarea").bind("keyup change",function(){
	    $("#word_count").html($("textarea").val().length);
	    if($(this).val().length > 140){
	      $("#word_count").css("color","#f00") ;
	    } else {
	      $("#word_count").css("color","#000") ;
	    };
	  });

});
