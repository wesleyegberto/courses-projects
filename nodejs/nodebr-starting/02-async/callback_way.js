function fetchUser(userCallback) {
	setTimeout(() => {
		userCallback(null, { id: 42, name: 'Duke' });
	}, 500);
}

function fetchAddress(addressCallback) {
	setTimeout(() => {
		addressCallback(null, { id: 422, street: 'First Avenue, 1500' });
	}, 500);
}

fetchUser((userAddress, user) => {
	if (userAddress) {
		console.error('Ops: ', userAddress);
		return;
	}
	console.log('User: ', user);

	fetchAddress((addressError, address) => {
		if (addressError) {
			console.error('Ops: ', addressError);
			return;
		}
		console.log('Address: ', address);
	});
});