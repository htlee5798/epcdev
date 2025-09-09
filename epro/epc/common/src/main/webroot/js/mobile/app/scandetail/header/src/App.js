import $ from 'jquery';
import React, { Component } from 'react';
import BtnPrev from "./components/btn-prev";
import BtnCamera from "./components/btn-camera";
import BtnSearch from "./components/btn-search";
import BtnBasket from "./components/btn-basket";
import BtnHome from "./components/btn-home";

class App extends Component {
	constructor (props) {
		super(props);

		this.state = {
			isApp : false,
			receivedlink : props.receivedlink || null
		}
	}
	componentDidMount () {
		this.setState({
			isApp: $.utils.isiOSLotteMartApp() || $.utils.isAndroidLotteMartApp()
		});
	}

	render() {
		return (
			// <React.Fragment>
			<header className="ta-header-sub">
				<BtnPrev isApp={this.state.isApp}/>
				<BtnHome receivedlink={this.state.receivedlink}/>
				<div className="group-action">
					<BtnCamera isApp={this.state.isApp}/>
					<BtnSearch/>
					<BtnBasket receivedlink={this.state.receivedlink}/>
				</div>
			</header>
			// </React.Fragment>
		);
	}
}

export default App;
