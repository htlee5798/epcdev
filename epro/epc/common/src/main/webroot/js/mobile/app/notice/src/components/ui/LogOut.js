import React from 'react';
import PropTypes from 'prop-types';
import Content from "./Content";
import ButtonClose from "./ButtonClose";

const LogOut = ({
                   forceStrCd,
                   STR_CD,
                   forceStrNm,
                   LoginYN,
                   active,
                   onClick=f=>f
               }) => {
    if (forceStrCd === ''
        || LoginYN === 'Y'
        || (forceStrCd !== '' && forceStrCd === STR_CD)) {
        return null;
    }

    let layerClassName = `fixed-center-layer target-location ${active ? 'active' : ''}`;

    return (
        <div className={layerClassName}>
            <div className="layer-inner">
                <Content forceStrCd={forceStrCd}
                         forceStrNm={forceStrNm}
                         LoginYN={LoginYN}
                         active={active}/>
                <ButtonClose onClick={onClick}/>
            </div>
        </div>
    );
};

LogOut.propTypes = {
    forceStrCd : PropTypes.string,
    STR_CD : PropTypes.string,
    forceStrNm : PropTypes.string,
    LoginYN : PropTypes.string,
    active : PropTypes.bool,
    onClick:PropTypes.func
};

export default LogOut;