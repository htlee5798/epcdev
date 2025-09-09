import $ from 'jquery';
import React, {Component} from 'react';
import {
	APP_INSTALL_CONFIRM,
	BTN_TEXT_QR,
	ERROR_MESSAGE_NOT_SUPPORT_DEVICE
} from "../i18n/i18n-ko";
import communicateWithApp from '../cores/communicate-with-app';

class BtnCamera extends Component {
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

	onCamera = (event) => {
		event.preventDefault();

		if(this.state.isApp) {
			communicateWithApp('detailScanShow');
		} else {
			if($.utils.isIOS()) {
				if(window.confirm(APP_INSTALL_CONFIRM)) {
					window.location.href = 'http://itunes.apple.com/app/id493616309?mt=8';
				}
			} else if($.utils.isAndroid()) {
				if(window.confirm(APP_INSTALL_CONFIRM)) {
					window.location.href = 'market://details?id=com.lottemart.shopping';
				}
			} else {
				alert(ERROR_MESSAGE_NOT_SUPPORT_DEVICE);
			}
		}
	};

	render () {
		return (
			<a href="#"
				className="action-camera"
				onClick={this.onCamera}>
				<i className="icon-header-sub-camera">
					{BTN_TEXT_QR}
				</i>
			</a>
		);
	}
}

export default BtnCamera;