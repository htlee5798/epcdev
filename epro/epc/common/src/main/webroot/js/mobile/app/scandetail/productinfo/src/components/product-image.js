import $ from 'jquery';
import React, {Component} from 'react';
import getMobilePath from '../cores/get-mobile-path';
import getImagePath from "../cores/get-image-path";
import getErrorImagePath from "../cores/get-error-image-path";

class ProductImage extends Component {
    static defaultProps = {
        PROD_NM: '',
        imgQty: 1
    };

    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    getLink = ({MD_SRCMK_CD, CategoryID, imgQty}) => {
        let link = `/mobile/popup/mobileProductOrgImg.do?ProductCD=${MD_SRCMK_CD}&CategoryID=${CategoryID}&qty=${imgQty}&imgIdx=1`;

        return getMobilePath(link);
    };

    getImagePath = ({MD_SRCMK_CD}) => {
        let path = `/${MD_SRCMK_CD.substring(0, 5)}/${MD_SRCMK_CD}_1_200.jpg`;
        return getImagePath(path);
    };

    onClick = (event) => {
        event.preventDefault();
    };


    onError = (event) => {
        let $img = $(event.target);
        let src = getErrorImagePath(500);

        $img.attr('src', src);
    };

    render() {
        return (
            <a href="#"
               onClick={this.onClick}
               className="wrap-prod-thumbnail">
                <img src={this.getImagePath(this.state)}
                     onError={this.onError}
                     alt={decodeURIComponent(this.state.PROD_NM)}/>
            </a>
        );
    }

}

export default ProductImage;