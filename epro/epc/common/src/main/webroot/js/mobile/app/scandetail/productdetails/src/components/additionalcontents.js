import React, {Component} from 'react';

class AdditionalContent extends Component {
    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    render () {
        return (
            <div className="wrap-gdi-serviceinfo" dangerouslySetInnerHTML={{__html:decodeURIComponent(this.state.data)}}>
            </div>
        )
    }
}

export default AdditionalContent;