import React from 'react';
import PropTypes from 'prop-types';
import getMobilePath from "../../cores/get-mobile-path";
import {BTN_IMAGE_ZOOM} from "../../i18n/i18n-ko";

const ButtonZoom = ({mdSrcmkCd, categoryId, onZoom=f=>f}) =>
    <a href={
        getMobilePath({url: `/mobile/popup/mobileProductDesc.do?ProductCD=${mdSrcmkCd}&CategoryID=${categoryId}`})
    }
       onClick={onZoom}
       className="topadded-btn">
        <i className="icon-common-search">{BTN_IMAGE_ZOOM}</i>
    </a>

ButtonZoom.propTypes = {
    mdSrcmkCd: PropTypes.string,
    categoryId: PropTypes.string,
    onZoom: PropTypes.func
};

export default ButtonZoom;