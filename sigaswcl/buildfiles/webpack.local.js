const path = require('path');
var webpack = require('webpack');
var webpackMerge = require('webpack-merge');

// caricamento della configurazione comune, che verra' integrata in
// questo file
var commonConfig = require('./webpack.common.js');

/// caricamento parametri di ambiente
const ENV = process.env.NODE_ENV = process.env.ENV = 'development';
const ENV_PARAMS = require('./local.properties.json');

/// merge della configurazione
module.exports = webpackMerge(commonConfig, {
  output: {
    "path": path.join(process.cwd(), "dist"),
    "publicPath": ENV_PARAMS.publicPath,
    "filename": "[name].bundle.js",
    "chunkFilename": "[id].chunk.js"
  },
  devServer: {
	    contentBase: path.join(__dirname, "dist"),
	    compress: true,
	    port: 4200,
	    proxy: {
	        '/sigas': {
	            target: 'http://localhost:8080',
	            secure: false
	        }
	    }
	},
  plugins: [
    new webpack.EnvironmentPlugin({
      NODE_ENV: 'development', // use 'development' unless process.env.NODE_ENV is defined
      DEBUG: false
    }),
    
    new webpack.DefinePlugin({
      'ENV_PROPERTIES': JSON.stringify(ENV_PARAMS)
    })
  ]	
});
