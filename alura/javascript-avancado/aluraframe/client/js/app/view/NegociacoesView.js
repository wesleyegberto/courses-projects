class NegociacoesView extends View {
  constructor(elemento) {
    super(elemento);
  }

  template(model) {
    return `
      <table class="table table-hover table-bordered">
          <thead>
              <tr>
                  <th onclick="negociacaoCtrl.ordena('data')">DATA</th>
                  <th onclick="negociacaoCtrl.ordena('quantidade')">QUANTIDADE</th>
                  <th onclick="negociacaoCtrl.ordena('valor')">VALOR</th>
                  <th onclick="negociacaoCtrl.ordena('volume')">VOLUME</th>
              </tr>
          </thead>
          <tbody>
            ${model.negociacoes.map(n => `
              <tr>
                  <td>${DateHelper.dataParaTexto(n.data)}</td>
                  <td>${n.quantidade}</td>
                  <td>${n.valor}</td>
                  <td>${n.volume}</td>
              </tr>
            `).join('')}
          </tbody>
          <tfooter>
            <tr>
              <td colspan="3">Total:</td>
              <td>${model.volumeTotal}</td>
            </tr>
          </tfooter>
     </table>
    `;
  }
}