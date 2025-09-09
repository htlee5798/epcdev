import {props, withComponent} from 'skatejs';

class QuickMenuBarButtons extends withComponent() {
    static get props () {
        return {
            isSoldOut : props.boolean
        }
    }
    connected () {
        console.log('quick-menu-bar-button');
    }
    render (state) {
        return `<wrap-button></wrap-button>`;
    }
}

customElements.define('quick-menu-bar-buttons', QuickMenuBarButtons);