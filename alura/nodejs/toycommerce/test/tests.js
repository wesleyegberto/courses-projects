var http = require('http');
var assert = require('assert');
// O supertest usa o App para subir a aplicação
var app = require('../config/express')();
var request = require('supertest')(app);

describe('Produto Controller', () => {

	beforeEach(done => {
    var connection = app.infra.connectionFactory();            
    connection.query("delete from Livro", function(ex, result){
      if(!ex) {
        done();
      } else {
        console.log(ex);
      }
    });
	});

	// Test usando só Node
  /*
	it('devolver os produtos em json', function (done) {
    var configuracoes = {
        hostname: 'localhost',
        port: 3000,
        path: '/produtos',
        headers: {
            'Accept':'application/json'
        }
    };
    http.get(configuracoes, function(res) {
        assert.equal(res.statusCode, 200);
        assert.equal(res.headers['content-type'], 'application/json');
        done(); // chama o done para informar que o teste acabou (async)
    });
  });
  */

	it('devolver os produtos em json', function (done) {
  	request.get('/produtos')
      .set('Accept', 'application/json')
      .expect(200)
      .expect('Content-Type', /application\/json/, done);
  });

	it('não deve cadastrar produto com dados invalidos', (done) => {
		request.post('/produtos')
      .send({titulo:"", descricao:"livro de teste"})
      .expect(400, done);
	});

	it('deve cadastrar produto com dados válidos e redirecionar para listagem', function (done) {
    request.post('/produtos')
      .send({titulo:"novo livro",preco:20.50,descricao:"livro de teste"})
      .expect(302)
      .expect('Location', /\/produtos$/, done)
  });
});