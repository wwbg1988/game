$(function(){

  //NEW==============================================================================
  //菜单顶部位置LI样式
  $(".leftnav").prepend('<li id="changeCafetorium"><h3 class="current"><span class="js-currentCafetorium">武汉人民医院每一食堂</span><span class="default" style="cursor:pointer;">更改</span></h3></li>');
  //NEW==============================================================================

  $("section").css("min-height",$(window).height() - $(".header").height());//section 最小高度

  // $("#mask").height($(document).height() - $(".header").height());//遮罩高度

  // $(".menu").height($(window).height() - $(".header").height());//菜单高度

  $(".menu > ul").height($(window).height() - $(".header").height() );//菜单高度

  $(".menu a.active").find("span.w").addClass("active");//菜单图标

  // 收起菜单
  var n = 1;
  function toggleHeight(){
    if (n) {
      $("section").height($(window).height() - $(".header").height());
      n = !n;
    } else {
      $("section").height("auto");
      n = !n;
    }

  };
  $(".header .icon_left").click(function(){
    $(".bottom").slideToggle();
    $("#mask").fadeToggle();
    $(".submit-bt").slideToggle();
    $("section").toggleClass("height_fixed");
    $(".menu section").toggleClass("section_slide");
    $(".menu .sidenav").toggleClass("leftnav sidenav_width");
    $(".menu").toggleClass("rot");
    toggleHeight();
  });

  //遮罩点击
  $("#mask").click(function(){
    $(".header .icon_left").trigger("click");
  });

  //设置提交按钮位置
  var count = $(".submit-bt").length;
  if(!!count){
    if($(".submit-bt").offset().top + $(".submit-bt").height() < $(window).height()){
      $(".submit-bt").css({
        "position":"fixed",
        "left":"0",
        "bottom":"0"
      });
    }
  }


  /*天气暂不需要
  //设置日期显示
  function show(){
    var mydate = new Date();
    var str = "" + mydate.getFullYear() + "/";
    str += (mydate.getMonth()+1) + "/";
    str += mydate.getDate();
    return str;
  }
  switch ( new Date().getDay()) {
    case 0:
    var week = "星期日";
    break;
    case 1:
    var week = "星期一";
    break;
    case 2:
    var week = "星期二";
    break;
    case 3:
    var week = "星期三";;
    break;
    case 4:
    var week = "星期四";
    break;
    case 5:
    var week = "星期五";
    break;
    default: var week = "星期五"
  }
 
  $("#today").text(show());
  $("#week").text(week);
 */
  //  添加购物车效果
  $(".add").click(function(){
    cartBubble("count");
  });

});

function cartBubble(cartClass){
    $('.' + cartClass).addClass("active");
    setTimeout(function(){
      $('.' + cartClass).removeClass("active");
    },500);
  }
