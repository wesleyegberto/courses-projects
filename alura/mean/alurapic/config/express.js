const express = require('express');
const consign = require('consign');
const bodyParser = require('body-parser');
const cookieParser = require('cookie-parser');

var app = express();

const secretWord = 'palavra-super-secreta';
app.set('secret', secretWord);

// middleware for static files
app.use(express.static('./public'));
app.use(cookieParser(secretWord))
app.use(bodyParser.json());

consign({cwd: 'app'})
    .include('models')
    .then('api')
    // garante o carregamento do auth primeiro
    .then('routes/auth')
    .then('routes')
    .into(app);

module.exports = app;