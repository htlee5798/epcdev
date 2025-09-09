import $ from 'jquery';
import React, {Component} from 'react';
import BtnMore from "./buttons/btn-more";
import getMobilePath from '../cores/get-mobile-path';
import {
    EMPTY_REVIEW_LIST,
    TITLE_REVIEW_LIST,
    TITLE_ZOOM_VIEW,
    TAG_EXPERIENCE,
    TAG_REVIEW,
    TOTAL_GRADE,
    REVIEW_IMAGE,
    IS_LOADING
} from "../i18n/i18n-ko";
import getErrorImagePath from "../cores/get-error-image-path";
import BtnLike from "./buttons/btn-like";
import BtnToggleReviewDetail from "./buttons/btn-toggle-review-detail";
import BtnEditReview from "./buttons/btn-edit-review";
import BtnRemoveReview from "./buttons/btn-remove-review";

class Lists extends Component {
    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    componentWillReceiveProps (nextProps) {
        if (this.state.page !== nextProps.page) {
            this.setState({
                page: nextProps.page
            });
        }
        if (this.state.totalCount !== nextProps.totalCount) {
            this.setState({
                totalCount: nextProps.totalCount
            });
        }
        if (this.state.sortType !== nextProps.sortType) {
            this.setState({
                sortType: nextProps.sortType
            });
        }
        if (this.state.reviewType !== nextProps.reviewType) {
            this.setState({
                reviewType: nextProps.reviewType
            });
        }
        if(this.state.lists !== nextProps.lists) {
            this.setState({
                lists: nextProps.lists
            });
        }

        if(this.state.isLoading !== nextProps.isLoading) {
            this.setState({
                isLoading: nextProps.isLoading
            });
        }

        if(this.state.imageRootUrl !== nextProps.imageRootUrl) {
            this.setState({
                imageRootUrl: nextProps.imageRootUrl
            });
        }

        if(this.state.isBelowGrade !== nextProps.isBelowGrade) {
            this.setState({
                isBelowGrade: nextProps.isBelowGrade
            });
        }
    }

    onError = (event) => {
        let $img = $(event.target);
        let src = getErrorImagePath(500);

        $img.attr('src', src);
    };

    onZoomView = (event) => {
        event.preventDefault();
        if($.fn.openZoomView) {
            $(event.currentTarget).openZoomView();
        }
    };

    listClass = (
        {
            file,
            useYn
        }) => {
        return `user-reply ${file.atchFileId ? 'has-image' : ''} ${!useYn ? 'blind-reply' : ''}`;
    };

    gradeStyle = (grade) => {
        return {
            width : (grade * 10) + '%'
        }
    };


    renderList = () => {
        return this.state.lists.map((v) =>
            <li className={this.listClass(v)}>
                <header className="reply-header">
                    <div className="reply-header-top">
                        <i className={v.type === 'EXPERIENCE' ? 'icon-tag-color5' : 'icon-tag-color4'}>
                            {v.type === 'EXPERIENCE' ? TAG_EXPERIENCE : TAG_REVIEW}
                        </i>
                        <span className="reply-id">
                            {v.userName}
                        </span>
                        <span className="reply-date">
                            {v.regDtByString}
                        </span>
                    </div>
                    <div className="reply-header-bottom">
                        <span className="reply-score">
                            <em className="reply-score-inner"
                                style={this.gradeStyle(v.grade)}>
                                {TOTAL_GRADE(v.grade)}
                            </em>
                        </span>
                        {v.strNm !== null &&
                        <span className="reply-branch">
                                {v.strNm}
                            </span>
                        }
                    </div>
                </header>
                <p className="reply-content">
                    {v.contents}
                </p>
                {v.file.atchFileId !== null &&
                <div className="wrap-reply-img">
                    <a href={getMobilePath({url: `/mobile/popup/scan/products/${v.prodCd}/reviews/${v.id}/image.do?type=${v.type}`})}
                       data-open-zoom-view-title={TITLE_ZOOM_VIEW}
                       onClick={this.onZoomView}>
                        <img src={this.state.imageRootUrl + v.filePath}
                             alt={REVIEW_IMAGE}
                             onError={this.onError}/>
                    </a>
                </div>
                }
                <div className="wrap-reply-btn">
                    <BtnLike id={v.id}
                             type={v.type}
                             recommCnt={v.recommCnt}
                             isRecommended={v.isRecommended}
                             {...this.state}/>
                    <BtnToggleReviewDetail/>
                </div>
                {v.isShowButton &&
                    <div className="wrap-button">
                        <BtnEditReview id={v.id} {...this.state}/>
                        <BtnRemoveReview id={v.id} {...this.state}/>
                    </div>
                }
            </li>
        );
    };

    render () {
        let currentPage = this.state.lists.length;

        return (
            <div>
                <h2 className="blind">
                    {TITLE_REVIEW_LIST}
                </h2>
                {(this.state.totalCount > 0 && !this.state.isBelowGrade) &&
                    <ul className="wrap-reply-list">
                        {this.renderList()}
                    </ul>
                }
                {((!this.state.isLoading && this.state.totalCount === 0)
                    || this.state.isBelowGrade) &&
                    <p className="list-empty">
                        {EMPTY_REVIEW_LIST}
                    </p>
                }
                {(this.state.isLoading) &&
                    <div className="list-loading">
                        <div className="more-bar">
                            <span className="spinner">
                                {IS_LOADING}
                            </span>
                        </div>
                    </div>
                }
                {(this.state.totalCount > 0 && !this.state.isBelowGrade) &&
                    <div className="wrap-button wrap-prod-more">
                        <BtnMore currentPage={currentPage}
                                 totalCount={this.state.totalCount}/>
                    </div>
                }
            </div>
        )
    }
}

export default Lists;