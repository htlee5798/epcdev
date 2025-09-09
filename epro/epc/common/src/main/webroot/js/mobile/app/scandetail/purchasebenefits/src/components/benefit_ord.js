import $ from 'jquery';
import React,{Component} from 'react';
import Pubsub from "pubsub-js";

class BenefitOrd extends Component {
    static defaultProps = {
        strHtml : ''
    }
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

    componentDidMount () {
        this.setState({
            strHtml: $('[data-layer="goodsDetailsPop7"]').find('tbody').html()
        });

    }

    onToggle = (event) => {
        event.preventDefault();

        Pubsub.publish('purcahseBenefitsLayerActive', {
            type : 'benefitOrdLayerActive',
            value : !this.state.active
        });
    };

    onClose = (event) => {
        event.preventDefault();

        Pubsub.publish('purcahseBenefitsLayerActive', {
            type : 'benefitOrdLayerActive',
            value : false
        });
    };

    render () {
        let layerClass = 'layerpopup-type1 layerpopup-type1-ver2';
        let toggleButtonClass = 'row btn-prod-benefit';

        return (
            <tr id="benefitTr"
                className="prod-benefit-row tdconts-btn">
                <th scope="row">구매혜택</th>
                <td id="benefitTag">
                    <div className="wrap-prod-layerpopup">
                        <button type="button"
                                onClick={this.onToggle}
                                className={this.state.active? 'active ' + toggleButtonClass:toggleButtonClass}
                                id='benefitTagTit'>
                        </button>
                        <article className={this.state.active?'active ' + layerClass:layerClass}>
                            <header className="header">
                                <h3 className="title">구매 혜택 안내</h3>
                                <button className="icon-close"
                                        onClick={this.onClose}>
                                    닫기
                                </button>
                            </header>
                            <table>
                                <caption>구매혜택</caption>
                                <colgroup>
                                    <col width="36%"/>
                                    <col/>
                                </colgroup>
                                <tbody dangerouslySetInnerHTML={{__html:this.state.strHtml}}></tbody>
                            </table>
                        </article>
                    </div>
                </td>
            </tr>
        );
    }
}

export default BenefitOrd;