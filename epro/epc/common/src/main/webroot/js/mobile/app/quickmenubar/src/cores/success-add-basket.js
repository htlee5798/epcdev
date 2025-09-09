import $ from 'jquery';
import getBasketCount from './get-basket-count';

const successAddBasket = (basketCount) => {
    let $basketCount = $('#basketCountData');

    if($basketCount.length === 0) {
        return;
    }

    let $basketToast = $('<p class="basket-toastpopup"></p>').appendTo('body');

    setTimeout(() => {
        $basketToast
            .addClass('action')
            .on('transitionend webkitTransitionEnd', function() {
                getBasketCount();
                $basketToast.remove();
            });
    }, 500);
};

export default successAddBasket;