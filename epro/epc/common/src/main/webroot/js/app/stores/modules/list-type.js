import {
    LIST_TYPE_GET,
    LIST_TYPE_SET
} from "../types-list-type";

const listType = {
    state: {
        listType : 'L'
    },
    getters: {
        [LIST_TYPE_GET](state) {
            return state.listType;
        }
    },
    mutations: {
        [LIST_TYPE_SET](state, value) {
            state.listType = value;
            sessionStorage.setItem('VT', value);
        }
    },
    actions: {
        [LIST_TYPE_SET]({commit}, value = 'L') {
            commit(LIST_TYPE_SET, value);
        }
    }
};

export default listType;