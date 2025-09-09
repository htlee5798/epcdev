import React, {Component} from 'react';
import getBasketCount from '../cores/get-basket-count';

class BtnBasket extends Component {
	constructor (props) {
		super(props);

		this.state = {
			basketCount: 0,
			receivedlink : props.receivedlink
		};
	}

	componentWillMount () {
		getBasketCount((basketCount) => {
			this.setState({
				basketCount
			});
		});
	}

	onBasket = (event) => {
		event.preventDefault();

		if(this.state.receivedlink) {
			window.openLoginLayer();
			return;
		}
		window.global.goBasket();
	};
	render() {
		return (
			<a href={this.state.receivedlink ? this.state.receivedlink : '#'} id="basketCountData" className="action-basket" data-qty={this.state.basketCount} onClick={this.onBasket}>
				<i className="icon-header-sub-basket">장바구니</i>
			</a>
		);
	}
}

export default BtnBasket;