import React from 'react';
import PropTypes from 'prop-types';
import List from "./ui/List";

const App = ({html = ''}) => {
    if(html === '') {
        return null;
    }

    return (
        <List html={html}/>
    )
};

App.propTypes = {
    html: PropTypes.string
};

export default App;
