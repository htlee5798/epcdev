import C from '../constants';

export const notice = (state = {}, action) => {
    switch (action.type) {
        case C.ON_CLOSE_TOAST :
            return Object.assign({}, state, {
                active: false
            });
        case C.IS_ACTIVE :
            return state.active;
        default :
            return state;
    }
};