const fs = require('fs');

module.exports = function(app) {
	app.post("/upload/image", (req, resp) => {
		var filename = req.headers.filename;
		var newFilename = `${new Date().getTime()}_${filename}`;
		req.pipe(fs.createWriteStream(`misc/${newFilename}`))
			.on('finish', () => {
				resp.status(201).send('ok');
			});
	});
};