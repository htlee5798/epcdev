import $ from 'jquery';
import React, {Component} from 'react';
import BtnLatest from "./buttons/btn-sort-latest";
import BtnRecommend from "./buttons/btn-sort-recommend";
import ReviewType from "./options/review-type";
import BtnRegistrationExperience from './buttons/btn-registration-experience';

class Sorts extends Component {
    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    componentWillReceiveProps(nextProps) {
        if(this.state.sortType !== nextProps.sortType) {
            this.setState({
                sortType: nextProps.sortType
            });
        }

        if(this.state.reviewType !== nextProps.reviewType) {
            this.setState({
                reviewType: nextProps.reviewType
            });
        }
    }

    componentDidMount () {
        $(window)
            .off('scroll.scanDetail')
            .on('scroll.scanDetail', () => {
                let offset = $('.wrap-total-score').offset().top
                    + $('.wrap-total-score').outerHeight(true)
                    - $('#header').outerHeight(true);
                if ($(window).scrollTop() > offset) {
                    $('body').addClass('sort-sticky');
                } else {
                    $('body').removeClass('sort-sticky');
                }
            });
    }

    render() {
        return (
            <section className="wrap-reply-sort">
            	<BtnRecommend sortType={this.state.sortType}/>
                <BtnLatest sortType={this.state.sortType}/>
                <ReviewType reviewType={this.state.reviewType}/>
                <BtnRegistrationExperience receivedlink={this.state.receivedlink}
                                           categoryId={this.state.CategoryID}
                                           productCd={this.state.ProductCD}/>
            </section>
        );
    }
}

export default Sorts;