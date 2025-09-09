/* aqPaging v1.2 - Paging function with next and previous ranges.
   Copyright (C) 2009 Paul Pham <http://jquery.aquaron.com/aqPaging>

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
(function($){
$.fn.aqPaging = function (options) {
   var opts = $.extend({ },$.fn.aqPaging.defaults,options);

   return this.each(function(){
      if (opts.pages < 0) return false;

      if (!$('.aqPaging',this).length) {
         $.fn.aqPaging.defaults.uniqID++;
         $('<div class="aqPaging" id="aqPaging_'
            +$.fn.aqPaging.defaults.uniqID+'"><\/div>')
            .appendTo(this);

         $.fn.aqPaging.defaults.cbs[$.fn.aqPaging.defaults.uniqID]
            = opts.cb;

         $('.aqPaging',this).css(opts.css);
      }

      var $pager = $('.aqPaging',this);
      var pid = $pager.attr('id');

      var s = 1, e = opts.pages;
      var html = '';

      var offset = (opts.current > opts.max) ? 1 : 0;

      if (opts.pages > opts.max) {
         if (opts.current > opts.max)
            s = opts.max*parseInt((opts.current-offset)/opts.max);

         if (opts.current-offset+opts.max < opts.pages) 
            e = s + opts.max + offset;
      }

      for (var p=s; p<=e; p++)
         html += '<a href="javascript:void(0)" onclick="$.fn.aqPaging.flip(\''+pid+'\''
            +','+p+','+opts.pages+');">' + p + '<\/a> ';

      $pager.html(html);

      if (opts.current >= s && opts.current-opts.max > 0) {
         $pager.prepend('<a href="javascript:void(0)" onclick="$.fn.aqPaging.flip(\''
            +pid+'\''+',1,'+opts.pages +')">1<\/a> <i>&hellip;<\/i> ');
      }
      if ((opts.current-offset+opts.max) <= opts.pages && e != opts.pages) {
         $pager.append('<i>&hellip;<\/i>'
            +' <a href="javascript:void(0)" onclick="$.fn.aqPaging.flip(\''+pid+'\''+','
            +opts.pages+','+opts.pages +')">'+opts.pages+'<\/a> ');
      }

      var hi = ((opts.current-1)%opts.max) + ((offset+1)*offset);
      if (opts.css) {
         var _bg = $pager.css('backgroundColor');
         var _fg = $pager.css('color');
         var _bg2 = (_bg == 'transparent') ? '#fff' : _bg;
         
         $pager.find('a').css(opts.blockCss).css({
            margin: '2px', padding: '2px 5px', color: _fg,
            borderWidth: '1px', borderStyle: 'solid', borderColor: _fg 
         })
         .not(':eq('+hi+')').hover(
            function() { $(this).css({ backgroundColor: _fg, borderColor: opts.borderColor, color: _bg2 }) },
            function() { $(this).css({ backgroundColor: _bg, borderColor: _fg, color: _fg }) }
         );
         $pager.find('a').eq(hi).css({ backgroundColor: _fg, borderColor: opts.borderColor, color: _bg2 });
         $pager.find('i').css(opts.blockCss);
      } else
         $pager.find('a').removeClass('aqPagingHi')
            .eq(hi).addClass('aqPagingHi');

      if (opts.flip)
         $.fn.aqPaging.flip(pid,opts.current,opts.pages);
         
      if (opts.showSelectBox){
         //$pager.append($.fn.aqPaging.createSelectBox([50,100,200]));
      }
   });
};





$.fn.aqPaging.flip = function(id,p,total) {
   var idx = id.replace(/aqPaging_/,'');
   var func = $.fn.aqPaging.defaults.cbs[idx];
   if (func) func(p);
   $('#'+id).parent().aqPaging({current: p, pages: total});
   return false;
};

$.fn.aqPaging.defaults = {
   cbs: [], pages: 0, current: 0, max: 10, uniqID: 0, flip: false,
   css: { fontFamily: 'georgia,helvetica', padding: '0px' },
   blockCss: { display:'block', float:'left' },
   borderColor: '#444',
   showSelectBox:true
};

$.fn.aqPaging.createSelectBox = function(arr) {
    var result = " size per page<select id='_sizePerPage' name='_sizePerPage' class='filter'>";
    for(var i in arr){
        result += "<option value='" + arr[i] + "'>" + arr[i] + "</option>";
    }
    result += "</select>";
    return result;
};

})(jQuery);