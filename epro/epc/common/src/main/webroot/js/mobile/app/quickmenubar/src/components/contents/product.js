import React, {Component} from 'react';
import Pubsub from "pubsub-js";
import getOptions from '../../cores/get-options';
import {
    ERROR_MESSAGE_EMPTY_TOTALQUANTITY,
    ERROR_MESSAGE_ONLY_SMARTPICKUP,
    TITLE_OPTION,
} from "../../i18n/i18n-ko";
import successAddBasket from "../../cores/success-add-basket";
import RenderQuantity from '../products/render-quantity';
import RenderOption from '../products/render-option';

const getParams = (data, categoryId, periDeliYn) => {
    if(!data && data.length === 0) {
        return [];
    }
    return data.map(d => {
        return {
            prodCd: d.productCode,
            itemCd: d.itemCode,
            bsketQty: d.quantity || d.minQuantity,
            categoryId: categoryId,
            nfomlVariation: d.variationYn === 'Y' ? '' : `${d.itemCode}:${d.quantity}`,
            periDeliYn: periDeliYn ? 'Y' : 'N',
            isScanDetail: true
        }
    });
};

const validation = (data, submitType, pType, PickUpType) => {
    let errorMessage = '';

    if(data.length === 0) {
        return ERROR_MESSAGE_EMPTY_TOTALQUANTITY;
    }

    data.some(d => {
        if (d.bsketQty === 0) {
            errorMessage = ERROR_MESSAGE_EMPTY_TOTALQUANTITY;
        } else if (submitType === 'purchase' && pType !== 'Y' && PickUpType !== '') {
            errorMessage = ERROR_MESSAGE_ONLY_SMARTPICKUP();
        }
        return d.bsketQty === 0 || (submitType === 'purchase' && pType !== 'Y' && PickUpType !== '');
    });

    return errorMessage;
};

class Product extends Component {
    constructor(props) {
        super(props);

        this.state = {
            options: [],
            subOptions : [],
            selectedOptions : [],
            isOnlyOneProduct : false,
            selectedSubOptionValue : '',
            selectedOptionValue : ''
        };

        this.selectedValue = [];
    }

    componentWillMount () {
        const promise = getOptions(this.props);
        const productInfo = this.props.productInfo;

        promise.then((options) => {
            let state = Object.assign(
                this.state,
                {
                    options,
                    selectedOptions : options.length === 0 ? [{
                        currSellPrc : productInfo.CURR_SELL_PRC,
                        isSoldOut : productInfo.ABSENCE_YN === 'Y',
                        itemCode : '001',
                        maxQuantity : productInfo.MAX_ORD_PSBT_QTY,
                        minQuantity : productInfo.MIN_ORD_PSBT_QTY,
                        name : '',
                        originalName : '',
                        productCode : productInfo.PROD_CD,
                        stockQuantity : 0,
                        value : '001',
                        variationYn : 'Y'
                    }] : [],
                    isOnlyOneProduct : options.length === 0
                }
            );

            this.setState(
                state
            );
        });
    }

    componentDidMount () {
        Pubsub.subscribe('onSubmitBasket', () => {
            let params = getParams(
                this.state.selectedOptions,
                this.props.categoryId,
                this.props.isPeriodDelivery
            );

            let errorMessage = validation(
                params,
                'basket',
                this.props.pType,
                this.props.productInfo.PickUpType
            );

            if(errorMessage !== '') {
                alert(errorMessage);
                return;
            }

            window.global.addBasket(params, function() {
                successAddBasket();
            });
        });

        Pubsub.subscribe('onSubmitBuy', () => {
            let params = getParams(
                this.state.selectedOptions,
                this.props.categoryId,
                this.props.isPeriodDelivery
            );

            let errorMessage = validation(
                params,
                'purchase',
                this.props.pType,
                this.props.productInfo.PickUpType
            );

            if(errorMessage !== '') {
                alert(errorMessage);
                return;
            }

            window.global.addDirectBasket(params);
        });
    }

