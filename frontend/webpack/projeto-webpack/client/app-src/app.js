// vai procurar no node_modules
// esses CSS sao tratados como modules pelo WebPack, porem precisamos de loaders
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-theme.css';

import '../css/style.css';

import 'bootstrap/js/modal.js';

import { NegociacaoController } from './controllers/NegociacaoController.js';
import { Negociacao } from './domain/index.js';

$('h1').click(() => alert('Clicked'));

const controller = new NegociacaoController();
const negociacao = new Negociacao(new Date(), 1, 200);
const headers = new Headers();
headers.set('Content-Type', 'application/json');
const body = JSON.stringify(negociacao);
const method = 'POST';

const config = { 
    method,
    headers,
    body 
};

fetch(`${API_URL}/negociacoes`, config)
    .then(() => console.log('Dado enviado com sucesso'));