//$(function(){
//
//  $("#dialog_mask").height($(document).height());//遮罩高度
//  var dialog_left = ($(window).width() - $("#dialog").width())/2;
//  var dialog_top = ($(window).height() - $("#dialog").height())/2;
//
//
//  $("#close_bt").click(function(){
//    $("#dialog_mask,#dialog").fadeOut();
//  });
// 
//});
//

//显示弹出框
function showDialog()
{
	$("#dialog_mask").height($(document).height());//遮罩高度
	var dialog_left = ($(window).width() - $("#dialog").width())/2;
	var dialog_top = ($(window).height() - $("#dialog").height())/2;
	  
    $("#dialog_mask,#dialog").fadeIn();
    $("#dialog").css({
      "left":(dialog_left-5) + "px",
      "top":(dialog_top-10) + "px"
    });
}

//关闭弹出框
function closeDialog()
{
    $("#dialog_mask,#dialog").fadeOut();
}