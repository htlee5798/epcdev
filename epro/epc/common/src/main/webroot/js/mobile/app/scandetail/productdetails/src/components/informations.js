import React, {Component} from 'react';

const RenderInformations = (props) => {
    if(!props.lists) {
        return null;
    }


    let lists = JSON.parse(decodeURIComponent(props.lists));

    if(lists.length === 0) {
        return null;
    }

    return lists.map((v) =>
        <tr>
            <th scope="row">
                {v.COL_NM}
            </th>
            <td>
                {v.COL_VAL}
            </td>
        </tr>
    )
}

class Informations extends Component {
    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }
    render() {
        return (
            <RenderInformations {...this.state}/>
        );
    }
}

export default Informations;