module.exports = function(app){
  app.post('/correios/calculo-prazo', function(req, res) {
    var shippingData = req.body;
    var correiosSOAPClient = new app.servicos.correiosSOAPClient();
    correiosSOAPClient.calculaPrazo(shippingData, function(err, result) {
      if (err) {
        res.status(500).send(err);
        return;
      }
      res.json(result);
    });
  });
}