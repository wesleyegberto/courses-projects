var fs = require('fs');

fs.createReadStream('smith.jpg')
	// chunk to be processed
	.pipe(fs.createWriteStream('cloned_smith.jpg'))
	// final event
	.on('finish', function() {
		console.log('Smith was cloned again!');
	});