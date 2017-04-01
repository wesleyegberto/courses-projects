const express = require('express');
const consign = require('consign');
const bodyParser = require('body-parser');

var app = express();

app.set('secret', 'palavra-super-secreta');

// middleware for static files
app.use(express.static('./public'));
app.use(bodyParser.json());

consign({cwd: 'app'})
    .include('models')
    .then('api')
    // garante o carregamento do auth primeiro
    .then('routes/auth')
    .then('routes')
    .into(app);

module.exports = app;