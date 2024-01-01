import fs from 'node:fs';

try {
  fs.readFile(
    'not-found.txt',
    (err, result) => {
      if (err) {
        // readFile will suppress
        throw err;
      }
      console.log('Result', result);
    }
  );
} catch (error) {
  // never is called cause readFile catches all exception to log (we must use Promise.reject)
  console.log('Never called: ', error);
}
