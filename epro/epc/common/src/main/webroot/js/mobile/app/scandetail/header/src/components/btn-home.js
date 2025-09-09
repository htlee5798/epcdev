import $ from 'jquery';
import React, {Component} from 'react';
import getMobilePath from '../cores/get-mobile-path';

class BtnHome extends Component {
	render () {
		return (
			<h1 className="action-logo">
				<a href={getMobilePath({url:'/mobile/main.do'})} className="icon-header-sub-logo">롯데마트몰</a>
			</h1>
		)
	}
}

export default BtnHome;