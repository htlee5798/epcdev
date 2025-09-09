import getMobilePath from './get-mobile-path';
import Pubsub from 'pubsub-js';

const isMember = (
    {
        url,
        isWrote = false
    }) => {
    if(window.global.isMember && !window.global.isMember()) {
        alert('비회원은 작성할 수 없습니다.');
        return;
    }

    if(isWrote) {
        Pubsub.publish('activeToastRegisterIsAlready');
        return;
    }

    window.location.href = getMobilePath({url : `${url}`});
};

export default isMember;