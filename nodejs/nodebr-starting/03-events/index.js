const EventEmitter = require('events');

class MyEmitter extends EventEmitter {

}

const myEmitter = new MyEmitter();
const eventName = 'usuario:click';

myEmitter.on(eventName, function(click) {
    console.log('user has clicked', click);
})


// myEmitter.emit(eventName, 'Tab');
// myEmitter.emit(eventName, 'Ok button');

// let count = 0;
// setInterval(function() {
//     myEmitter.emit(eventName, 'Ok ' + (count++));
// }, 1000)

const stdin = process.openStdin();

function main() {
    return new Promise(function(resolve, reject) {
        stdin.addListener('data', function (value) {
            // console.log(`Typed: ${value.toString().trim()}`);
            return resolve(value);
        })
    })
}
main().then(function (resultado) {
    console.log('Result:', resultado.toString());
});