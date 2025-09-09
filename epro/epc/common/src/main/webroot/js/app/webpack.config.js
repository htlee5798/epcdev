const path = require('path');
const webpack = require('webpack');
const glob = require('glob');
const CleanWebpackPlugin = require('clean-webpack-plugin');

const toObject = (array) => array.reduce((obj, path) => {
	let fileName = path.split('/').slice(-1)[0];

	obj[path.replace('/' + fileName, '')] = path;
	return obj;
}, {});

module.exports = {
    entry: toObject(glob.sync('./components/**/**/*.js')),
    output: {
        path: path.resolve(__dirname, './dist'),
        publicPath: '/dist/',
        filename: '[name]/bundle.js'
    },
    plugins: [
        new webpack.optimize.CommonsChunkPlugin({
            name: 'vue',
            filename: '[name]/bundle.js',
            minChunks: module => /node_modules/.test(module.resource)
        }),
        new webpack.optimize.UglifyJsPlugin({
            sourceMap: true,
            compress: {
                warnings: false
            }
        }),
        new webpack.LoaderOptionsPlugin({
            minimize: true
        }),
        new CleanWebpackPlugin(['dist'], {
            root: path.join(__dirname, './'),
            verbose: true,
            dry: false,
            exclude: []
        })
    ],
    module: {
        rules: [
            {
                test: /\.vue$/,
                loader: 'vue-loader',
                options: {
                    loaders: {}
                }
            },
            {
                test: /\.js$/,
                loader: 'babel-loader',
                exclude: /node_modules/
            },
            {
                test: /\.s[a|c]ss$/,
                loader: 'style!css!sass'
            }
        ]
    },
    resolve: {
        alias: {
            'vue$': 'vue/dist/vue.esm.js'
        },
        extensions: ['.js', '.vue'],
        modules: [
            path.resolve(),
            'node_modules'
        ]
    },
    externals: {
        jquery: 'jQuery',
        Swiper : 'Swiper',
        clipboard: 'Clipboard'
    },
    devtool: '#eval-source-map'
};