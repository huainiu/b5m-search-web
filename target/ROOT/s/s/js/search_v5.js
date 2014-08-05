(function($){
	window.newSearch = window.newSearch || {
		init: function(){
			window.setSliderWidth = function(li){
				var w = $(".p-slider-bar .sc-ctn li", li).length * 140;
				var $sctn = $('.p-slider-bar .sc-ctn', li)
				var sctn = $sctn.get(0);
				var $sctnInner = $sctn.find('ul');
				var sctnInner = $sctnInner.get(0);
				$sctnInner.width(w);
			};

			$(document).on({
				click: function(){
					var $sctn = $(this).closest('.p-slider-bar').find('.sc-ctn');
					var sctn = $sctn.get(0);
					var $sctnInner = $sctn.find('ul');
					var sctnInner = $sctnInner.get(0);
					var that = this;
					var sl = sctn.scrollLeft;
					if(!$(this).hasClass('disabled')){
						$sctn.animate({scrollLeft:sl-140}, 500, function(){
							if(sctn.scrollLeft <= 0){
								$(that).addClass('disabled');
							}
							if(sctn.scrollLeft+840 < sctnInner.scrollWidth){
								$('.p-slider-bar .right').removeClass('disabled');
							}
						});
					}
					return false;
				}
			}, '.p-slider-bar .left').on({
				click: function(){
					var $sctn = $(this).closest('.p-slider-bar').find('.sc-ctn');
					var sctn = $sctn.get(0);
					var $sctnInner = $sctn.find('ul');
					var sctnInner = $sctnInner.get(0);
					var that = this;
					var sl = sctn.scrollLeft;
					if(!$(this).hasClass('disabled')){
						$sctn.animate({scrollLeft:sl+140}, 500, function(){
							if(sctn.scrollLeft+840 >= sctnInner.scrollWidth){
								$(that).addClass('disabled');
							}
							if(sctn.scrollLeft > 0){
								$('.p-slider-bar .left').removeClass('disabled');
							}
						});
					}
					return false;
				}
			}, '.p-slider-bar .right');

		}
	};
})(jQuery);