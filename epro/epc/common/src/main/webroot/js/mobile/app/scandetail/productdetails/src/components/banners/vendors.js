import React, {Component} from 'react';

const RenderBannerVendors = (props) => {
    if(props.lists.length === 0) {
        return null;
    }

    return (
        <div className="wrap-gdi-banner">
            {props.lists.map((v) =>
                <p>
                    {v.BNR_CONTENT}
                </p>
            )}
        </div>
    );
}

class BannerVendors extends Component {
    static defaultProps = {
        lists: []
    };

    constructor (props) {
        super(props);

        this.state = Object.assign({}, props);
    }
    render () {
        return (
            <RenderBannerVendors {...this.state} />
        );
    }
}

export default BannerVendors;