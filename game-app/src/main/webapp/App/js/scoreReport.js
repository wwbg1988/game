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
//以下是metro磁贴样式
	//以下是metro磁贴样式
	var windowWide = (($(document.body).width())/100)*90;// wrap 内宽度
	var big_gap = (windowWide/100)*2;//大容器间隙
	var big_col_width = (windowWide - (big_gap*2))/3;//大容器宽度
	var small_tag_width = (big_col_width - big_gap)/2;//小窗口宽度
	$(".key_col").css({"width":big_col_width,"margin-left":big_gap});
	$(".key_col:first-child").css("margin-left","0");
	$(".small_tag_wrap").css("margin-top",big_gap);
	$(".small_tag_wrap_2").css("margin-top","0");
	$(".big_tag_2").css("margin-top",big_gap);
	$(".stag").css({"width":small_tag_width,"height":small_tag_width});
	$(".stag:last-child").css("margin-left",big_gap);
	$(".btag").css("line-height",$(".btag").width() + "px");
	$(".stag").css("line-height",$(".stag").width() + "px");
	$(".flat_tag").width(big_col_width).css("margin-left",0);
	$(".key_col:nth-child(2) .small_tag_wrap:last-child").css("margin-top",big_gap);
	$(".big_tag").css({
		"height":big_col_width,
		"line-height":big_col_width + "px"
	});
	// metro end

	$(".circle").height($(".circle").width());//设置头像高度
	var circle_gap = ($(".circle").width() - $(".circle img").width())/2;
	$(".circle img").css("margin-top",circle_gap);
	$("#main").css("padding-top","20px");
// metro end
	


//获取url中的参数
//name=需要获取的参数
function getUrlParam(name) {
 var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
 var r = window.location.search.substr(1).match(reg); //匹配目标参数
 if (r != null)
  return unescape(r[2]);
 return null; //返回参数值
}
var data = "";
$.post("/addressConfigScore/cafetoriumConfigScoreReportJson.do",{"cafetoriumId":getUrlParam("cafetoriumId"),"time":getUrlParam("time")},function(da){
//$.post("/addressConfigScore/cafetoriumConfigScoreReportJson.do",{"cafetoriumId":"5","time":"2015-08-25"},function(da){
	var status = da.status;
	//获取评论条数
	if(da.data.count!=0&&da.data.count!=null){
		$("#count").html(da.data.count);
	}else{
		$("#count").html("0");
	}
	//获取食堂信息
	var cafetoriumDto=da.data.cafetoriumDto;
	$("#cafetoriumAddress").html(cafetoriumDto.levelAddress);
	$("#cafetoriumName").html(cafetoriumDto.cafeName);
	if(cafetoriumDto.mobileNo){
		$("#userName").html(cafetoriumDto.userName+"<a href='javascript:callPhone(\""+cafetoriumDto.mobileNo+"\");' ><img src='images/sm_tele-icon.png' /></a>");
	}else{
		$("#userName").html(cafetoriumDto.userName);
	}
	if(cafetoriumDto.userImg){
		$("#userImg").html("<img src='.."+cafetoriumDto.userImg+"'/>");
	}else{
		$("#userImg").html("<img src='images/head_icon.png'/>");
		$(".circle img").css("margin-top",circle_gap);
	}
//	$("#userImg").html("<img src='images/head_icon.png'/>");
//	$(".circle img").css("margin-top",circle_gap);

	if(status!=200){
		alert(da.message);
		return;
	}
	data=da.data.scoreReportDto;

	var sensitiveList=da.data.sensitiveList;
	if(sensitiveList){
		for(var i=0;i<sensitiveList.length;i++){
			$("#con"+i).html(sensitiveList[i]+"");
			$("#con"+i).show();
		}
	}



	//以下是图表
	var dishname = [];
	var dishvalue = [];
	var dishhisvalue = [];

	  $.each(data["datas"],function(index,value){
	    dishname[index] = value.name;
	    dishvalue[index] = value.value;
	    dishhisvalue[index] = value.hisvalue;
	  });

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
	          'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
	          'echarts/chart/line'
	      ],
	      function (ec) {
	          // 基于准备好的dom，初始化echarts图表
	          var myChart = ec.init(document.getElementById('main'));

	          var option = {
	            backgroundColor:"#fff",
	            // color:["#5ab1ef"],
	            grid : {
	              borderWidth: 0,
	              x : 50,
	              x2: 50,
	              y:50
	            },
	            animation:false,
	            calculable : false,
	            legend: {
								setSelected:false,
	                data:['今日平均分','历史平均分']
	            },
	            xAxis : [
	                {
	                    splitLine : {
	                          show:false
	                    },
	                    type : 'category',
	                    axisLine : {
	                      show:true,
	                      lineStyle: {
	                          color: '#000000',
	                          type: 'solid',
	                          width: 1
	                      }
	                    },
	                    data : dishname
	                }
	            ],
	            yAxis : [
	                {
	                    splitLine : {
	                        show:false
	                    },
	                    type : 'value',
	                    axisLine : {
	                      show:true,
	                      lineStyle: {
	                          color: '#000000',
	                          type: 'solid',
	                          width: 1
	                      }
	                    },
	                    axisTick:{
	                      show:true,
	                      lineStyle: {
	                          color: '#000000',
	                          width: 1
	                      }
	                    },
	                    name : '评分',
	                    axisLabel : {
	                        formatter: '{value} '
	                    }
	                },
	                {
	                    type : 'value',
	                    name : '温度',
	                    axisLabel : {
	                      show: false
	                    },
	                    axisLine : {
	                      show:false
	                    },
	                }
	            ],
	            series : [
	                {
	                    barCategoryGap:'50',
	                    itemStyle:{
	                      normal: {
	                          color: "#5ab1ef",
	                           label : {
	                               show: true,
	                               position: 'insideTop',
	                               formatter: '{c}'
	                           }
	                       }
	                    },
	                    name:'今日平均分',
	                    type:'bar',
	                    data:dishvalue
	                },
	                {
	                  itemStyle:{
	                    normal: {
	                        color: "#1b75e7",
	                         label : {
	                             show: true,
	                             position: 'insideTop',
	                             formatter: '{c}'
	                         }
	                     }
	                  },
	                    name:'历史平均分',
	                    type:'line',
	                    yAxisIndex: 1,
	                    data:dishhisvalue
	                }
	            ]
	        };

	          // 为echarts对象加载数据
	          myChart.setOption(option);
						//刷新
						setTimeout(function(){
							myChart.refresh()
						},300);

	      }
	  );

	});
});
