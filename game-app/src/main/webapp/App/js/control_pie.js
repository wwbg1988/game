$(function(){

	//自动解码经过encodeURIComponent加密过的中文
	function getUrlParameter(name)
	{
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		//window.location.search获取问号(?)+之后的所有参数
		var r = window.location.search.substr(1).match(reg);
		if(r!=null)
		{
			return decodeURIComponent(r[2]); //解码中文
		}
		return null;
	}
	
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
  // metro end

$(".circle").height($(".circle").width());//设置头像高度
var circle_gap = ($(".circle").width() - $(".circle img").width())/2;
$(".circle img").css("margin-top",circle_gap);
$('.btag, .stag').hide();
var data = "";
$.post("/http/api/catering/sensitive.do?reportId="+getUrlParameter("reportId")+"&sensitiveId="+getUrlParameter("sensitiveId"),function(da){
	
	var status = da.status;
	if(status!=200){
		return;
	}
	data=da.data;
	//以下是图表
	var dishname = [];
	var dishvalue = [];
//	$('.btag, .stag').each(function(i){
	
		$.each(data["data"],function(index,value){
		    dishname[index] = value.name;
		    dishvalue[index] = value.value;
		    var d = $('.btag, .stag').get(index);
		    $(d).show();
		    $(d).text(value.name);
		  });
//	})
//	  $.each(data["datas"],function(index,value){
//	    dishname[index] = value.name;
//	    dishvalue[index] = value.value;
//	  });

      //食堂负责人信息和单条关键词对象
	  //$(".leader_info span.uname").before(data.addressUser.phone).text(data.addressUser.userName);
	  $(".leader_info span.uname").text(data.addressUser.userName);
	  $(".item_info span.left").text(data.addressUser.sensitiveName);
	  $(".item_info span.right").text(data.addressUser.sensitivePer);
	  $(".leader_info p.location").text(data.addressUser.addressCafetorium); 
	  $(".leader_info p.company_name").text(data.addressUser.cafetorium); 
	  $(".key_info span span.orange").text(data.addressUser.commentCount);
	  var img = data.addressUser.userImg;
	  if(img != null){
		  $("#imageUrl").attr('src',".."+data.addressUser.userImg); 
	  }
	  
	  //获取食堂信息
	  var cafetoriumDto=da.data.addressUser;
	  $(".leader_info span.uname").html(cafetoriumDto.userName+"<a href='tel:"+cafetoriumDto.phone+"'><img src='images/sm_tele-icon.png' /></a>");
	  
var pieData = [];

  pieData = data["datas"];
  // 路径配置
  require.config({
      paths: {
          echarts: 'dist'
      }
  });

  var flag = [pieData[0].name];
  if(pieData[1]){
	  flag[1]="其他";
  }
  // 使用
  require(
      [
          'echarts',
          'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
      ],
      function (ec) {
          // 基于准备好的dom，初始化echarts图表
          var myChart = ec.init(document.getElementById('pie'));
          var option = {
        		  legend: {
                      x : 'center',
                      y : 'top',
                      padding : 10,
                      data:flag
                  },
                  backgroundColor:"#fff",
                  series : [
                      {
                              type:'pie',
                          radius : '90%',
                          legendHoverLink:false,
                          itemStyle : {
                              normal : {
                                  label : {
                                      position : 'inner',
//                                      formatter : "{b}\n{d}%",
                                      formatter : "{d}%",
                                      textStyle:{
                                        fontFamily:"Microsoft YaHei",
                                        fontSize:16
                                      }
                                  },
                                  labelLine : {
                                      show : false
                                  }
                              },
                              emphasis : {
                                  label : { 
                                      show : true,
//                                      formatter : "{b}\n{d}%",
                                      formatter : "{d}%",
                                      textStyle:{
                                        fontFamily:"Microsoft YaHei",
                                        fontSize:16
                                      }
                                  }
                              }
                          },
                          data:pieData
                      }
                  ]
              };
          // 为echarts对象加载数据
          myChart.setOption(option);
      }
  );
});
});
