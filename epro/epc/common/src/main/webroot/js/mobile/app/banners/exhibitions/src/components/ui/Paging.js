import React from "react";
import PropTypes from 'prop-types';

const Paging = ({productShopRelationList = []}) => {
    if (productShopRelationList.length > 1) {
        return null;
    }

    return (
        <div className="swiper-pagination"></div>
    );
};


Paging.propTypes = {
    productShopRelationList: PropTypes.array
};

export default Paging;