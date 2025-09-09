import {connect} from 'react-redux';
import Link from './ui/Link';
import RemoveButton from "./ui/RemoveButton";
import {goToMarket, removeBanner} from "../actions";

export const NewLink = connect(
    state => ({
        isHide : state.banner.dvty !== 'MOBILE' || state.banner.isSamsung === 'Y' || !state.banner.isVisible
    }),
    dispatch => ({
        onClick(event) {
            event.preventDefault();
            dispatch(goToMarket());
        }
    })
)(Link);

export const NewRemoveButton = connect(
    state => ({
        isHide : state.banner.dvty !== 'MOBILE' || state.banner.isSamsung === 'Y' || !state.banner.isVisible
    }),
    dispatch => ({
        onClick(event) {
            event.preventDefault();
            dispatch(removeBanner());
        }
    })
)(RemoveButton);