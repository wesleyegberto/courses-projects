export class ProxyFactory {
  /**
   * Cria um proxy que encapsula o objeto recebido.
   * @param {*} objeto objeto que será encapsulado
   * @param {*} props lista com nome das funções que queremos interceptar
   * @param {*} acao callback
   */
  static create(objeto, props, acao) {
    return new Proxy(objeto, {
      // interceptamos todos as leituras em atributos, funções getter
      // e pré-execução de função (para executar é feito um get antes)
      get(target, prop, receiver) {
        if (props.includes(prop) && ProxyFactory._ehFuncao(target[prop])) {
          return function () {
            console.log(`invoking function: ${prop}`);
            // precisamos fazer return caso o método do target retorne algo
            Reflect.apply(target[prop], target, arguments);
            return acao(target);
          }
        }
        return Reflect.get(target, prop, receiver);
      },
      // intercepta todos as atribuções em atributos e métodos setter
      set: function (target, prop, value, proxy) {
        if(props.includes(prop)) {
          console.log(`setting '${value}' to ${prop}, antigo: ${target[prop]}`);
          target[prop] = value;
          acao(target);
        }
        return Reflect.set(target, prop, value, proxy); // delega
      }
    })
  }

  static _ehFuncao(prop) {
    return typeof(prop) == typeof(Function);
  }
}