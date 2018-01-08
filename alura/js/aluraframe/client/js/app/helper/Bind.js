class Bind {
  /**
   * Cria um Bind entre o model e a view, efetuando atualizacoes
   * quando props é alterado/invocado.
   */
  constructor(model, view, ...props) {
    let proxy = ProxyFactory.create(model, props, model => view.update(model));
    view.update(model);
    return proxy;
  }
}