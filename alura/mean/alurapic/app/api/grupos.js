const app = {};

app.lista = function(req, resp) {
  let grupos = [
    {_id: 1, nome: 'Animais' },
    {_id: 2, nome: 'Paisagens' },
    {_id: 3, nome: 'Desenhos' }
  ];
  resp.json(grupos);
};

module.exports = app;