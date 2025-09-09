import C from '../constant';

export const sideBar = (state = {}, action) => {
    switch (action.type) {
        case C.SET_ACTIVE:
            let active = action.windowHeight / 1.5 < action.scrollTop;

            if(state.active !== active) {
                return Object.assign({}, state, {
                    active
                });
            } else {
                return state;
            }
        default:
            return state;
    }
};