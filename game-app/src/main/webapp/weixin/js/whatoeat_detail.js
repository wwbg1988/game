$(function(){

  var bullets = document.getElementById('position').getElementsByTagName('li');
	var banner = Swipe(document.getElementById('mySwipe'), {
		auto: 2000,
		continuous: true,
		disableScroll:false,
		callback: function(pos) {
			
			var t = $('.swipe-wrap').find('.wrap').eq(pos).find('img').attr('alt')
			$('#mes').text(t)
			var i = bullets.length;
			while (i--) {
			  bullets[i].className = ' ';
			}
			bullets[pos].className = 'cur';
		}
	});
	
	$('.bottom .left > ul').on('click','li',function(){
		    var num = $("#purchaseNumber").val();
			var i = $(this).index();
			var d = $('#count');
			if(i == 2){
				if (eval(d.text()) < eval(num) || num == "") {
					if(i == 2){
						d.text(parseInt(d.text()) + 1)
					}else{
						return false;	
					}
				} else {
					alert("该商品限购"+num+"件");
				}
			}
			if(i == 0){
				if(parseInt(d.text()) > 1){
					d.text(parseInt(d.text()) - 1)
				}
			}
			
	})

});
