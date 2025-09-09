import $ from 'jquery';
import React, {Component} from 'react';
const TIME_STAMP = new Date().getDate();

class SnapShot extends Component {
   constructor(props) {
       super(props);

       this.state = Object.assign({}, props);
   }

   getImagePath (v) {
       return `${v.IMG_URL}?v=${TIME_STAMP}`;
   }

    onError = (event) => {
        let $img = $(event.target);
        let src = '//simage.lottemart.com/v3/images/temp/blank.png';

        $img.attr('src', src);
    };

   render () {
       return (
           <div className="gdi-partner">
               {this.state.lists.map((v) =>
                   <img src={this.getImagePath(v)}
                        onError={this.onError}
                   alt={decodeURIComponent(this.state.productName) + '_' + v.SEQ}/>
               )}
           </div>
       )
   }
}

export default SnapShot;