import React, {Component} from 'react';
import Lists from "./lists";
import LayerpopupPassword from "./layerpopups/password";
import Sorts from "./sorts";

class ListContainer extends Component {
    constructor(props) {
        super();

        this.state = Object.assign({}, props);
    }

    componentWillReceiveProps(nextProps) {
        if (this.state.page !== nextProps.page) {
            this.setState({
                isLoading :true,
                page: nextProps.page
            });
        }
        if (this.state.totalCount !== nextProps.totalCount) {
            this.setState({
                isLoading :true,
                totalCount: nextProps.totalCount
            });
        }
        if (this.state.sortType !== nextProps.sortType) {
            this.setState({
                isLoading :true,
                sortType: nextProps.sortType
            });
        }
        if (this.state.reviewType !== nextProps.reviewType) {
            this.setState({
                isLoading :true,
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

        if(this.state.reviewAvreageGrade !== nextProps.reviewAvreageGrade) {
            this.setState({
                reviewAvreageGrade: nextProps.reviewAvreageGrade
            });
        }
    }

    render () {
        const {
            sortType,
            reviewType,
            ProductCD,
            receivedlink,
        } = this.state
        return (
            <div>
                {!this.state.isBelowGrade &&
                    <Sorts sortType={sortType}
                           reviewType={reviewType}
                           receivedlink={receivedlink}
                           ProductCD={ProductCD}/>
                }
                <section className="wrap-reply">
                    <Lists {...this.state}/>
                </section>
                <LayerpopupPassword/>
            </div>
        );
    }
}

export default ListContainer;