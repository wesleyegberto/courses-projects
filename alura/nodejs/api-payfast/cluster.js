const appExpress = require('./config/express.js');
const cluster = require('cluster');
const os = require('os');
// get the number of cores
const cores = os.cpus();

// only the master thread has the fork()
if (cluster.isMaster) {
	cores.forEach(() => cluster.fork());

	// callback when some work starts
	cluster.on('online', (worker, code, signal) => {
    console.log(`Worker ${worker.process.pid} has started`);
  });
	// callback when some worker exits
	cluster.on('exit', (worker, code, signal) => {
    console.log(`Worker ${worker.process.pid} has died (signal from OS: ${signal})`);
    // create another worker
    cluster.fork();
  });
} else {
	// code to slaves
	appExpress().listen(3000, () => console.log('Server is running at 3000'));
}