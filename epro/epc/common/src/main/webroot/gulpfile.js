const gulp = require('gulp');
const concat = require('gulp-concat');
const uglify = require('gulp-uglify');
const sourcemaps = require('gulp-sourcemaps');
const rename = require('gulp-rename');
const clean = require('gulp-clean');
const fileExist = require('files-exist');
const paths = require('./gulp-default-path');
const debug = require('gulp-debug');

const mainJS = require('./gulp-variable-main');
const productListJS = require('./gulp-variable-product-list');

const venders = [
	paths.jqueryLibrary + '/jquery-3.2.1.min.js',
	paths.jqueryLibrary + '/cookie/jquery.cookie.js',
	paths.jqueryLibrary + '/swiper/swiper-3.4.2.min.js',
	paths.jqueryLibrary + '/jsrender/jsrender.min.js'
];

const cores = [
	paths.polyfill + '/Array.filter.js',
	paths.polyfill + '/Array.indexof.js',
	paths.polyfill + '/Array.isArray.js',
	paths.polyfill + '/Array.some.js',
	paths.polyfill + '/Date.Format.js',
	paths.polyfill + '/Date.yyyymmdd.js',
	paths.polyfill + '/String.replaceAll.js',
	paths.polyfill + '/String.trim.js',

	paths.jqueryRoot + '/jquery.utils.js',
	paths.jqueryRoot + '/jquery.apis.js',
	paths.jsRoot + '/templates/jsrender/helper.js',
	paths.jsRoot + '/templates/jsrender/register.js',
	paths.modules + '/schemeLoader.js',
	paths.modules + '/tuneLoader.js',
	paths.modules + '/commons/imageError.js',
	paths.modules + '/rnbs/m.myProductHistory.js',
	paths.modules + '/rnbs/m.myMenuHistory.js',
	paths.modules + '/rnbs/m.myByProductHistory.js',
	paths.modules + '/image-lazy-load.js',
	paths.modules + '/infiniteScroll.js',
	paths.modules + '/mcouponPopup.js'
];

const delegateTaskRunner = (taskName, taskFileArray) => {
	gulp
		.src(fileExist(taskFileArray))
		.pipe(sourcemaps.init())
		.pipe(debug({ title: 'concat : ' }))
		.pipe(concat(taskName + '.js'))
		.pipe(gulp.dest(paths.dist))
		.pipe(rename(taskName + '.min.js'))
		.pipe(debug({ title: 'minify : ' }))
		.pipe(uglify())
		.pipe(sourcemaps.write('./'))
		.pipe(gulp.dest(paths.dist));
};

gulp.task('clean-dist', () => {
	gulp.src(paths.dist + '*.js', { read: false }).pipe(clean());
});

gulp.task('venders', () => {
	gulp
		.src(fileExist(venders))
		.pipe(concat('vender.min.js'))
		.pipe(gulp.dest(paths.dist));
});

gulp.task('cores', () => {
	delegateTaskRunner('cores', cores);
});

gulp.task('main', () => {
	'use strict';

	delegateTaskRunner('main', mainJS);
});

gulp.task('productList', () => {
	'use strict';

	delegateTaskRunner('product-list', productListJS);
});

gulp.task('default', ['clean-dist', 'cores', 'venders', 'main', 'productList']);
