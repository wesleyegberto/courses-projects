var app = require('./config/express.js')();

app.listen(3000, function() {
	console.log('Server is running at 3000');
});