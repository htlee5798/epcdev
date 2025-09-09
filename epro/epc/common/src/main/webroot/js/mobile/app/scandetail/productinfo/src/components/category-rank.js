import React, {Component} from 'react';
import {
    TITLE_RANK
} from "../i18n/i18n-ko";

class CategoryRank extends Component {
    static defaultProps = {
        itemName: '',
        rankInfo: ''
    };

    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    render () {
        return (
            <p className="category-rank">
                <span className="point2">
                    {this.state.itemName}
                </span> 카테고리 <strong className="point1">
                    {TITLE_RANK(this.state.rankInfo)}
                </strong>
            </p>
        )
    }
}

export default CategoryRank;