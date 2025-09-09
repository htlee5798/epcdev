function gnbInit() {
	var url = "/v3/gnb-category.html";
	if (location.protocol === "http:") {
		url = _LMAppUrl + url;
	}
	else {
		url = _LMAppSSLUrl + url;
	}
	$.get(url)
	.done(function(text) {
		$("#gnbCategoryWrap").html(text);
		
		
		setTimeout(function() {
			tabContent('.gnb-tab-nav', 1); // GNB > 카테고리 보기, 카테고리 전체보기 탭.

			$('.link-gnb-category').off().on('click', function(){
				var tabId = $(".wrap-gnb-category a.tab-menu.active").attr("data-gnb-tab");
				if( !$("#gnbTabCont" + tabId).hasClass("active")) {
					$("[id^=gnbTabCont]").removeClass("active");
					$("#gnbTabCont" + tabId).addClass("active");
				}
				gnbLayerFade( this, '.gnb .wrap-gnb-category' );
			}).click();
			
			$(".wrap-gnb-category .depth2[id^=depth2]").attr("style", "");
			var tabElm = $(".wrap-gnb-category").find(".wrap-tab-cont");
			tabElm.find(".depth1-menu > li").each(function(index, menu) {
				$(menu).mouseenter(function(e) {
					tabElm.find("li.active").removeClass("active");
					$(menu).addClass("active");
					var categoryId = $(menu).find("a").attr("data-gnb-category");
					tabElm.find(".depth2.depth-active, .depth3.depth-active").removeClass("depth-active");
					tabElm.find("#depth2-" + categoryId).addClass("depth-active");
				});
			});
			
			$(".wrap-gnb-category").mouseleave(function(e) {
				$(".wrap-gnb-category li.active").removeClass("active");
			});
			
			var tabElm2 = $(".wrap-gnb-category").find(".wrap-tab-cont").find(".wrap-depth2-depth3");
			tabElm2.find(".depth2-menu li").each(function(index, menu) {
				$(menu).mouseenter(function(e) {
					tabElm2.find("li.active").removeClass("active");
					$(menu).addClass("active");
					var categoryId = $(menu).find("a").attr("data-gnb-category");
					tabElm2.find(".depth3.depth-active").removeClass("depth-active");
					tabElm2.find("#depth3-" + categoryId).addClass("depth-active");
				});
			});
			
			$(".wrap-all-category .gnb-tab-nav > a").click(function(e) {
				var tabId = $(this).attr("data-gnb-tab");
				var tabElm = $(".wrap-all-category .wrap-tab-cont");
				tabElm.empty();
				tabElm.append($("#gnbTabContAll" + tabId).html());
			});

			$('.link-category-all').off().on('click', function(event){
				eventFunc(event);
				var tabId = $(".wrap-gnb-category a.tab-menu.active").attr("data-gnb-tab");
				
				$(".wrap-all-category .gnb-tab-nav > a.active").removeClass("active");
				$(".wrap-all-category .gnb-tab-nav > a[data-gnb-tab=" + tabId + "]").addClass("active");
				var tabElm = $(".wrap-all-category .wrap-tab-cont");
				tabElm.empty();
				tabElm.append($("#gnbTabContAll" + tabId).html());

				gnbLayerFade( this, '.gnb .wrap-all-category' );
			});
		}, 0);
	});
}

