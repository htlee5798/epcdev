import React, {Component} from 'react';
import {CERTIFICATION_TITLE} from "../i18n/i18n-ko";

class CertificationMark extends Component {
    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    renderContent (lists) {
        let infoGrpCd = lists[0].INFO_GRP_CD;
        let infoGrpName = lists[0].INFO_GRP_NM;
        const thClassName = infoGrpCd === 'KC004'
            ? '' : (infoGrpCd === 'KC005'
                ? 'ksc_water' : 'ksc');
        return this.state.lists.map((v) =>
            <tr>
                <th scope="row" className={thClassName}>
                    {thClassName === '' ? v.COL_NM : infoGrpName}
                </th>
                <td>
                    {thClassName === '' ? v.COL_VAL : `${v.COL_NM}  ${v.COL_VAL}`}
                </td>
            </tr>
        );
    }

    render() {
        return (
            <div className="tbl-view">
                <h3 className="title">
                    {CERTIFICATION_TITLE}
                </h3>
                <table summary={CERTIFICATION_TITLE}>
                    <caption>{CERTIFICATION_TITLE}</caption>
                    <colgroup>
                        <col width="36%"/>
                    </colgroup>
                    <tbody>
                    {this.renderContent(this.state.lists)}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default CertificationMark;