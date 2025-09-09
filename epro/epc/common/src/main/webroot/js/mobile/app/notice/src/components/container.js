import {connect} from 'react-redux';
import {onCloseToast} from "../actions";
import LogIn from "./ui/LogIn";
import LogOut from "./ui/LogOut";

export const LogInContainer = connect(
    state =>
        ({
            forceStrCd : state.notice.forceStrCd,
            STR_CD : state.notice.STR_CD,
            forceStrNm : state.notice.forceStrNm,
            LoginYN : state.notice.LoginYN,
            active : state.notice.active
        }),
    dispatch =>
        ({
            onClick(event) {
                event.preventDefault();
                dispatch(onCloseToast());
            }
        })
)(LogIn);

export const LogOutContainer = connect(
    state =>
        ({
            forceStrNm : state.notice.forceStrNm,
            forceStrCd : state.notice.forceStrCd,
            LoginYN : state.notice.LoginYN,
            active : state.notice.active
        }),
    dispatch =>
        ({
            onClick(event) {
                event.preventDefault();
                dispatch(onCloseToast());
            }
        })
)(LogOut);
