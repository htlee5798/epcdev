import React, {Component} from 'react';
import {
    TAG_PRODUCT,
    TITLE_100_MilEAGE,
    TOTAL_REVIEW_COUNT,
    EMPTY_REVIEW_NOTICE
} from '../i18n/i18n-ko';
import setComma from '../cores/set-comma';
import BtnRegistrationExperience from "./buttons/btn-registration-experience";

const RenderButtonReview = (props) => {
    let {receivedlink, CategoryID, ProductCD} = props;
    return (
        <BtnRegistrationExperience receivedlink={receivedlink}
                                   categoryId={CategoryID}
                                   productCd={ProductCD}/>
    );
};

const RenderStarReview = (props) => {
    if(props.reviewsCount === 0 || props.isBelowGrade) {
        return null;
    }
    let perReviewAvreageGradeStyle = {
        'width':(props.reviewAvreageGrade * 10) + '%'
    };

    return (
        <span className="wrap-score">
            <em className="inner" style={perReviewAvreageGradeStyle}>
                {(props.reviewAvreageGrade).toFixed(1)}
            </em>
        </span>

    )
};

class TotalScore extends Component {
    constructor (props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    componentWillReceiveProps(nextProps) {
        if(this.state.reviewsCount !== nextProps.reviewsCount) {
            this.setState({
                reviewsCount: nextProps.reviewsCount
            });
        }
        if(this.state.reviewAvreageGrade !== nextProps.reviewAvreageGrade) {
            this.setState({
                reviewAvreageGrade: nextProps.reviewAvreageGrade
            });
        }

        if(this.state.isBelowGrade !== nextProps.isBelowGrade) {
            this.setState({
                isBelowGrade: nextProps.isBelowGrade
            });
        }
    }

    render() {
        return (
            <div className="wrap-total-score sp83">
                <h3 className="title">{TAG_PRODUCT}</h3>
                <div className="wrap-action">
                    <p className="desc">{TITLE_100_MilEAGE}</p>
                    <RenderButtonReview reviewsCount={this.state.reviewsCount}
                                        receivedlink={this.state.receivedlink}
                                        CategoryID={this.state.CategoryID}
                                        ProductCD={this.state.ProductCD}/>
                </div>
                {(this.state.reviewsCount !== 0) ? (
                <div className="product-score">
                    <p className="score">{(this.state.reviewAvreageGrade).toFixed(1)}</p>
                    <RenderStarReview reviewAvreageGrade={this.state.reviewAvreageGrade}
                                      reviewsCount={this.state.reviewsCount}
                                      isBelowGrade={this.state.isBelowGrade}/>
                    <span className="desc">
                        {TOTAL_REVIEW_COUNT(setComma(this.state.reviewsCount))}
                    </span>
                </div>
                ) : (
                <div className="product-score">
                    <p class="empty-review" dangerouslySetInnerHTML={{__html: EMPTY_REVIEW_NOTICE}}></p>
                </div>
                )}
            </div>
        );
    }
}

export default TotalScore;