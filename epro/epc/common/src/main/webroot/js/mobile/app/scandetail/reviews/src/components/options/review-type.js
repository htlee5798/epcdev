import $ from 'jquery';
import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import {
    REVIEW_TYPE_ALL,
    REVIEW_TYPE_EXPERIENCE,
    REVIEW_TYPE_REVIEW
} from "../../i18n/i18n-ko";

const DEFAULT_OPTIONS = [{
    name : REVIEW_TYPE_ALL,
    value : ''
}, {
    name : REVIEW_TYPE_REVIEW,
    value : 'REVIEW'
}, {
    name : REVIEW_TYPE_EXPERIENCE,
    value : 'EXPERIENCE'
}];

class ReviewType extends Component {
    static defaultProps = {
        options : DEFAULT_OPTIONS
    };

    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    onChangeReviewType = (event) => {
        Pubsub.publish('changeReviewType', $(event.target).val());
    };

    render () {
        return (
            <div className="reply-sort-select">
                <select name="reviewType"
                        onChange={this.onChangeReviewType}>
                    {this.state.options.map((v) =>
                        <option value={v.value}
                                selected={v.value === this.state.reviewType}>
                            {v.name}
                        </option>)}
                </select>
            </div>
        )
    }
}

export default ReviewType;