import {} from './polyfill/fetch';
import { instanciaAtual } from './controller/NegociacaoController';

let negociacaoController = instanciaAtual();

// bind poruqe negociacao nao tara no escopo global
document.querySelector('.form').onsubmit = negociacaoController.adiciona.bind(negociacaoController);
document.querySelector('#btn-import').onclick = negociacaoController.importaNegociacoes.bind(negociacaoController);
document.querySelector('#btn-delete').onclick = negociacaoController.apaga.bind(negociacaoController);