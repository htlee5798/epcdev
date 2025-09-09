$(document).ready(function () {
	
	/* 그리드 토글 */
	$(".data_table tr:odd").addClass("odd");
	$(".data_table tr:not(.odd)").hide();
	$(".data_table tr:first-child").show();	
	$(document).on("click", ".data_table tr.odd  td.list_pointer",function(){
		$(this).parent("tr").next("tr").toggle(200);
		$(this).parent("tr").find(".arrow").toggleClass("up");
		$(this).parent("tr").toggleClass("tr_active");
	}); 
	
	/* 왼메뉴 토글*/
    $('.nav-toggle .nav-open').on("click", function (e) {
		e.preventDefault();
		$('nav .nav-toggle').css('background','#4a506e').css('border-radius','16px').css('right','15px').css('box-shadow','0px 0px 5px rgba(0,0,0,0.2)');
        $('header').toggleClass('nav-active');
		$('.nav-overlay').bind('touchmove', function(e){e.preventDefault()});
    });
	$('.nav-toggle .nav-close').on("click", function (e) {
        e.preventDefault();
		$('nav .nav-toggle').css('background','#e70014').css('border-radius','5px').css('right','-48px').css('box-shadow','0px 0px 5px rgba(0,0,0,1)');
		$('header').toggleClass('nav-active');
		$('.nav-overlay').unbind('touchmove');
		$($('a.btn-cog').data('target')).slideToggle(300);
		$("#bookmark").hide();
    });
	$('.btn-cog').on("click", function (e) {
        e.preventDefault();
        $($(this).data('target')).slideToggle(300);
    });
	
	$("#left-navi-menu").jagabiMenu();
	$("#ca-list-menu").jagabiCategory();
	
	/* 점포선택*/
	$(".store_table tr").click(function(){
		$(this).toggleClass("tr_active");
	}); 
	
});

/* 메뉴 네비*/
(function($, window, document, undefined) {
    var pluginName = "jagabiMenu";
    var defaults = {
        speed: 300,
        showDelay: 0,
        hideDelay: 0,
        singleOpen: true,
        clickEffect: true
    };
    function Plugin(element, options) {
        this.element = element;
        this.settings = $.extend({},
        defaults, options);
        this._defaults = defaults;
        this._name = pluginName;
        this.init()
    };
    $.extend(Plugin.prototype, {
        init: function() {
            this.openSubmenu();
            this.submenuIndicators();
			/*
            if (defaults.clickEffect) {
                this.addClickEffect()
            }
			*/
        },
        openSubmenu: function() {
            $(this.element).children("ul").find("li").bind("click",
            function(e) {
                e.stopPropagation();
                e.preventDefault();
                if ($(this).children(".submenu").length > 0) {
                    if ($(this).children(".submenu").css("display") == "none") {
                        $(this).children(".submenu").delay(defaults.showDelay).slideDown(defaults.speed);
                        $(this).children(".submenu").siblings("a").addClass("submenu-indicator-minus");
                        if (defaults.singleOpen) {
                            $(this).siblings().children(".submenu").slideUp(defaults.speed);
                            $(this).siblings().children(".submenu").siblings("a").removeClass("submenu-indicator-minus")
                        }
                        return false
                    } else {
                        $(this).children(".submenu").delay(defaults.hideDelay).slideUp(defaults.speed)
                    }
                    if ($(this).children(".submenu").siblings("a").hasClass("submenu-indicator-minus")) {
                        $(this).children(".submenu").siblings("a").removeClass("submenu-indicator-minus")
                    }
                }
                window.location.href = $(this).children("a").attr("href")
            })
        },
        submenuIndicators: function() {
            if ($(this.element).find(".submenu").length > 0) {
                $(this.element).find(".submenu").siblings("a").append("<span class='submenu-indicator'>+</span>")
            }
        },
		
    });
    $.fn[pluginName] = function(options) {
        this.each(function() {
            if (!$.data(this, "plugin_" + pluginName)) {
                $.data(this, "plugin_" + pluginName, new Plugin(this, options))
            }
        });
        return this
    }
})(jQuery, window, document);

