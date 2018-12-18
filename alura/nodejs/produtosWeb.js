
// Importação do módulo HTTP
var http = require('http');

// Handler do request
var requestHandler = (req, resp) => {
	resp.writeHead(200, {'Content-Type': 'text/html'});
	resp.end('<html><body>Hello World!</body></html>')
};

// Cria o server e define o handler
var server = http.createServer(requestHandler);

// Inicia o listener na porta
server.listen(3000);

console.log("server is running");