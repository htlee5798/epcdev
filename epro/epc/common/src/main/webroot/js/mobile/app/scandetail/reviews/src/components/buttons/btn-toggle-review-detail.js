import $ from 'jquery';
import React, {Component} from 'react';
import {
    BTN_TOGGLE_REVIEW_DETAIL
} from '../../i18n/i18n-ko';

class BtnToggleReviewDetail extends Component{
    onToggle = (event) => {
        event.preventDefault();

        let $target = $(event.target);
        let $li = $target.closest('.user-reply');
        let position = $li.offset().top - $('#header').outerHeight() - $('.wrap-reply-sort').outerHeight();

        $li.toggleClass('active');

        if($('body').hasClass('sort-sticky')) {
            $('html, body').stop()
                .animate({
                    scrollTop: position
                }, {
                    duration: 200,
                    ease: 'ease-in-out'
                });
        }
    }

    render () {
        return (
            <button type="button"
                    onClick={this.onToggle}
                    className="btn-prod-toggle">
                {BTN_TOGGLE_REVIEW_DETAIL}
            </button>
        )
    }
}

export default BtnToggleReviewDetail;