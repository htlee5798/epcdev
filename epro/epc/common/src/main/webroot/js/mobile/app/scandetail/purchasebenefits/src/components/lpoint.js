import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import {
    TITLE_LPOINT,
    BTN_LPOINT,
    BTN_CLOSE,
    TITLE_LAYER_LPOINT
} from "../i18n/i18n-ko";

class Lpoint extends Component {
    constructor(props) {
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

    onToggle = (event) => {
        event.preventDefault();

        Pubsub.publish('purcahseBenefitsLayerActive', {
            type : 'lpointLayerActive',
            value : !this.state.active
        });
    };

    onClose = (event) => {
        event.preventDefault();

        Pubsub.publish('purcahseBenefitsLayerActive', {
            type : 'lpointLayerActive',
            value : false
        });
    };


    render () {
        let layerClass = 'layerpopup-type1';
        let toggleButtonClass = 'row btn-prod-benefit';

        if(this.state.html) {
            return (
                <tr  className="prod-benefit-row tdconts-btn">
                    <th scope="row">
                        {TITLE_LPOINT}
                    </th>
                    <td>
                        <div className="wrap-prod-layerpopup">
                            <button type="button"
                                    onClick={this.onToggle}
                                    className={this.state.active? 'active ' + toggleButtonClass:toggleButtonClass}>
                                {BTN_LPOINT}
                                <i className="icon-common-toggle"></i>
                            </button>
                            <article className={this.state.active?'active ' + layerClass:layerClass}>
                                <header className="header">
                                    <h3 className="title">
                                        {TITLE_LAYER_LPOINT}
                                    </h3>
                                    <button className="icon-close"
                                            onClick={this.onClose}>
                                        {BTN_CLOSE}
                                    </button>
                                </header>
                                <div dangerouslySetInnerHTML={{__html:decodeURIComponent(this.state.html)}}>
                                </div>
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

export default Lpoint;