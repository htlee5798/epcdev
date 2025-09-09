import {props, withComponent} from 'skatejs';

import './components/quick-menu-bar-buttons';

//TODO:create instance quickmenubar
class QuickMenuBar extends withComponent() {
    static get props() {
        return {
            data : props.string
        }
    }
    connected () {
        console.log('index');
    }
    render({data}) {
        console.log(JSON.parse(data));
        console.log(this);

        const info = JSON.parse(data);

        let isSoldOut = info.items[0]
            .filter((v)=> {
                return v.ABSENCE_YN === 'N';
            })[0]
            .length === 0;

        return `<quick-menu-bar-buttons></quick-menu-bar-buttons>
                <quick-menu-bar-contents></quick-menu-bar-contents>`;
    }
}

customElements.define('quick-menu-bar', QuickMenuBar);

