$(function(){


  //下周菜单__TAB切换
  $(".tabmenu li").click(function(){
    var tabId = $(this).attr("id");
    $(this).children("img.active").show();
    $(this).children("img.blank").hide();
    $(this).siblings().children("img.blank").show()
    $(this).siblings().children("img.active").hide();
    $(this).siblings().removeClass("tabmenu_active");//tab类切换
    $(this).addClass("tabmenu_active");//tab类切换
    $("#"+tabId+"Cont").siblings().removeClass("tab_content_active");//tab内容切换
    $("#"+tabId+"Cont").addClass("tab_content_active");//tab内容切换
  });

  $(".tabmenu li:first").trigger("click");

  // 下周菜单__对号点击
  $(".dish_check").click(function(){
    $(this).children("span").toggleClass("check-link check-active");
    $(".count").text($(".check-active").length);
    $(".bottom .left span").text($(".check-active").size());
  });


});
