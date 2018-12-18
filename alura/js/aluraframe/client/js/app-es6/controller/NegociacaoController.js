import { ListaNegociacoes } from '../model/ListaNegociacoes';
import { Mensagem } from '../model/Mensagem';
import { NegociacoesView } from '../view/NegociacoesView';
import { MensagemView } from '../view/MensagemView';
import { NegociacaoService } from '../service/NegociacaoService';
import { DateHelper } from '../helper/DateHelper';
import { Bind } from '../helper/Bind';
import { Negociacao } from '../model/Negociacao';

class NegociacaoController {
  constructor() {
    // bind associa o contexto de um objeto na função querySelector
    // porque o querySelect usa this para navegar no DOM
    const $ = document.querySelector.bind(document);

    this._inputData = $("#data");
    this._inputQuantidade = $("#quantidade");
    this._inputValor = $("#valor");

    // this._mensagem = new Mensagem();
    // this._mensagemView = new MensagemView($('#mensagemView'));
    // this._mensagem = ProxyFactory.create(new Mensagem(), ['texto'], model => this._mensagemView.update(model));
    // this._mensagemView.update(this._mensagem);

    // criamos um bind que utiliza um proxy usando o objeto mensagem e atualiza a view automaticamente
    this._mensagem = new Bind(new Mensagem(), new MensagemView($('#mensagemView')), 'texto');

    // this._negociacoesView = new NegociacoesView($('#negociacoesView'));
    // this._listaNegociacoes = ProxyFactory.create(new ListaNegociacoes(), ['adiciona', 'esvazia'], model => this._negociacoesView.update(model));
    // this._negociacoesView.update(this._listaNegociacoes);

    this._listaNegociacoes = new Bind(new ListaNegociacoes(),
      new NegociacoesView($('#negociacoesView')),
      'adiciona', 'esvazia', 'ordena', 'inverteOrdem');

    new NegociacaoService()
      .lista()
      .then(negociacoes => negociacoes.forEach(negociacao => this._listaNegociacoes.adiciona(negociacao)))
      .catch(erro => this._mensagem.texto = erro);

    // setInterval(() => this.importaNegociacoes(), 3000);
  }

  adiciona(event) {
    // cancela o submit
    event.preventDefault();

    // removido para adicionar IndexDB primeiro
    // this._listaNegociacoes.adiciona(this._criaNegociacao());
    // agora usamos o bind do model
    // this._negociacoesView.update(this._listaNegociacoes);

    // TODO: quando dentro do then() a linha abaixa nao captura os valores dos campos
    let negociacao = this._criaNegociacao();
    new NegociacaoService()
      .cadastra(negociacao)
        .then(mensagem => {
          this._listaNegociacoes.adiciona(negociacao);
          this._mensagem.texto = mensagem;
          this._limpaFormulario();
        })
        .catch(erro => this._mensagem.texto = erro);

    this._limpaFormulario();
  }

  _criaNegociacao() {
    // let data = new Date(...this._inputData.value.replace(/-/g, ','))
    // ES6 - spread operator = cada posição do array vira um argumento
    // let data = new Date(... this._inputData.value.split('-').map((val, idx) => { return val - idx % 2}));
    return new Negociacao(
      DateHelper.textoParaData(this._inputData.value), // ES6 - métodos estáticos
      this._inputQuantidade.value,
      this._inputValor.value
    );
  }

  apaga() {
    this._listaNegociacoes.esvazia();
    // this._negociacoesView.update(this._listaNegociacoes);

    // removido para apresentar mensagem do retorno
    // this._mensagem.texto = 'Negociações apagadas com sucesso';
    // agora usamos bind do model
    // this._mensagemView.update(this._mensagem);

    new NegociacaoService()
      .apaga()
      .then(mensagem => {
        this._mensagem.texto = mensagem;
        this._listaNegociacoes.esvazia();
      })
      .catch(erro => this._mensagem.texto = erro);
  }

  _limpaFormulario() {
    this._inputData.value = '';
    this._inputQuantidade.value = 1;
    this._inputValor.value = 1.0
    this._inputData.focus();
  }

  importaNegociacoes() {
    let service = new NegociacaoService();
    service.importaTodasNegociacoes(this._listaNegociacoes.negociacoes)
      .then(negociacoes => {
        negociacoes.forEach(negociacao => this._listaNegociacoes.adiciona(negociacao));
        this._mensagem.texto = 'Negociações importadas com sucesso';
      })
      .catch(erro => this._mensagem.texto = erro);
  }

  ordena(coluna) {
    if (this._ordemAtual == coluna) {
      this._listaNegociacoes.inverteOrdem();
    } else {
      this._listaNegociacoes.ordena((a, b) => a[coluna] - b[coluna]);
    }
    this._ordemAtual = coluna;
  }
}

let negociacaoController = new NegociacaoController();
export function instanciaAtual() {
  return negociacaoController;
}