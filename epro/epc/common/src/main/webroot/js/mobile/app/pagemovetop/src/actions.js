import C from './constant';

export const setActive = (windowHeight, scrollTop) =>
    ({
        type: C.SET_ACTIVE,
        windowHeight,
        scrollTop
    });