$(window).load(function() {
	$('.section_visual').flexslider({namespace:"toyjr-", directionNav:false, pauseOnHover:true, slideshowSpeed:5000});
	$('.section_hot').flexslider({namespace:"toyjr-", animation:"slide", animationLoop:false, controlNav:false, slideshow:false, itemWidth:231});
	$('.section_banner').flexslider({namespace:"toyjr-", directionNav:false, pauseOnHover:true, slideshowSpeed:5000, animation:"slide"});
	$('.article_prod_item2, .article_prod_item3, .article_prod_item4').flexslider({namespace:"toyjr-", animation:"slide", animationLoop:false, controlNav:false, slideshow:false, itemWidth:237});

	var toylnb='.section_lnb>ul>li';

	function removeSub(){$(toylnb).children('a').removeClass('on');$(toylnb).children('a').next().hide();}

	$(toylnb).mouseenter(function(){
		$(this).children('a').addClass('on').next().show()
	})
	$(toylnb).mouseleave(function(){
		removeSub()
	});
	$(toylnb).children('a').focusin(function(){
		removeSub()
		$(this).addClass('on').next().show()
	})
});