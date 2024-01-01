import fs from 'node:fs';

try {
  // promise will receive the rejection and rethrow the error
  await new Promise((resolve, reject) => {
    fs.readFile(
      'not-found.txt',
      (err, result) => {
        if (err) {
          reject(err);
          return;
        }
        resolve(result);
      }
    );
  });
} catch (error) {
  console.log('This will be called: ', error);
}