/* 카테고리 메뉴*/
(function($, window, document, undefined) {
    var pluginName = "jagabiCategory";
    var defaults = {
        speed: 300,
        showDelay: 0,
        hideDelay: 0,
        singleOpen: true,
        clickEffect: true
    };
    function Plugin(element, options) {
        this.element = element;
        this.settings = $.extend({},
        defaults, options);
        this._defaults = defaults;
        this._name = pluginName;
        this.init()
    };
    $.extend(Plugin.prototype, {
        init: function() {
            this.openSubmenu();
            this.submenuIndicators();
        },
        openSubmenu: function() {
           $(this.element).children("ul").children("li").find("a").bind("click",
            function(e) {
                e.stopPropagation();
                e.preventDefault();
                if ($(this).parent("li").children(".submenu").length > 0) {
                    if ($(this).parent("li").children(".submenu").css("display") == "none") {
                        $(this).parent("li").children(".submenu").delay(defaults.showDelay).slideDown(defaults.speed);
                        $(this).parent("li").children(".submenu").siblings("a").addClass("submenu-indicator-minus");
                        if (defaults.singleOpen) {
                            $(this).parent("li").siblings().children(".submenu").slideUp(defaults.speed);
                            $(this).parent("li").siblings().children(".submenu").siblings("a").removeClass("submenu-indicator-minus");
							//$(this).parent("li").css('background','#fff9b9');
                        }
                        return false
                    } else {
                        $(this).parent("li").children(".submenu").delay(defaults.hideDelay).slideUp(defaults.speed)
						//$(this).parent("li").css('background','#fff');
						//$(this).parent("li").children(".submenu").children("li").css('background','#f9f9f9');
                    }
                    if ($(this).parent("li").children(".submenu").siblings("a").hasClass("submenu-indicator-minus")) {
                        $(this).parent("li").children(".submenu").siblings("a").removeClass("submenu-indicator-minus");
                    }
                }
                window.location.href = $(this).parent("li").children("a").attr("href")
            });
			
			// 롱터치 시 팝업
			var isTapHold = false;
			$(this.element).children("ul").find("li").on("taphold",
            function(e) {
                isTapHold = true;
				popupid = $(this).attr('rel');
				$('#' + popupid).fadeIn();
				$('body').append('<div id="fade"></div>');
				$('#fade').css('opacity','.80').fadeIn();
				$(window).bind('touchmove', function(e){e.preventDefault()});//파업창 및 스크롤 정지

				popuptopmargin = ($('#' + popupid).height() + 10) / 2;
				popupleftmargin = ($('#' + popupid).width() + 0) / 2;
				
				$('#' + popupid).css({
				'margin-top' : -popuptopmargin,
				'margin-left' : -popupleftmargin
				});
            });
			
			$(this.element).children("ul").find("li").on('tap', function(e){
				if(!isTapHold){
				  //TODO tap events
				}else{
				  isTapHold = false;
				}
			});
			//롱터치 끝
			
        },
		
		
        submenuIndicators: function() {
            if ($(this.element).find(".submenu").length > 0) {
                $(this.element).find(".submenu").siblings("a").append("<span class='submenu-indicator'>+</span>")
            }
        },
		
    });
    $.fn[pluginName] = function(options) {
        this.each(function() {
            if (!$.data(this, "plugin_" + pluginName)) {
                $.data(this, "plugin_" + pluginName, new Plugin(this, options))
            }
        });
        return this
    }
})(jQuery, window, document);

/* 탭(Tab)*/
function tab_menu(num){	
	var f = $('.menu-tab').find('li');
	for ( var i = 0; i < f.length; i++ ) {			
		if ( i == num) {			
			f.eq(i).addClass('active');	
			$('.tab_view' + i ).show();
		} else {
			f.eq(i).removeClass('active');					
			$('.tab_view' + i ).hide();
		}
	}
}

/* 즐겨찾기 설정 */
function sort_list(num){	
	if ( 1 == num) {			
		$(".contents ul").removeClass( 'sortable-off' );
		$(".contents ul").addClass( 'sortable' );
		$(".contents ul.sortable").addClass( 'ui-sortable' );
		$( ".sortable" ).sortable({ 
		    disabled: false,
			placeholder: "ui-sortable-placeholder",
			opacity:0.8
		});
		$("#btn_ok").show();
		$("#btn_edit").hide();
	} else if ( 2 == num) {
		$(".contents ul.sortable").removeClass( 'ui-sortable' );
		$(".contents ul").removeClass( 'sortable' );
		$(".contents ul").addClass( 'sortable-off' );
		$(".sortable-off").sortable({
  			disabled: true
		});
		$("#btn_ok").hide();
		$("#btn_edit").show();
	}
}

/* input 문구 리셋*/
function reset_text(ele){
  if(ele.value == ele.defaultValue)
    ele.value = '';
}
function restore_text(ele){
  if(ele.value == '')
    ele.value = ele.defaultValue;
}