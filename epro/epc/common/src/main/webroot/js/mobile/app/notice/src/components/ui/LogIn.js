import React from 'react';
import PropTypes from 'prop-types';
import Content from "./Content";
import Description from "./Description";
import ButtonClose from "./ButtonClose";

const LogIn = ({
                   forceStrCd,
                   STR_CD,
                   forceStrNm,
                   LoginYN,
                   active,
                   onClick=f=>f
}) => {
    if (forceStrCd === ''
        || LoginYN === 'N'
        || (forceStrCd !== '' && forceStrCd === STR_CD)) {
        return null;
    }

    let layerClassName = `fixed-center-layer location-info ${active ? 'active' : ''}`;

    return (
        <div className={layerClassName}>
            <div className="layer-inner">
                <Content forceStrCd={forceStrCd}
                         forceStrNm={forceStrNm}
                         LoginYN={LoginYN}
                         active={active}/>
                <Description forceStrCd={forceStrCd}
                             forceStrNm={forceStrNm}
                             LoginYN={LoginYN}/>
                <ButtonClose onClick={onClick}/>
            </div>
        </div>
    );
};

LogIn.propTypes = {
    forceStrCd : PropTypes.string,
    STR_CD : PropTypes.string,
    forceStrNm : PropTypes.string,
    LoginYN : PropTypes.string,
    active : PropTypes.bool,
    onClick:PropTypes.func
};

export default LogIn;