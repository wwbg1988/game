$(function(){
	var data = "";
	$.post(appName+"/http/api/catering/sensitive.do?reportId="+location.search.substr(location.search.indexOf('=')+1,location.search.length-1),function(da){
		
		var status = da.status;
		if(status!=200){
			return;
		}
		data=da.data;
		//以下是图表
		var dishname = [];
		var dishvalue = [];

		  $.each(data["datas"],function(index,value){
		    dishname[index] = value.name;
		    dishvalue[index] = value.value;
		  });
	 


      //食堂负责人信息和单条关键词对象
	  //$(".leader").before(data.addressUser.phone).text(data.addressUser.userName);
	  $(".leader").text(data.addressUser.userName);  
	  $(".item_info span.left").text(data.addressUser.sensitiveName);
	  $(".item_info span.right").text(data.addressUser.sensitivePer);
	  $(".add_info span.left").text(data.addressUser.addressCafetorium); 
	  $(".add_info span.right").text(data.addressUser.cafetorium); 
	  
var pieData = [];

  pieData = data["datas"];
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
          'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
      ],
      function (ec) {
          // 基于准备好的dom，初始化echarts图表
          var myChart = ec.init(document.getElementById('pie'));

          var option = {
                  backgroundColor:"#f2f2f2",
                  series : [
                      {
                          name:'访问来源',
                          type:'pie',
                          radius : '90%',
                          center: ['50%', '50%'],
                          legendHoverLink:false,
                          itemStyle : {
                              normal : {
                                  label : {
                                      position : 'inner',
                                      formatter : "{b}\n{d}%",
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
                                      formatter : "{b}\n{d}%",
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
