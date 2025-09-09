import React from "react";
import PropTypes from 'prop-types';

const List = ({html = ''}) =>
    <div dangerouslySetInnerHTML={{__html : decodeURIComponent(decodeURIComponent(html))}}></div>

List.propTypes = {
    html: PropTypes.string
};

export default List;