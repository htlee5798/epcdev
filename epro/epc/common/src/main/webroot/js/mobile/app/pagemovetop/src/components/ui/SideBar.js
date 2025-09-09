import React, {Component} from 'react';
import PropTypes from 'prop-types';
import ButtonZoom from "./ButtonZoom";
import ButtonTop from "./ButtonTop";

import $ from 'jquery';

class SideBar extends Component {
    componentDidMount() {
        let windowHeight = $(window).height();
        let {onScroll} = this.props;

        $(window).on('scroll', function () {
            let scrollTop = $(window).scrollTop();
            onScroll(windowHeight, scrollTop);
        });
    }

    onZoom = (event) => {
        event.preventDefault();

        if ($.fn.openZoomView) {
            $(event.currentTarget).openZoomView();
        }
    };

    onTop = (event) => {
        event.preventDefault();

        $('html, body').animate({scrollTop: 0}, 300);
    };

    render () {
        const {
            mdSrcmkCd,
            categoryId,
            active
        } = this.props;

        let layerClass = `pagemove-bar ${active ? "enable" : ""}`;

        return (
            <div className={layerClass}>
                <ButtonZoom mdSrcmkCd={mdSrcmkCd}
                            categoryId={categoryId}
                            onZoom={this.onZoom}/>
                <ButtonTop onTop={this.onTop}/>
            </div>
        )
    }
}

SideBar.propTypes = {
    mdSrcmkCd: PropTypes.string,
    categoryId: PropTypes.string,
    active: PropTypes.bool,
    onScroll : PropTypes.func
};

export default SideBar;