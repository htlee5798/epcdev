$.templates('mobileRecipeNew',
    '{{include tmpl="mobileRecipeBestForRecipeImage"/}}' +
    '{{include tmpl="mobileRecipeBestForProducts"/}}'
);

$.templates('mobileRecipeBestForRecipeImage',
    '<div class="area-ckimg">' +
        '<a href="{{:~getLMAppUrlM()}}/mobile/recipe/mobileNewRecipeDetail.do?MNO={{:cookMethodCd}}&NNO=1&rcpNo={{:rcpNo}}">' +
            '<div class="wrap-imgbox">' +
                '<img src="{{:lmageUrl}}.jpg" alt="{{:rcpNm}}" onerror="recipeMain.image705_404(this)" />' +
                '<p class="title-cook"><span>{{:rcpNm}}</span></p>' +
            '</div>' +
            '<div class="area-summary">' +
                '<i class="icon-cook-volume"></i><span>{{:qnttNm}}</span>' +
                '<i class="icon-cook-time"></i><span>{{:cookTmNm}}</span>' +
                '<i class="icon-cook-price"></i><span>{{:cookThemeNm}}</span>' +
            '</div>' +
            	'{{if rcpVendor != null || rcpVendor not empty}}' +
		     	'<em class="tag-source">{{:rcpVendor}}</em>' +
		     	'{{/if}}' +
		     		'{{if rcpYoutubeUrl != null || rcpYoutubeUrl not empty}}'+   
		     			'<a href="{{:~getLMAppUrlM()}}/mobile/recipe/mobileNewRecipeDetail.do?MNO={{:cookMethodCd}}&NNO=1&rcpNo={{:rcpNo}}" class="btn-play-recipe">동영상</a>'+
		     		'{{/if}}' +
        '</a>' +
    '</div>'
);

$.templates('mobileRecipeBestForProducts',
    '{{if mainRecipeRecomList && mainRecipeRecomList.length > 0}}'+
    '<div class="slide-items">' +
        '<ul>' +
        '{{for mainRecipeRecomList}}' +
        '<li>'+
            '<a href="{{:~getLMAppUrlM()}}/mobile/cate/PMWMCAT0004_New.do?ProductCD={{:prodCd}}&CategoryID={{:categoryId}}">' +
                '<img class="lazy" data-src="{{:prodImgUrl}}.jpg" alt="{{:prodNm}}" onerror="recipeMain.image404(this)"/>' +
                '<span>{{:prodNm}}</span>'+
            '</a>' +
        '</li>' +
        '{{/for}}' +
        '<li class="main-special-more"><a href="{{:~getLMAppUrlM()}}/mobile/recipe/mobileNewRecipeDetail.do?MNO={{:cookMethodCd}}&NNO=1&rcpNo={{:rcpNo}}"><i class="icon-special-more"></i><span class="main-special-more-txt">더보기</span></a></li>' +
        '</ul>' +
    '</div>' +
    '{{/if}}'
);

$.templates('mobileRecipeDetailForItems',
	    '<a href="{{:~getLMAppUrlM()}}/mobile/recipe/mobileNewRecipeDetail.do?MNO={{:cookMethodCd}}&NNO=1&rcpNo={{:rcpNo}}">' +
	        '<div class="wrap-imgbox">' +
	            '<img class="lazy" ' +
	                    'data-src="{{:lmageUrl}}_278.jpg" ' +
	                    'onerror="recipeMain.image278_404(this)"' +
	                    'alt="{{:rcpNm}}"/>' +
	        '</div>'+
	        '<p class="title">{{:rcpNm}}</p>' +
	        '<ul>' +
	        	'<li>· 분량	: {{:qnttNm}}</li>'+
	            '<li>· 조리시간 : {{:cookTmNm}}</li>'+
	            '<li>· 테마 : {{:cookMethodNm}}</li>'+
	        '</ul>' +
	    '</a>' +
	    '{{if rcpVendor != null || rcpVendor not empty}}' +
	    	'<em class="tag-source" name="rcp_vendor">{{:rcpVendor}}</em>' +
	    '{{/if}}' +
	    '{{if rcpYoutubeUrl != null || rcpYoutubeUrl not empty}}' +
	    	'<a href="{{:~getLMAppUrlM()}}/mobile/recipe/mobileNewRecipeDetail.do?MNO={{:cookMethodCd}}&NNO=1&rcpNo={{:rcpNo}}" class="btn-play-recipe">동영상</a>' +
	    '{{/if}}'
	);