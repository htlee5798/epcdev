import React, {Component} from 'react';
import getMobilePath from '../cores/get-mobile-path';

class BtnSearch extends Component{
	render() {
		return (
			<a href={getMobilePath({url:'/mobile/search/Search.do'})} className="action-search">
				<i className="icon-header-sub-search">검색페이지</i>
			</a>
		)
	}
}

export default BtnSearch;