import $ from 'jquery';

const actionMaterial = (event) => {
    let $el = $(event.currentTarget);
    let offset = $el.offset();
    let $layer = $('.mat-layer');
    let posX = event.pageX - offset.left;
    let posY = event.pageY - offset.top;

    if ($layer.length > 0) {
        return;
    }

    $layer = $('<i class="mat-layer"></i>').appendTo($el);
    $el.css('overflow', 'hidden');

    let promise = new Promise((resolve) => {
        setTimeout(function () {
            $layer
                .css({'top': posY, 'left': posX})
                .addClass('active')
                .on('transitionend -webkit-transitionend', function() {
                    $(this).remove();
                    resolve();
                });
            $el
                .closest('.product-item')
                .addClass('linked')
                .siblings()
                .removeClass('linked');
        }, 50);
    });

    return promise;
};

export default actionMaterial;