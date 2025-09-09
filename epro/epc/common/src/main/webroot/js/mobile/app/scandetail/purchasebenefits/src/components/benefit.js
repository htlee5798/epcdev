import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import {
    BTN_CLOSE,
    TITLE_BENEFIT
} from "../i18n/i18n-ko";
import benefitIcons from '../const/benefit-icons';

class Benefit extends Component {
    static defaultProps = {
        icons: [],
        isShow: false
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

    componentWillMount () {
        let icons = [];

        for(let promotion of this.state.promotions) {
            let benefitIcon = benefitIcons.filter((v) => {
                return v.promoType === promotion.promoType;
            });

            if(benefitIcon.length > 0) {
                icons.push(benefitIcon[0]);
            }
        }

        this.setState({
            icons,
            isShow : icons.length > 0
        });
    }

    onToggle = (event) => {
        event.preventDefault();

        Pubsub.publish('purcahseBenefitsLayerActive', {
            type: 'benefitLayerActive',
            value : !this.state.active
        });
    };

    onClose = (event) => {
        event.preventDefault();

        Pubsub.publish('purcahseBenefitsLayerActive', {
            type: 'benefitLayerActive',
            value : false
        });
    };

    renderBenefitIcon = () => {
        return this.state.icons.map((v) =>
            <em className="prod-icon type2">{v.title}</em>
        );
    };

    renderBenefitLayer = () => {
        return this.state.icons.map((v) =>
            <tr className="gray">
                <th scope="row">{v.title}</th>
                <td>{v.desc}</td>
            </tr>
        );
    };

    render () {
        if(this.state.isShow) {
            let layerClass = 'layerpopup-type1 layerpopup-type1-ver2';
            let toggleButtonClass = 'row btn-prod-benefit';

            return (
                <tr className="prod-benefit-row">
                    <th>{TITLE_BENEFIT}</th>
                    <td>
                        <div className="wrap-prod-layerpopup">
                            <button type="button"
                                    className={this.state.active? 'active ' + toggleButtonClass:toggleButtonClass}
                                    onClick={this.onToggle}>
                                {this.renderBenefitIcon()} <i className="icon-common-toggle"></i>
                            </button>
                            <article className={this.state.active?'active ' + layerClass:layerClass}>
                                <header className="header">
                                    <h3 className="title">
                                    구매 혜택 안내
                                    </h3>
                                    <button type="button"
                                            onClick={this.onClose}
                                            className="icon-close">
                                        {BTN_CLOSE}
                                    </button>
                                </header>
                                <table>
                                    <caption>구매혜택</caption>
                                    <colgroup>
                                        <col width="36%"/>
                                        <col/>
                                    </colgroup>
                                    <tbody>
                                        {this.renderBenefitLayer()}
                                    </tbody>
                                </table>
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

export default Benefit;