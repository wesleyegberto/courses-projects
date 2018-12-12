const util = require('util');

function fetchUser() {
	return new Promise((resolve, reject) => {
		setTimeout(() => {
			resolve({ id: 42, name: 'Duke' });
		}, 500);
	});
}

// just to show promisify
function fetchAddress(addressCallback) {
	setTimeout(() => {
		addressCallback(null, { id: 422, street: 'First Avenue, 1500' });
	}, 500);
}
const fetchAddressAsync = util.promisify(fetchAddress);

async function main() {
	try {
		// const user = await fetchUser();
		// const address = await fetchAddressAsync();

		// when a method doesn't depend on previous result we call do a Promise.all
		const result = await Promise.all([
			fetchUser(),
			fetchAddressAsync()
		]);

		const user = result[0];
		const address = result[1];

		console.log('User:', user);
		console.log('Address:', address);
	} catch (error) {
		console.error('Ops: ', error);
	}
}

main();
