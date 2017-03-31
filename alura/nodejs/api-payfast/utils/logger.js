const fs = require('fs');
const winston = require('winston');

if (!fs.existsSync("logs")){
  fs.mkdirSync("logs");
}

module.exports = new winston.Logger({
	transports: [
		new winston.transports.File({
			name: 'info-log',
			level: 'info',
			filename: 'logs/payfast_infos.log',
			maxsize: 10000,
			maxFiles: 10
		}),
		new winston.transports.File({
			name: 'error-log',
			level: 'error',
			filename: 'logs/payfast_errors.log',
			maxsize: 10000,
			maxFiles: 10
		})
	]
});