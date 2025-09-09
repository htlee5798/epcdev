import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import {
    TITLE_BENEFIT_CARD,
    BTN_BENEFIT_CARD,
    BTN_CLOSE
} from "../i18n/i18n-ko";
import convertCardHtml from '../cores/convert-card-html';

class BenefitCard extends Component {
    static defaultProps = {
        cardHtml: ''
    };

    constructor (props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    componentWillReceiveProps (nextPros) {
        if(this.state.active !== nextPros.active) {
            this.setState({
                active: nextPros.active
            });
        }
    }

    componentWillMount() {
        let cardHtml = convertCardHtml(decodeURIComponent(this.state.CCARD_NEW));

        this.setState({
            cardHtml
        });
    }

    onToggle = (event) => {
        event.preventDefault();

        Pubsub.publish('purcahseBenefitsLayerActive', {
            type : 'benefitCardLayerActive',
            value : !this.state.active
        });
    };

    onClose = (event) => {
        event.preventDefault();

        Pubsub.publish('purcahseBenefitsLayerActive', {
            type : 'benefitCardLayerActive',
            value : false
        });
    };

    render () {
        let layerClass = 'layerpopup-type1 layerpopup-type1-ver2';
        let toggleButtonClass = 'row btn-prod-benefit';

        if(this.state.cardHtml !== '') {
            return (
                <tr className="prod-benefit-row tdconts-btn">
                    <th scope="row">{TITLE_BENEFIT_CARD}</th>
                    <td>
                        <div className="wrap-prod-layerpopup">
                            <button type="button"
                                    className={this.state.active? 'active ' + toggleButtonClass:toggleButtonClass}
                                    id="partnership-iscount"
                                    onClick={this.onToggle}>
                                {BTN_BENEFIT_CARD} <i className="icon-common-toggle"></i>
                            </button>
                            <article className={this.state.active?'active ' + layerClass:layerClass}>
                                <header className="header">
                                    <h3 className="title">
                                        {BTN_BENEFIT_CARD}
                                    </h3>
                                    <button type="button"
                                            onClick={this.onClose}
                                            className="icon-close">
                                        {BTN_CLOSE}
                                    </button>
                                </header>
                                <div className="cardsales-list" dangerouslySetInnerHTML={{__html:this.state.cardHtml}}></div>
                            </article>
                        </div>
                    </td>
                </tr>
            );
        } else {
            return null;
        }
    }
}

export default BenefitCard;