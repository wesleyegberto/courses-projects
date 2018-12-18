var fs = require('fs');

// read a file buffering it
fs.readFile('smith.jpg', function(err, buffer) {
	fs.writeFile('cloned_smith.jpg', buffer, function(err) {
		console.log('Smith was cloned!');
	});
});