//打电话
function callPhone(phone){
	var u = navigator.userAgent;
	if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {//安卓手机
		//call.phone(phone+"");
		window.location.href = "tel:"+phone;
	} else if (u.indexOf('iPhone') > -1) {//苹果手机
		window.location.href = "tel:"+phone;
	}
}
$(function(){
//
//	$("#content").height(300);
//	$("#content").width(900);
//$('.leader_info_wrap .head_icon .circle').css({
//	"height":"5em"
//});
//$('.leader_info_wrap .head_icon .circle img').css({
//	"position":"relative",
//	"top":"0.3em"
//});

  var data = {
      "abc":[
                {"name":"狮子头","value":"234"},
                {"name":"大排","value":"342"},
                {"name":"炸猪排","value":"164"},
                {"name":"粮醋排骨","value":"556"},
                {"name":"鱼香肉丝","value":"765"},
                {"name":"辣子鸡","value":"321"}
            ]
  };
  function getUrlParam(name) {
	  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	  var r = window.location.search.substr(1).match(reg); //匹配目标参数
	  if (r != null)
	   return unescape(r[2]);
	  return null; //返回参数值
	 }
  var data = "";
  $.post("/addressConfigScore/historyConfigScore.do",{"cafetoriumId":getUrlParam("cafetoriumId"),"index":"1","size":"1000"},function(da){
	  var status = da.status;
	//获取评论条数
		if(da.data.count!=0&&da.data.count!=null){
			$("#count").html(da.data.count);
		}else{
			$("#count").html("0");
		}
		//获取食堂信息
		var cafetoriumDto=da.data.cafetoriumDto;
//		alert(cafetoriumDto.userImg);
//		if(cafetoriumDto.userImg){
//			$("#userImg").html("<img src='.."+cafetoriumDto.userImg+"'/>");
//		}else{
//			$("#userImg").html("<img src='images/head_icon.png'/>");
//		}

		if(!!cafetoriumDto.userImg){
			$("#userImg").html("<img src='.."+cafetoriumDto.userImg+"'/>");
		}
		else{
			$("#userImg").html("<img src='images/head_icon.png'/>");
		}

		//添加样式
		$('.leader_info_wrap .head_icon .circle').css({
			"height":"5em"
		});
		$('.leader_info_wrap .head_icon .circle img').css({
			"position":"relative",
			"top":"0.3em"
		});

		$('.leader_info_wrap .head_icon .circle').css({
			'border': '0.15em solid #d0daf5',
			'border-radius': '5em'
		});




		//alert(cafetoriumDto.cafeName);
		$("#company_name").text(cafetoriumDto.cafeName);
		$("#location").text(cafetoriumDto.levelAddress);

		if(cafetoriumDto.mobileNo){
			$("#uname").html(cafetoriumDto.userName+"<a href='javascript:callPhone(\""+cafetoriumDto.mobileNo+"\");' ><img src='images/sm_tele-icon.png' /></a>");
		}else{
			$("#uname").html(cafetoriumDto.userName);
		}

		$("#historyScore").text(cafetoriumDto.score);



		if(status!=200){
			alert(da.message);
			return;
		}
		data=da.data.historyConfigScoreDtoList;

//		var sensitiveList=da.data.historyConfigScoreDtoList.sensitiveList;
//		alert(JSON.stringify(da.data.historyConfigScoreDtoList));
//		if(sensitiveList){
//			for(var i=0;i<sensitiveList.length;i++){
//				$("#con"+i).html(sensitiveList[i]+"");
//				$("#con"+i).show();
//			}
//		}
//

		tabchart(data);

		dataOne=da.data.historyConfigScoreDtoList[0];
		//alert(JSON.stringify(dataOne.time));

		  $("#detail_1").html("");
     	 $("#detail_2").html("");
           $.post("/addressConfigScore/cafetoriumConfigScoreReport.do",{"cafetoriumId":getUrlParam("cafetoriumId"),"time":dataOne.time},function(da){
         	  var status = da.status;
         			if(status!=200){
         				alert(da.message);
         				return;
         			}
         			data=da.data.historyConfigScoreDtoList;
         			  // alert("日期="+param.name);

         			$("#detail_1").append("<th style='width: 132px;'> 日期 </th>");
         			$("#detail_2").append("<td> "+dataOne.time.substring(5,dataOne.time.length)+" </td>");
         			//开始获取配置项
         			var configdetaillist = da.data.historyConfigScoreDto.configList;
         			if(configdetaillist){
         				for(var j=0;j<configdetaillist.length;j++){
         					var configJ = configdetaillist[j].configName;
         					var configJScore = configdetaillist[j].score;
         					$("#detail_1").append("<th style='width: 132px;'> "+configJ+"</th>");
         					$("#detail_2").append("<td> "+configJScore+" </td>");
         					//alert("配置项["+j+"]="+configJ);
         					//alert("配置项分数["+j+"]="+configJScore);
         				}
         			}
         			//开始获取敏感词
         			var sensitiveList=da.data.historyConfigScoreDto.sensitiveList;
         			if(sensitiveList==null){
         				sensitiveList="";
         			}
         		//	alert("敏感词="+sensitiveList);
         			$("#detail_1").append("<th style='width: 278px;'> 敏感词 </th>");
         			$("#detail_2").append("<td> "+sensitiveList+" </td>");

           });

  });
// alert(data.abc[1].name);
// conts:图表容器ID，
// cdatas:数据数组名称
function tabchart(data){

	 // 图表宽度X44=================================================================
	  $("#content").width(data.length*44);

  dishname = [];
  dishvalue = [];
  distime = [];
  // 遍历对象赋值
  $.each(data,function(index,value){
    dishname[index] = value.time.substring(5,value.time.length);
    dishvalue[index] = value.dayScore;
    distime[index] =  value.time;
  });

  //alert("dishname===="+dishname);
  // 路径配置
  require.config({
      paths: {
          echarts: 'dist'
      }
  });

  // 使用
  require(
      [
          'echarts',
          'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
      ],
      function (ec) {

          // 基于准备好的dom，初始化echarts图表
          var myChart = ec.init(document.getElementById('content'));
          var option = {

                  animation : false,
                  grid : {
                    x : 16,
                    y : 0,
                    x2 : 20,
                    y2 : 35
                  },
                  xAxis : [
                    {
                      type : 'category',
                      axisLabel : {
                        textStyle : {
                          fontFamily : 'Microsoft YaHei',
                        	fontSize : 12
                        }
                      },
                      splitLine : {
                        lineStyle : {color : "#fff"}
                      },
                      boundaryGap : false,
                      data : dishname
                    }
                  ],
                  yAxis : [
                    {
                      type : 'value',
                      max:5,
                      splitLine : {
                        lineStyle : {color : "#fff"}
                      },
                      axisTick : {show:false},
                      axisLine : {show : false},
                      axisLabel : {
                        show : false
                      }
                    }
                  ],
                  series : [
                    {
                    	precision:2,
                      name:distime,
                      type:'line',
                      stack: '总量',
                      data:dishvalue
                    }
                  ]
          };

          // 为echarts对象加载数据
          myChart.setOption(option);


          var ecConfig = require('echarts/config');
          function eConsole(param) {

        	//  alert(param.seriesName[param.dataIndex]);
        	  $("#detail_1").html("");
        	 $("#detail_2").html("");
             // console.log(param);
              $.post("/addressConfigScore/cafetoriumConfigScoreReport.do",{"cafetoriumId":getUrlParam("cafetoriumId"),"time":param.seriesName[param.dataIndex]},function(da){
            	  var status = da.status;
            			if(status!=200){
            				alert(da.message);
            				return;
            			}
            			data=da.data.historyConfigScoreDtoList;
            			  // alert("日期="+param.name);
            			$("#detail_1").append("<th style='width: 132px;'> 日期 </th>");
            			$("#detail_2").append("<td> "+param.name+" </td>");
            			//开始获取配置项
            			var configdetaillist = da.data.historyConfigScoreDto.configList;
            			if(configdetaillist){
            				for(var j=0;j<configdetaillist.length;j++){
            					var configJ = configdetaillist[j].configName;
            					var configJScore = configdetaillist[j].score;
            					$("#detail_1").append("<th style='width: 132px;'> "+configJ+"</th>");
            					$("#detail_2").append("<td> "+configJScore+" </td>");
            					//alert("配置项["+j+"]="+configJ);
            					//alert("配置项分数["+j+"]="+configJScore);
            				}
            			}
            			//开始获取敏感词
            			var sensitiveList=da.data.historyConfigScoreDto.sensitiveList;
            			if(sensitiveList==null){
            				sensitiveList="";
            			}
            		//	alert("敏感词="+sensitiveList);
            			$("#detail_1").append("<th style='width: 278px;'> 敏感词 </th>");
            			$("#detail_2").append("<td> "+sensitiveList+" </td>");

              });

          }

          // 设置滚动到最右边==================================================================================
//          $("#content_wrap").scrollLeft($("#content").width());
          myChart.on(ecConfig.EVENT.CLICK, eConsole);

      }
    );
  }

  tabchart("abc");//调用图表函数

  $("#content_wrap").scrollLeft($(this).width());

});
