class Bind {
  /**
   * Cria um Bind entre o model e a view, efetuando atualizando
   * quando props é alterado/invocado.
   * @param {*} model 
   * @param {*} view 
   * @param {*} props 
   */
  constructor(model, view, ...props) {
    let proxy = ProxyFactory.create(model, props, model => view.update(model));
    view.update(model);
    return proxy;
  }
}