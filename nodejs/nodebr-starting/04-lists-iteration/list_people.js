const service = require('./starwars_service');

async function main() {
	try {
		const searchResult = await service.findPeopleByName('a');

		const names = [];

		// average: 0.190ms
		console.time('for');
		for (let i = 0; i < searchResult.results.length; i++) {
			const person = searchResult.results[i];
			names.push(person.name);
		}
		console.timeEnd('for');

		// average: 0.057ms
		console.time('for-in');
		for (let i in searchResult.results) {
			const person = searchResult.results[i];
			names.push(person.name);
		}
		console.timeEnd('for-in');

		// average: 0.016ms
		console.time('for-of');
		for (let person of searchResult.results) {
			names.push(person.name);
		}
		console.timeEnd('for-of');

		// average: 0.042ms
		console.time('forEach');
		searchResult.results.forEach(person => {
			names.push(person.name);
		});
		console.timeEnd('forEach');
	} catch (error) {
		console.log('Ops', error);
	}
}

main();