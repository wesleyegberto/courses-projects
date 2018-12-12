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

fetchUser()
	.then(user => {
		console.log('User: ', user);
		return fetchAddressAsync()
			.then(address => {
				console.log('Address: ', address);
				return {
					data: user,
					address: address
				};
			});
	})
	.then(register => {
		console.log('Register: ', register);
	})
	.catch(error => console.error('Ops: ', error));