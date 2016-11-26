$(function(){

//  $("#dialog_mask").height($(document).height());//遮罩高度
//  var dialog_left = ($(window).width() - $("#dialog").width())/2;
//  var dialog_top = ($(window).height() - $("#dialog").height())/2;
//
//  $(document).click(function(){
//    $("#dialog_mask,#dialog").fadeIn();
//    $("#dialog").css({
//      "left":(dialog_left-5) + "px",
//      "top":(dialog_top-10) + "px"
//    });
//  });
//
//  $("#close_bt").click(function(){
//    $("#dialog_mask,#dialog").fadeOut();
//  });

  

//  $('input').focus(function(){
//      $(this).removeClass("input_error");
//  });
//
//       //验证用户名
//   $('input[name="name"]').blur(function(){
//          if( this.value=="" || this.value.length < 2 ){
//            // $(".input_title").text("请正确填写以下信息").css("color","a94442");
//            // $(this).addClass("input_error");
//          }
//   });
//
//   $('input[name="tele"]').blur(function(){
//          if( this.value=="" || !(/^1[3|4|5|8][0-9]\d{4,8}$/.test(this.value)) ){
//            // $(".input_title").text("请正确填写以下信息").css("color","a94442");
//            // $(this).addClass("input_error");
//          }
//   });
//
//   $('input[name="addr"]').blur(function(){
//          if( this.value=="" || this.value.length < 8 ){
//            // $(".input_title").text("请正确填写以下信息").css("color","a94442");
//            // $(this).addClass("input_error");
//          }
//   });
//
//
//  //提交，最终验证。
//   $('input[type="submit"]').click(function(){
//          $("form :input").trigger('blur');
//          var numError = $('form .input_error').length;
//          if(numError){
//              return false;
//          }
//   });

});



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
