$(function() {

	// 以下是metro磁贴样式
	var windowWide = (($(document.body).width()) / 100) * 90;// wrap 内宽度
	var big_gap = (windowWide / 100) * 3;// 大容器间隙
	var big_col_width = (windowWide - (big_gap * 2)) / 3 - 1;// 大容器宽度
	var small_tag_width = (big_col_width - big_gap) / 2;// 小窗口宽度
	$(".key_col").css({
		"width" : big_col_width,
		"margin-left" : big_gap
	});
	$(".key_col:first-child").css("margin-left", "0");
	$(".small_tag_wrap").css("margin-top", big_gap);
	$(".small_tag_wrap_2").css("margin-top", "0");
	$(".big_tag_2").css("margin-top", big_gap);
	$(".stag").css({
		"width" : small_tag_width,
		"height" : small_tag_width
	});
	$(".stag:last-child").css("margin-left", big_gap);
	$(".btag").css("line-height", $(".btag").width() + "px");
	$(".stag").css("line-height", $(".stag").width() + "px");
	// metro end

	// 获取url中的参数
	// name=需要获取的参数
	function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg); // 匹配目标参数
		if (r != null)
			return unescape(r[2]);
		return null; // 返回参数值
	}

	var data = "";
	$.post("/http/api/carte/dishPercent.do", {
		"cafetoriumId" : getUrlParam("cafetoriumId"),
		"carteWeek" : getUrlParam("carteWeek")
	}, function(da) {

		var status = da.status;
		if (status != 200) {
			alert(da.message);
			return;
		}
		data = da.data.dishTypeList;

		function tabchart(conts, cdatas) {

			dishname = [];
			dishvalue = [];

			cont_index = conts.substr(conts.length-1,1);
			
			


			// 遍历对象赋值
			$.each(data, function(index, value) {

				var datas = data[index]['datas'];
				if (datas)
					$.each(datas, function(index2, value2) {
						if (cdatas == datas[index2].typeId) {
							dishname.push(value2.name);
							dishvalue.push(value2.value);		
							
							//chart container height
							chart_length = data[index];
							$("#content" + cont_index).height(50*chart_length["datas"].length);
						}

						
					});
				

				
			});
			

			
			// 路径配置
			require.config({
				paths : {
					echarts : 'dist'
				}
			});

			// 使用
			require([ 'echarts', 'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
			], function(ec) {
				// 基于准备好的dom，初始化echarts图表
				var myChart = ec.init(document.getElementById(conts));
				var option = {
					color : [ "#5ab1ef" ],
					grid : {
						borderWidth : 0,
						x : 80,
						x2 : 40,
						y : 30,
						y2 : 10
					},
					calculable : false,
					xAxis : [ {
						name:'（ % ）',
						position : 'top',
						axisLabel : {
							show : true,
							interval : 'auto',
							margin : 8,
							formatter : '',
							textStyle : {
								color : '#666',
								fontFamily : 'Microsoft YaHei',
								fontSize : 12,
							}
						},
						splitLine : {
							show : false
						},
						axisLine : {
							show : true,
							lineStyle : {
								color : '#000000',
								type : 'solid',
								width : 1
							}
						},
						axisTick : {
							show : true,
							lineStyle : {
								color : '#000000',
								width : 1
							}
						},
						type : 'value',
						boundaryGap : [ 0, 0.01 ]
					} ],
					yAxis : [ {
						
						axisLabel : {
							show : true,
							interval : '0',
							margin : 40,
							textStyle : {
								align : "center",
								color : '#333',
								fontFamily : 'Microsoft YaHei',
								fontSize : 14
							},
							formatter : function(value){
								if(value.length > 4){
									return(value.substr(0,4) + "\n" + value.substr(4,value.length));
								}
								return value;
							}
						},
						axisTick : {
							show : false,
						},
						splitLine : {
							show : false
						},
						axisLine : {
							show : true,
							lineStyle : {
								color : '#000',
								type : 'solid',
								width : 1
							}
						},
						type : 'category',
						data : dishname
					} ],
					series : [ {
						barCategoryGap : "40%",
						backgroundColor : "#abc",
						color : [ "#abc" ],
						clickable : false,
						name : '2012年',
						type : 'bar',
						itemStyle : {
							normal : {
								label : {
									show : true,
									position : 'insideRight',
									formatter : '{c}%'
								}
							}
						},
						data : dishvalue
					} ]
				};
				

				// 为echarts对象加载数据
				myChart.setOption(option);
			});
		}

		// 遍历对象赋值
		$.each(da.data.dishTypeList, function(index, value) {
			var indexs = index + 1;
			$(".tab_wrap ul").append(
					"<li name='tab_content" + indexs + "' title='"
							+ value.typeId + "'>" + value.typeName + "</li>")
		});
		$(".tab_wrap li:first").addClass("active")
		// tab
		$(".tab_wrap li").click(function() {
			$(this).addClass("active");
			$(this).siblings("li").removeClass("active");
			var cindex = $(this).attr("name");
			var cdata = $(this).attr("title");
			$("#" + cindex).siblings().hide();
			$("#" + cindex).fadeIn();
			var chartc = $("#" + cindex).children("div").attr("id");

			tabchart(chartc, cdata);// 调用图表函数
		});

		$(".tab_wrap li:first-child").trigger("click");// 默认tab click
		// tab end
	});
});
