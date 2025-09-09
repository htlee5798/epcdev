import mojs from 'mo-js';

const OPTS = {
    fill: 'none',
    radius: 5,
    strokeWidth: {5: 0},
    scale: {0: 1},
    angle: {'rand(-35, -70)': 0},
    duration: 300,
    left: 0, top: 0,
    easing: 'cubic.out'
};

const circle1 = new mojs.Shape({
    ...OPTS,
    stroke: 'cyan',
});

const circle2 = new mojs.Shape({
    ...OPTS,
    radius: {0: 3},
    strokeWidth: {3: 0},
    stroke: 'magenta',
    delay: 'rand(75, 150)'
});

export default function (e) {
    circle1
        .tune({x: e.pageX, y: e.pageY})
        .replay();

    circle2
        .tune({x: e.pageX, y: e.pageY})
        .replay();
};