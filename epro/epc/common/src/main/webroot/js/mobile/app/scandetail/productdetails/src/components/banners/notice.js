import React, {Component} from 'react';
import getBannerImagePath from '../../cores/get-banner-image-path';

const RenderNotice = (props) => {
    if(props.lists.length === 0 || props.error404) {
        return null;
    }

    return props.lists.map((v) => props.renderBanner(v));
};

class BannerNotice extends Component {
    static defaultProps = {
        error404: false,
        lists: []
    };

    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    onError = () => {
        this.setState({
            error404: true
        });
    };

    renderContents = (v) => {
        return (
            <div className="prod-dbanner">
                <p>
                    <img src={getBannerImagePath({path :v.BNR_PATH})}
                         onError={this.onError}
                         alt={v.BNR_ALT}/>
                </p>
            </div>
        )
    }

    renderBanner = (v) => {
        if(v.LINK_URL !== '') {
            return (
                <a href={v.LINK_URL}>
                    {this.renderContents(v)}
                </a>
            )
        } else {
            return this.renderContents(v);
        }
    }

    render() {
        return (
            <RenderNotice {...this.state}
                          renderBanner={this.renderBanner}/>
        )
    }
}

export default BannerNotice;