    onChange = (event) => {
        let selectedOptionValue = event.target.value;
        this.setStateAsync({
            selectedSubOptionValue: '',
            subOptions : [],
            selectedOptionValue
        }).then(() => {
            let subOptions = this.state.options
                .filter(option => option.value === selectedOptionValue)
                .map(option => option.subOptions
                    && option.subOptions.map(subOption => {
                        return {
                            currSellPrc: subOption.PROM_MAX_VAL <= subOption.CURR_SELL_PRC ? subOption.PROM_MAX_VAL : subOption.CURR_SELL_PRC,
                            isSoldOut: subOption.IS_SOLD_OUT === 'true',
                            itemCode: subOption.ITEM_CD,
                            maxQuantity: subOption.MAX_ORD_PSBT_QTY,
                            minQuantity: subOption.MIN_ORD_PSBT_QTY,
                            name: subOption.name,
                            originalName: `${option.name}/${subOption.name}`,
                            productCode: subOption.PROD_CD,
                            value: subOption.value,
                            variationYn: subOption.VARIATION_YN
                        }
                    }));

            if (subOptions[0]) {
                this.setState({
                    subOptions: subOptions[0]
                });
                return;
            }

            if (selectedOptionValue === '' || this.selectedValue.indexOf(selectedOptionValue) !== -1) {
                return;
            }

            this.selectedValue.push(selectedOptionValue);

            let deepOptions = [];

            this.state.options.forEach(option => deepOptions.push(Object.assign({}, option)));


            let selectedOptions = this.selectedValue.map((value) =>
                deepOptions.filter(option => option.value === value)[0]
            );

            this.setState({
                selectedOptions
            });
        });
    };

    onSubChange = (event) => {
        let selectedSubOptionValue = event.target.value;
        this.setStateAsync({
            selectedSubOptionValue
        }).then(() => {
            if (selectedSubOptionValue === '' || this.selectedValue.indexOf(selectedSubOptionValue) !== -1) {
                return;
            }

            this.selectedValue.push(selectedSubOptionValue);

            let selectedOptions = [
                this.state.subOptions
                    .filter(option => option.value === selectedSubOptionValue)[0],
                ...this.state.selectedOptions
            ];

            this.setState({
                selectedOptions
            });
        });
    };

    setStateAsync(state) {
        return new Promise((resolve) => {
            this.setState(state, resolve);
        });
    }

    onRemoveSelected = (event, value) => {
        event.preventDefault();

        this.selectedValue.splice(this.selectedValue.indexOf(value), 1);
        let selectedOptions = this.selectedValue.map(v =>
            this.state.selectedOptions.filter(option => option.value === v)[0]
        );

        this.setStateAsync({
            selectedOptionValue : selectedOptions.length === 0 ? '' : this.state.selectedOptionValue,
            selectedOptions
        }).then(() => {
            this.onTotalPrice(0, value);

            if(this.selectedValue.length === 0 && this.state.subOptions.length !== 0) {
                this.setState({
                    selectedSubOptionValue : '',
                    subOptions: []
                });
            }
        });
    };

    onTotalPrice = (quantity, value) => {
        let totalPrice = this.state.selectedOptions.map(option => {
            if(option.value === value) {
                option.quantity = quantity;
            }
            return (option.quantity || option.minQuantity) * option.currSellPrc;
        }).reduce((accumulate, currentValue) => accumulate + currentValue, 0);

        Pubsub.publish('setTotalPrice', totalPrice);
    };

    render () {
        return (
            <div className="inner-scroll">
                <form id="formOptionForProduct">
                    {!this.state.isOnlyOneProduct &&
                        <div className="option-select">
                            <p className="title">
                                <i className="icon-common-optcheck"></i>
                                {TITLE_OPTION}
                            </p>
                            <RenderOption options={this.state.options}
                                          selectedOptionValue={this.state.selectedOptionValue}
                                          onChange={this.onChange}/>
                            <RenderOption options={this.state.subOptions}
                                          selectedOptionValue={this.state.selectedSubOptionValue}
                                          onChange={this.onSubChange}/>
                        </div>
                    }
                    {this.state.selectedOptions.map((option) =>
                        <RenderQuantity {...option}
                                        isOnlyOneProduct={this.state.isOnlyOneProduct}
                                        onTotalPrice={this.onTotalPrice}
                                        onRemoveSelected={this.onRemoveSelected}/>
                    )}
                </form>
            </div>
        );
    }
}

export default Product;