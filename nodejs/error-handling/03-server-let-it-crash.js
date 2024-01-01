const UNKNOWN_ERROR = 1;

const KNOWN_ERRORS = [
    { exitCode: UNKNOWN_ERROR, event: 'uncaughtException' },
    { exitCode: UNKNOWN_ERROR, event: 'unhandledRejection' },
];

const log = msg => console.log(`pid: [${process.pid}] - ${msg}`);

process.on('exit', (code) => {
    if (code === 0) {
        log('Application completed');
    } else {
        log('Graceful shutdown after error');
    }
    // graceful shutdown
    log(`Server closed with success`);
    log(`DB closed with success`);
    process.exit(code);
})

KNOWN_ERRORS.forEach(({ exitCode, event }) => {
    process.on(event, (error) => {
        log(`Process exiting due to ${event}`, error.message);
        if (exitCode === UNKNOWN_ERROR) {
            // dump the app info to future analysis
            // process.abort();
            process.exit(exitCode);
        }

        process.exit(exitCode);
    })
})

log('Process started');
let counter = 0;
const connectToDB = () => {
    const random = Math.random();

    if (random < 0.4)
        return Promise.reject('Could not connect to DB');
    log('DB connected with success');

    if (++counter > 3) {
        log('Processed with success');
        process.exit(0);
    }
}

setInterval(() => connectToDB(), 200);
