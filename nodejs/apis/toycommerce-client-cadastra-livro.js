var http = require('http');

var configuracoes = {
  hostname: 'localhost',
  port: 3000,
  path: '/produtos',
  method: 'post',
  headers: {
    'Content-type': 'application/json'
  }
};

var client = http.request(configuracoes, function(res) {
  console.log(res.statusCode);
});

var produto = {
  titulo: 'Java EE com DevOps',
  isbn: '2194299124',
  preco: '120'
};

// Efetua o post
client.end(JSON.stringify(produto));