import { spawn } from 'node:child_process';

const prepareLog = pid => msg => console.log(`pid: [${pid}] - ${msg}`);

const INSTANCES = 3;

const orchestratorLog = prepareLog(process.pid);
orchestratorLog('Orchestrator started');

function spinUpInstance() {
    const cp = spawn('node', ['03-server-let-it-crash.js']);

    orchestratorLog(`Starting process ${cp.pid}`);

    cp.stdout.on('data', msg => console.log(msg.toString().trim()));
    // handle the process exist
    cp.on('exit', (code) => {
        orchestratorLog(`Process ${cp.pid} exited with code ${code}`);
        if (code === 0) {
            return;
        }
        spinUpInstance();
    });

}

for (let i = 0; i < INSTANCES; i++) {
    spinUpInstance();
}
