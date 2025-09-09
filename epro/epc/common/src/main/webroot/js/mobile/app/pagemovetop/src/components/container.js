import {connect} from 'react-redux';
import SideBar from "./ui/SideBar";
import {
    setActive
} from '../actions';

export const SideBarContainer = connect(
    state =>
        ({
            mdSrcmkCd : state.sideBar.mdSrcmkCd,
            categoryId : state.sideBar.categoryId,
            active: state.sideBar.active
        }),
    dispatch =>
        ({
            onScroll(windowHeight, scrollTop) {
                dispatch(setActive(windowHeight, scrollTop));
            }
        })
)(SideBar);