$(function(){

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

var data = {
    "datas":[
                {"name":"粮醋排骨","value":"8"},
                {"name":"大排","value":"6"},
                {"name":"炸猪排","value":"10"}
            ]
}


//以下是图表
var dishname = [];
var dishvalue = [];

  $.each(data["datas"],function(index,value){
    dishname[index] = value.name;
    dishvalue[index] = value.value;
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
          'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
      ],
      function (ec) {
          // 基于准备好的dom，初始化echarts图表
          var myChart = ec.init(document.getElementById('main'));

          var option = {
              backgroundColor:"#fff",
              color:["#1b809e"],
              grid : {
                borderWidth: 0,
                x : 50,
                x2: 50,
                y:30
              },
              calculable : false,
              xAxis : [
                  {
                    splitLine : {
                        show:false
                    },
                    type : 'category',
                    data : dishname
                  }
              ],
              yAxis : [
                  {
                      splitLine : {
                          show:false
                      },
                      type : 'value'
                  }
              ],
              series : [
                  {
                      barCategoryGap:'50',
                      name:'对比项目',
                      type:'bar',
                      itemStyle:{
                        normal: {
                             label : {
                                 show: true,
                                 position: 'insideTop',
                                 formatter: '{c}'
                             }
                         }
                      },
                      data:dishvalue
                  }
              ]
          };

          // 为echarts对象加载数据
          myChart.setOption(option);
      }
  );

});
