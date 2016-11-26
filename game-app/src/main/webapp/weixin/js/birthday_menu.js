$(function(){
  $("#dates").focus(function(){
    $(this).css("background","none").removeClass("dates_bg");;
    $(this).blur(function(){
      if (!$("#dates").val()){
        $("#dates").addClass("dates_bg");
      }
    });
  });

});
