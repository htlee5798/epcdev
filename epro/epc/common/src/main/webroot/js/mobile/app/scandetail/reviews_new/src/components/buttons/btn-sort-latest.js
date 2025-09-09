import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import {BTN_LATEST} from "../../i18n/i18n-ko";

class BtnLatest extends Component {
    static defaultProps = {
        value: 'DATE'
    };

    constructor(props) {
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

    render() {
        return (
            <button type="button"
                    name={this.state.name}
                    className={this.state.sortType === this.state.value ? "reply-sort-btn active" : 'reply-sort-btn'}
                    value={this.state.value}
                    disabled={this.state.sortType === this.state.value}
                    onClick={this.onClick}>
                {BTN_LATEST}
            </button>
        );
    }
}

export default BtnLatest;