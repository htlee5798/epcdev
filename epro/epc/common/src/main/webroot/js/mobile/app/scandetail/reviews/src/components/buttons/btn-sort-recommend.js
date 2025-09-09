import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import {BTN_RECOMMEND} from "../../i18n/i18n-ko";

class BtnRecommend extends Component {
    static defaultProps = {
        value: 'RECOMM'
    };

    constructor (props) {
        super();

        this.state = Object.assign({}, props);
    }

    componentWillReceiveProps(nextProps) {
        if(this.state.sortType !== nextProps.sortType) {
            this.setState({
                sortType: nextProps.sortType
            });
        }
    }

    onClick = (event) => {
        event.preventDefault();
        Pubsub.publish('changeSort', this.state.value);
    };

    render () {
        return (
            <button type="button"
                    className={this.state.sortType === this.state.value ? "reply-sort-btn active" : 'reply-sort-btn'}
                    value={this.state.value}
                    disabled={this.state.sortType === this.state.value}
                    onClick={this.onClick}>
                {BTN_RECOMMEND}
            </button>
        )
    }
}

export default BtnRecommend;