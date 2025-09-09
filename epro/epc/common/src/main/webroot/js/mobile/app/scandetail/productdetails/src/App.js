import $ from 'jquery';
import React, { Component } from 'react';
import {
    PRODUCT_DETAIL_TITLE,
    PRODUCT_DETAIL_DESC,
    PRODUCT_INFORMATION,
    SALES_CODE
} from "./i18n/i18n-ko";
import BannerNotice from "./components/banners/notice";
import Informations from "./components/informations";
import CertificationMark from "./components/certificationmark";
import BannerVendors from "./components/banners/vendors";
import ViewOriginal from "./components/vieworiginal";
import SnapShot from "./components/snapshot";
import AdditionalContent from "./components/additionalcontents";
import BtnMoreBrand from "./components/buttons/btn-more-brand";

class App extends Component {
    constructor (props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    onClick = (event) => {
        let $target = $(event.target);

        if($target.is('button, a')) {
            return false;
        }

        let promise = new Promise((resolve) => {
            let top = $('#informationDetails').offset().top;
            let scrollTop = $(window).scrollTop();

            if(scrollTop > top) {
                $('html, body').scrollTop(top - $('#header').outerHeight(true));
            }
            resolve();
        });

        promise.then(() => {
            $('#informationDetails').removeAttr('open');
        });
    };

    render() {
        return (
            <div>
                <h2 class="blind">
                    {PRODUCT_DETAIL_TITLE}
                </h2>
                <details className="wrap-toggle-information" id="informationDetails" open={true}>
                    <summary className="title">
                        {PRODUCT_DETAIL_TITLE}
                    </summary>
                    <div onClick={this.onClick}>
                        <BannerNotice lists={this.state.ProdBnrList}/>
                        <div className="prod-information">
                            <p className="goods-detail-desc">
                                {PRODUCT_DETAIL_DESC}
                            </p>
                            <h3 className="title">
                                {PRODUCT_INFORMATION}
                            </h3>
                            <div className="wrap-toggle">
                                <div className="tbl-view">
                                    <table>
                                        <caption>{PRODUCT_INFORMATION}</caption>
                                        <colgroup>
                                            <col width="28%"/>
                                            <col/>
                                        </colgroup>
                                        <tbody>
                                        {this.state.ProductInfo.MD_SRCMK_CD &&
                                        <tr>
                                            <th scope="row">
                                                {SALES_CODE}
                                            </th>
                                            <td>
                                                {this.state.ProductInfo.MD_SRCMK_CD}
                                            </td>
                                        </tr>
                                        }
                                        <Informations lists={this.state.productDetailList}/>
                                        </tbody>
                                    </table>
                                </div>
                                {(this.state.certificationMark && this.state.certificationMark.length > 0) &&
                                <CertificationMark lists={this.state.certificationMark}/>
                                }
                                <BannerVendors lists={this.state.VendorBnrList}/>
                                {this.state.ADD_DESC &&
                                <ViewOriginal productCd={this.state.ProductInfo.PROD_CD}
                                              categoryId={this.state.ProductInfo.CategoryID}/>
                                }
                                {(this.state.ADD_DESC_QS_LIST && this.state.ADD_DESC_QS_LIST.length > 0) &&
                                <SnapShot productName={this.state.ProductInfo.PROD_NM}
                                          lists={this.state.ADD_DESC_QS_LIST}/>
                                }
                                {this.state.additionalInfoContents &&
                                <AdditionalContent data={this.state.additionalInfoContents}/>
                                }
                                {this.state.ProductInfo.BRAND_NM &&
                                <BtnMoreBrand brandName={this.state.ProductInfo.BRAND_NM}/>
                                }
                            </div>
                        </div>
                    </div>
                </details>
            </div>
        );
    }
}

export default App;
