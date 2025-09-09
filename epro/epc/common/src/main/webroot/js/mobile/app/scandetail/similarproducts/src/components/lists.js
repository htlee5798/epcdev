import $ from 'jquery';
import React, {Component} from 'react';
import getMobilePath from '../cores/get-mobile-path';
import getErrorImagePath from '../cores/get-error-image-path';
import getImagePath from '../cores/get-image-path';
import setComma from '../cores/set-comma';
import {
    IMAGE_ALT_SOLUT_OUT
} from "../i18n/i18n-ko";

class List extends Component {
    constructor(props) {
        super(props);

        this.state = {
            lists: props.lists
        };
    }

    onError = (event) => {
        let src = getErrorImagePath(208);

        $(event.target).attr('src', src);
    };

    renderImage = (
        {
            MD_SRCMK_CD,
            PROD_NM,
            ADL_YN = 'N',
            SOUT_YN_REAL = 'N'
        }) => {
        let imageSrc = `/${MD_SRCMK_CD.substring(0, 5)}/${MD_SRCMK_CD}_1_208.jpg`;
        let imageAlt = `${PROD_NM}`;

        if($.utils.config('LMAdultPccvYn') !== 'Y' && SOUT_YN_REAL === 'Y') {
            imageSrc = '/images/layout/m-goods-adult.png';
            imageAlt = '19';
        } else if(SOUT_YN_REAL === 'Y') {
            imageSrc = '/images/layout/img_soldout_208x208.png';
            imageAlt = IMAGE_ALT_SOLUT_OUT;
        }

        return (
            <img src={getImagePath(imageSrc)}
                 onError={this.onError}
                 alt={imageAlt}/>
        );
    };

    renderList = () => {
        return this.state.lists.map((v) =>
            <li>
                <a href={getMobilePath({url:`/mobile/scan/products/${v.PROD_CD}/detail.do`})}>
                    <div className="thumbnail">
                        {this.renderImage(v)}
                    </div>
                    <div className="info">
                        <p className="title">
                            {v.PROD_NM}
                        </p>
                        <p className="price">
                            {v.PROM_MAX_VAL < v.CURR_SELL_PRC &&
                                <del className="number">
                                    {setComma(v.CURR_SELL_PRC)}
                                </del>
                            }
                            <strong className="number">
                                {setComma(v.PROM_MAX_VAL)}
                            </strong>
                        </p>
                    </div>
                </a>
            </li>
        );
    };

    render () {
        if(this.state.lists) {
            return (
                <ul className="goods-list">
                    {this.renderList()}
                </ul>
            );
        } else {
            return null;
        }
    }
}

export default List;