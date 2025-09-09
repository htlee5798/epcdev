import {
    BTN_REMOVE, CURRENCY_UNIT_WON,
    ERROR_MESSAGE_ONLY_NUMBER,
    ERROR_MESSAGE_PCIK_QUANTITY,
    TITLE_OPTION_SELECT
} from "../../i18n/i18n-ko";
import React, {Component} from 'react';
import setComma from "../../cores/set-comma";
import BtnMinus from '../products/btn-minus';
import BtnPlus from '../products/btn-plus';

const IME_MODE = {
    imeMode : 'disabled'
};

class RenderQuantity extends Component{
    constructor(props) {
        super(props);

        this.state = {
            quantity : this.props.quantity || this.props.minQuantity
        }
    }

    componentDidMount () {
        this.props.onTotalPrice(this.props.quantity, this.props.value);
    }

    onDecreaseQuantity = (event, quantity) => {
        event.preventDefault();

        this.updateTotalQuantity({...this.props, quantity : quantity - 1});
    };

    onIncreaseQuantity = (event, quantity) => {
        event.preventDefault();

        this.updateTotalQuantity({...this.props, quantity : quantity - 1 + 2});
    };

    setPrice () {
        return this.props.quantity * this.props.currSellPrc;
    }

	setInitQuantity(objProps) {
		if (!objProps.quantity) {
			objProps.quantity = objProps.minQuantity;
			this.setState({quantity: objProps.minQuantity});
		}
		return objProps.quantity || objProps.minQuantity;
	}

    updateTotalQuantity(
        {
            quantity,
            currSellPrc,
            minQuantity,
            maxQuantity,
            value
        }
    ) {
        if (isNaN(quantity)) {
            this.errorMessage({type: 'notNumber'});
            return;
        } else if (quantity > maxQuantity) {
            this.errorMessage({
                type: 'quantity',
                minQuantity: minQuantity,
                maxQuantity: maxQuantity
            });

            quantity = maxQuantity;

        } else if (quantity < minQuantity) {
            this.errorMessage({
                type: 'quantity',
                minQuantity: minQuantity,
                maxQuantity: maxQuantity
            });

            quantity = minQuantity;
        }

        this.setState({
            quantity
        });

        this.props.onTotalPrice(quantity, value);
    }

    errorMessage = ({
                        type,
                        minQuantity,
                        maxQuantity
                    }) => {
        switch (type) {
            case 'quantity' :
                alert(ERROR_MESSAGE_PCIK_QUANTITY(minQuantity, maxQuantity))
                break;
            case 'notNumber' :
                alert(ERROR_MESSAGE_ONLY_NUMBER);
                break;
        }
    };

    render () {
        return (
            <div className="option-select">
                <div className="goodsadded">
                    {!this.props.isOnlyOneProduct &&
                    <p>{TITLE_OPTION_SELECT(this.props)}</p>
                    }
                    <div className="wrap-spinner">
                        <div className="spinner">
                            <BtnMinus {...this.props}
                                      quantity={this.props.quantity}
                                      onDecreaseQuantity={this.onDecreaseQuantity}/>
                            <input type="tel"
                                   className="optnCnt"
                                   name="bsketQty"
                                   style={IME_MODE}
                                   maxLength={this.props.maxQuantity.length}
                                   onChange={(event) => this.updateTotalQuantity({...this.props, quantity : event.target.value})}
                                   value={this.setInitQuantity(this.props)} />
                            <BtnPlus {...this.props}
                                     quantity={this.props.quantity}
                                     onIncreaseQuantity={this.onIncreaseQuantity}/>
                        </div>
                        <p className="point1">
                            <em className="number">
                                {setComma(this.setPrice())}
                            </em>
                            {CURRENCY_UNIT_WON}
                        </p>
                    </div>
                    {!this.props.isOnlyOneProduct &&
                    <button type="button"
                            onClick={(event) => this.props.onRemoveSelected(event, this.props.value)}
                            className="icon-common-delete">
                        {BTN_REMOVE}
                    </button>
                    }
                </div>
            </div>
        );
    }
};

export default RenderQuantity;