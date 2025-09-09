import $ from 'jquery';
import Pubsub from "pubsub-js";
import React, {Component} from 'react';
import TotalScore from "./components/totalscore";
import {
    TITLE_REVIEWS
} from "./i18n/i18n-ko";
import ListContainer from "./components/listcontainer";
import ToastLike from "./components/toasts/like";
import ToastLikeError from "./components/toasts/like-error";
import ToastPasswordEmpty from "./components/toasts/password-empty";
import ToastPasswordWrong from "./components/toasts/password-wrong";
import getReviewList from './cores/get-review-list';
import ToastRegisterErrorIsAlready from "./components/toasts/register-error-is-already";

class App extends Component {
    static defaultProps = {
        page: 1,
        reviewType : '',
        sortType: 'RECOMM',
        isLoading: false,
        isWrote : false,
        lists: [],
        receivedlink : null
    };

    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    componentDidMount () {
        new Promise((resolve) => {
            this.setState({
                isLoading: true
            });

            resolve();
        }).then(() => {
            this.getReviewList();
        });

        Pubsub.subscribe('getMoreList', () => {
            new Promise((resolve) => {
                this.setState({
                    isLoading : true,
                    page: this.state.page + 1
                });

                resolve();
            }).then(() => {
                this.getReviewList();
            });
        });

        Pubsub.subscribe('changePage', (message, page) => {
            new Promise((resolve) => {
                this.setState({
                    page,
                    totalCount : 0,
                    isLoading: true,
                    lists : []
                });

                this.onMovePosition();

                resolve();
            }).then(() => {
                this.getReviewList();
            });
        });
        Pubsub.subscribe('changeSort', (message, type) => {
            new Promise((resolve) => {
                this.setState({
                    page: 1,
                    totalCount : 0,
                    sortType: type,
                    isLoading: true,
                    lists : []
                });

                this.onMovePosition();

                resolve();
            }).then(() => {
                this.getReviewList();
            });
        });

        Pubsub.subscribe('changeReviewType', (message, type) => {
            new Promise((resolve) => {
                this.setState({
                    page: 1,
                    totalCount : 0,
                    reviewType: type,
                    isLoading: true,
                    lists : []
                });

                this.onMovePosition();

                resolve();
            }).then(() => {
                this.getReviewList();
            });
        });
    }

    onMovePosition = () => {
        if($('body').hasClass('sort-sticky')) {
            $('html, body').scrollTop($('.wrap-total-score').offset().top
                + $('.wrap-total-score').outerHeight(true)
                - $('#header').outerHeight(true));
        }
    };

    getReviewList = () => {
        getReviewList(this.state)
            .then((
                {
                    imageRootUrl,
                    reviews = [],
                    totalCount = 0,
                    reviewAvreageGrade,
                    reviewsCount,
                    isBelowGrade = true
                }
            ) => {
                let lists = this.state.lists;

                for(let item of reviews) {
                    lists.push(item);
                }
                this.setState({
                    isLoading: false,
                    imageRootUrl,
                    lists,
                    totalCount,
                    reviewAvreageGrade,
                    reviewsCount,
                    isBelowGrade
                });
            });
    };

    render() {
        const {
            reviewsCount,
            reviewAvreageGrade,
            isBelowGrade,
            productInfo,
            CategoryID,
            ProductCD
        } = this.state;

        const {
            page,
            totalCount,
            sortType,
            reviewType,
            lists,
            isLoading,
            imageRootUrl,
            isWrote,
            receivedlink
        } = this.state;

        const {
            SELLER_RECOMM
        } = this.state.productInfo;

        return (
            <div>
                <h2 className="blind">
                    {TITLE_REVIEWS}
                </h2>
                <TotalScore reviewsCount={reviewsCount}
                            reviewAvreageGrade={reviewAvreageGrade}
                            isBelowGrade={isBelowGrade}
                            SELLER_RECOMM={SELLER_RECOMM}
                            productInfo={productInfo}
                            CategoryID={CategoryID}
                            ProductCD={ProductCD}
                            isWrote={isWrote}
                            receivedlink={receivedlink}/>
                <ListContainer ProductCD={ProductCD}
                               reviewAvreageGrade={reviewAvreageGrade}
                               isBelowGrade={isBelowGrade}
                               page={page}
                               totalCount={totalCount}
                               sortType={sortType}
                               reviewType={reviewType}
                               lists={lists}
                               isLoading={isLoading}
                               receivedlink={receivedlink}
                               imageRootUrl={imageRootUrl}/>
                <ToastLike/>
                <ToastLikeError/>
                <ToastPasswordEmpty/>
                <ToastPasswordWrong/>
                <ToastRegisterErrorIsAlready/>
            </div>
        );
    }
}

export default App;
