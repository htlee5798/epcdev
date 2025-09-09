import React, {Component} from 'react';
import Swiper from 'swiper/dist/js/swiper';
import PropTypes from 'prop-types';
import getBannerImagePath from "../../cores/get-banner-image-path";
import getMobilePath from "../../cores/get-mobile-path";

class List extends Component {
    componentDidMount() {
        const {productShopRelationList} = this.props;

        if(productShopRelationList.length > 1) {
            new Swiper('.wrap-planlist-slider', {
                slidesPerView: 'auto',
                pagination: {
                    el: '.wrap-planlist-slider .swiper-pagination',
                    clickable: true
                }
            });
        }
    }

    onError = (e) => {
        e.target.src = getBannerImagePath({
            path:`/images/layout/noimg_search_plan.jpg`
        });
    }

    render () {
        const {productShopRelationList} = this.props;
        return (
            <Render onError={this.onError}
                    productShopRelationList={productShopRelationList}/>
        )
    }
}

List.propTypes = {
    productShopRelationList: PropTypes.array
};

const Render = ({productShopRelationList = [], onError = f => f}) => {
    return (
        <ul className="swiper-wrapper">
            {productShopRelationList.map((v) =>
                <li className="swiper-slide">
                    <a href={getMobilePath({url:`/mobile/plan/planDetail.do?CategoryID=${v.CATEGORY_ID}&MkdpSeq=${v.MKDP_SEQ}`})}>
                                <span className="planlist-type">
                                    {v.MKDP_CAT_NM}
                                </span>
                        <span className="planlist-title">
                                    {decodeURIComponent(v.MKDP_NM)}
                                </span>
                        <img src={getBannerImagePath({path:v.M_IMG_PATH})}
                             onError={onError}
                             alt={decodeURIComponent(v.MKDP_NM)}/>
                    </a>
                </li>
            )}
        </ul>
    )
};

Render.propTypes = {
    productShopRelationList: PropTypes.array,
    onError: PropTypes.func
};

export default List;