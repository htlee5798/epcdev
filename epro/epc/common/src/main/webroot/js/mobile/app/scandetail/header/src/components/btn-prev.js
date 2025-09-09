import React, {Component} from 'react';
import getMobilePath from "../cores/get-mobile-path";
import communicateWithApp from '../cores/communicate-with-app';

class BtnPrev extends Component {
	constructor (props) {
		super(props);

		this.state = {
			isApp: props.isApp
		};
	}

	componentWillReceiveProps (nextProps) {
		if(this.state.isApp !== nextProps.isApp) {
			this.setState({
				isApp: nextProps.isApp
			});
		}
	}

	onPrev = (event) => {
		event.preventDefault();

		if(this.state.isApp) {
			communicateWithApp('scanHistoryBack');
		} else {
			if(document.referrer !== '') {
				window.history.back();
			} else {
				window.location.href = getMobilePath({
					url : '/mobile/corners.do'
				});
			}
		}
	};

	render () {
		return (
			<a href="javascript:;" className="action-prev" onClick={this.onPrev}>
				<i className="icon-header-sub-prev">이전페이지</i>
			</a>
		);
	}
}

export default BtnPrev;