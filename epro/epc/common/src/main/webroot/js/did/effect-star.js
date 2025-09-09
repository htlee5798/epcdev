'use strict';

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var Star = function (_mojs$CustomShape) {
    _inherits(Star, _mojs$CustomShape);

    function Star() {
        _classCallCheck(this, Star);

        return _possibleConstructorReturn(this, (Star.__proto__ || Object.getPrototypeOf(Star)).apply(this, arguments));
    }

    _createClass(Star, [{
        key: 'getShape',
        value: function getShape() {
            return '<path d="M5.51132201,34.7776271 L33.703781,32.8220808 L44.4592855,6.74813038 C45.4370587,4.30369752 47.7185293,3 50,3 C52.2814707,3 54.5629413,4.30369752 55.5407145,6.74813038 L66.296219,32.8220808 L94.488678,34.7776271 C99.7034681,35.1035515 101.984939,41.7850013 97.910884,45.2072073 L75.9109883,63.1330483 L82.5924381,90.3477341 C83.407249,94.4217888 80.4739296,97.6810326 77.0517236,97.6810326 C76.0739505,97.6810326 74.9332151,97.3551083 73.955442,96.7032595 L49.8370378,81.8737002 L26.044558,96.7032595 C25.0667849,97.3551083 23.9260495,97.6810326 22.9482764,97.6810326 C19.3631082,97.6810326 16.2668266,94.4217888 17.4075619,90.3477341 L23.9260495,63.2960105 L2.08911601,45.2072073 C-1.98493875,41.7850013 0.296531918,35.1035515 5.51132201,34.7776271 Z" />';
        }
    }]);

    return Star;
}(mojs.CustomShape);

mojs.addShape('star', Star);

var RADIUS = 28;

var burst = new mojs.Burst({
    left: 0, top: 0,
    radius: { 6: RADIUS - 3 },
    angle: 45,
    children: {
        shape: 'star',
        radius: RADIUS / 2.2,
        fill: 'red',
        opacity : 0.3,
        degreeShift: 'stagger(0,-5)',
        duration: 1000,
        // delay: 200,
        easing: 'quad.out',
        isShowEnd: false
    }
});

burst.el.style.zIndex = 99999;

var star = new mojs.Shape({
    left: 0, top: 0,
    shape: 'star',
    stroke: 'red',
    strokeWidth: 1,
    strokeOpacity : 0.5,
    scale: { 1 : 0 },
    easing: 'quad.in',
    duration: 700,
    opacity : 0.5,
    radius: RADIUS / 2,
    isShowEnd: false
});

star.el.style.zIndex = 99999;

var timeline = new mojs.Timeline({ speed: 1.5 });

timeline.add(burst, star);

document.addEventListener('click', function (e) {
    var coords = { x: e.pageX, y: e.pageY };
    burst.tune(coords);
    // circle.tune(coords);
    star.tune(coords);
    timeline.replay();
});