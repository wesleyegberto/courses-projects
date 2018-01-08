const path = require('path');
const htmlWebpackPlugin = require('html-webpack-plugin');
const babiliPlugin = require('babili-webpack-plugin');
const extractTextPlugin = require('extract-text-webpack-plugin');
const optimizeCssAssetsPlugins = require('optimize-css-assets-webpack-plugin');
const webpack = require('webpack'); // para disponibilizar o jQuery em todos os escopos

let plugins = [
	new htmlWebpackPlugin({
		hash: true, // add hash nos request para facilitar invalidacao cache
		minify: {
			html5: true,
			collapseWhitespace: true,
			removeComments: true
		},
    filename: 'index.html',
    template: __dirname + '/main.html',
	}),
	new extractTextPlugin('styles.css'),
	new webpack.ProvidePlugin({
		$: 'jquery/dist/jquery.js',
		jQuery: 'jquery/dist/jquery.js'
	}),
	// Para separar as libs de terceiros
	new webpack.optimize.CommonsChunkPlugin({
		name: 'vendor', // sera usado no entry
		filename: 'vendor.bundle.js'
	})
];

// Usamos stringfy para poder usar variavel API_URL no codigo, senao substituira texto API_URL
let SERVICE_URL = JSON.stringify('http://localhost:3000');

if (process.env.NODE_ENV === 'production') {
	// otimizar o carregamento e processamento no browser
	plugins.push(new webpack.optimize.ModuleConcatenationPlugin());

	plugins.push(new babiliPlugin());

	plugins.push(new optimizeCssAssetsPlugins({
		cssProcessor: require('cssnano'),
		cssProcessorOptions: {
			discardComments: {
				removeAll: true
			}
		},
		canPrint: true
	}));

	SERVICE_URL = JSON.stringify('http://minhaapi.api');
}

plugins.push(new webpack.DefinePlugin({
	API_URL: SERVICE_URL
}));

module.exports = {
	entry: {
		app: './app-src/app.js', // arquivo de entrada
		vendor: ['jquery', 'bootstrap', 'reflect-metadata'] // modules para o bundle
	},
	output: {
		filename: 'bundle.js', // nome do arquivo de saida
		path: path.resolve(__dirname, 'dist'), // pasta de saida
		// publicPath: 'dist' removido pois usaremos htmlWebpackPlugin que jogara o index.html no dist
	},
	module: {
		rules: [ // processadores especificos para os arquivos
			{ // loader para processar o babel
				test: /\.js$/,
				exclude: /node_modules/,
				use: {
					loader: 'babel-loader'
				}
			},
			{
				test: /\.css$/,
				use: extractTextPlugin.extract({
					fallback: 'style-loader',
					use: 'css-loader'
				})
			},
			{
				test: /\.(woff|woff2)(\?v=\d+\.\d+\.\d+)?$/,
				loader: 'url-loader?limit=10000&mimetype=application/font-woff'
			},
			{
				test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/,
				loader: 'url-loader?limit=10000&mimetype=application/octet-stream'
			},
			{
				test: /\.eot(\?v=\d+\.\d+\.\d+)?$/,
				loader: 'file-loader'
			},
			{
				test: /\.svg(\?v=\d+\.\d+\.\d+)?$/,
				loader: 'url-loader?limit=10000&mimetype=image/svg+xml'
			}
		]
	},
	plugins
};