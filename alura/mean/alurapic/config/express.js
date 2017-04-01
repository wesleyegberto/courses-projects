const express = require('express');
const consign = require('consign');
const bodyParser = require('body-parser');

var app = express();

// middleware for static files
app.use(express.static('./public'));
app.use(bodyParser.json());

consign({cwd: 'app'})
    .include('models')
    .then('api')
    .then('routes')
    .into(app);

module.exports = app;