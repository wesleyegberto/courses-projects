var app = require('./config/express')();

// Extrai o server para passar para o SocketIO
var server = require('http').Server(app);
var io = require('socket.io')(server);    

io.on('connection', function(client) {
	console.log('WS: Cliente conectou');
	client.on('event', (data) => console.log('WS: ' + data));
 	client.on('disconnect', () => console.log('WS: cliente desconectou'));
});

// Disponibiliza o objeto para ser extraído do express
app.set('io', io); 

// Agora é importado pelo Express-Load
// var configProdutos = require('./app/routes/configProdutos');
// configProdutos.configuraRotas(app);

// Recupera a porta da variável de ambiente
var port = process.env.PORT || 3000;

// Para usar Socket.io precisamos dar listen() no server
// para o Socket.io interceptar
server.listen(port, () => {
    console.log('Server is running');
});