var _isGnbLoaded = false;
$(function() {
	// HTML 방식
	//$('.link-gnb-category').off().on('click', function(){ 
	//	gnbInit();
	//});
	
	
	// JSON 방식
	//gnb_category.load();
	
	//GNB 영역 검색 버튼 클릭
	$("#gnbSearchTermBtn").on("click",DQgnbSearch);
	//GNB 영역 검색 키워드 엔터 진입
	$("#gnbSearchTerm").on("keypress",function(e){
	     var code = (e.keyCode ? e.keyCode : e.which);
	     if (code == 13) {
	    	 DQgnbSearch();
	     }
	});
	//GNB 스크롤 다운시 상단 고정
	$('.wrapper-push-nav').scrollFixedToggle();

});
//TODO 사용하지 않음!! 삭제 필요
var gnb_category = {
	categoryList: [],
	keywordList: [],
	load: function() {
		var that = this;
		$.getJSON("/common/gnb_category_list.do")
			.done(function(data) {
				if (data != null) {
					that.categoryList = data.GNB_CATEGORY;
					that.keywordList = data.GNB_KEYWORD;
					if (that.keywordList == null) {
						that.keywordList = [];
					}
					var tabId, tabName;
					$(".wrap-gnb-category .gnb-tab-nav").find("a.tab-menu").each(function(index, tab) {
						$(tab).click(function(e) {
							tabId = $(this).attr("data-gnb-tab");
							tabName = $(this).find(".tab-menu-txt").html();
							that.renderDepth1(tabId, tabName);
						});
					});
					$(".wrap-all-category .gnb-tab-nav").find("a.tab-menu").each(function(index, tab) {
						$(tab).click(function(e) {
							tabId = $(this).attr("data-gnb-tab");
							tabName = $(this).find(".tab-menu-txt").html();
							that.renderAllCategory(tabId, tabName);
						});
					});
					
					// default view
					that.renderDepth1("01", "food");
					that.renderAllCategory("01", "food");
				}
			})
		;
	},
	getCategory: function(list, categoryId) {
		var arr = $.grep(list, function(o) {
			return o.CATEGORY_ID === categoryId;
		});
		
		if (arr != null && arr.length > 0) {
			return arr[0];
		}
		return {};
	},
	renderDepth1: function(tabId, tabName) {
		var that = this;
		var template = $.templates("#GNBCategoryDepth1Template");
		var html = $.render.gnbCategoryDepth1({
			tabContId: tabId,
			tabContName: tabName,
			category: that.categoryList[tabId]
		});
		
		var tabElm = $(".wrap-gnb-category").find(".wrap-tab-cont");
		tabElm.empty();
		tabElm.append(html);
		tabElm.find(".depth1-menu > li").each(function(index, o) {
			$(o).mouseenter(function(e) {
				tabElm.find("li.active").removeClass("active");
				$(this).addClass("active");
				var category = that.getCategory(that.categoryList[tabId], $(this).find("a").attr("data-gnb-category"));
				that.renderDepth2(tabId, tabName, category);
				tabElm.find(".wrap-depth2-depth3 .depth2").show();
			});
		});
		
		$(".wrap-gnb-category").mouseleave(function(e) {
			tabElm.find(".wrap-depth2-depth3").empty();
			tabElm.find("li.active").removeClass("active");
		});
	},
	renderDepth2: function(tabId, tabName, category) {
		var that = this;
		var template = $.templates("#GNBCategoryDepth2Template");
		var html = template.render({
			tabContId: tabId,
			tabContName: tabName,
			parentCategory: category,
			keyword: that.keywordList[tabId]
		});
		
		var tabElm = $(".wrap-gnb-category").find(".wrap-tab-cont").find(".wrap-depth2-depth3");
		tabElm.empty();
		tabElm.append(html);
		tabElm.find(".depth2-menu li").each(function(index, menu) {
			$(menu).mouseenter(function(e) {
				tabElm.find("li.active").removeClass("active");
				$(this).addClass("active");
				var categoryId = $(this).find("a").attr("data-gnb-category");
				tabElm.find(".depth3.depth-active").removeClass("depth-active");
				tabElm.find("#depth3-" + categoryId).addClass("depth-active");
			});
		});
	},
	renderAllCategory: function(tabId, tabName) {
		var that = this;
		var template = $.templates("#GNBAllCategoryTemplate");
		var html = template.render({
			tabId: tabId,
			tabName: tabName,
			category: that.categoryList[tabId]
		});
		var tabElm = $(".wrap-all-category .wrap-tab-cont");
		tabElm.empty();
		tabElm.append(html);
	}
};

//검색버튼
function DQgnbSearch(){
	var keyword = $('#gnbSearchTerm').val();
	
	if( keyword === '' ) {
		alert( '검색하실 내용을 입력하세요.' );
		return false;
	}
	
	location.href="/search/search.do?&searchTerm="+encodeURIComponent(keyword.replace(/%/g, '%25').replace(/\+/g, '%2B'));		
}