import $ from 'jquery';
import React, {Component} from 'react';

const VIEW_BOX = "0 0 100 100";

class Circle extends Component {
    static defaultProps = {
        reviewAvreageGradeText : 0,
        radius : 35
    };
    constructor(props) {
        super ();

        this.state = Object.assign({}, props);
    }

    pieSlicer (grade) {
        let percentValue = (grade / 10) * this.state.circumference;
        if(document.getElementById('pie')) {
            document.getElementById('pie').style.strokeDasharray = percentValue + ' ' + this.state.circumference;
        }
    }

    componentWillMount () {
        let circumference = 2 * this.state.radius * Math.PI;
        let promise = new Promise((resolve) => {
            this.setState({
                circumference
            });

            resolve();
        });

        promise.then(() => {
            this.onAnimation();
        });
    }

    componentWillReceiveProps(nextProps) {
        if(this.state.reviewAvreageGrade !== nextProps.reviewAvreageGrade) {
            let promise = new Promise((resolve) => {
                this.setState({
                    reviewAvreageGrade: nextProps.reviewAvreageGrade
                });

                resolve();
            });

            promise.then(() => {
                this.onAnimation();
            });
        }
    }

    onAnimation = () => {
        let _this = this;
        $('output')
            .prop('Counter', 0)
            .animate({
                Counter: _this.state.reviewAvreageGrade
            }, {
                duration: 2000,
                easing: 'swing',
                step: function (now) {
                    _this.pieSlicer(now);
                    _this.setState({
                        reviewAvreageGradeText: now.toFixed(1)
                    });
                }
            });
    };

    render () {
        return (
            <div className="wrap-score-circle">
                <div id="readout">
                    <svg viewBox={VIEW_BOX}>
                        <circle r={this.state.radius}
                                cx="50"
                                cy="50"
                                id="pie"
                                fill="none">
                        </circle>
                    </svg>
                    <output for="utilslider">
                        {this.state.reviewAvreageGradeText}
                    </output>
                </div>
                <input type="hidden"
                       min="0"
                       max="10"
                       value={this.state.reviewAvreageGrade}
                       id="utilslider"/>
            </div>
        );
    }
}

export default Circle;