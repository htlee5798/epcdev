const path = require('path');
const webpack = require('webpack');
const CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = {
    entry: {
        quickmenubar : path.resolve(__dirname, './index.js')
    },
    output: {
        path: path.resolve(__dirname, './dist'),
        publicPath: '/dist/',
        filename: '[name].bundle.js'
    },
    optimization : {
        splitChunks : {
            cacheGroups: {
                vendors : {
                    name : 'vendors',
                    chunks : 'all',
                    filename: '[name].bundle.js',
                    test : /[\\/]node_modules|bower_components[\\/]/
                }
            }
        }
    },
    plugins: [
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
        extensions: ['.js'],
        modules: [
            path.resolve(),
            'node_modules',
            'bower_components'
        ]
    },
    externals: {
        jquery: 'jQuery'
    },
    devtool: '#eval-source-map'
};