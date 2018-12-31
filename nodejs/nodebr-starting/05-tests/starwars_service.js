const axios = require('axios');

const API_URL = 'https://swapi.co/api/people';

function mapPerson(person) {
	return {
		name: person.name,
		height: person.height
	};
}

async function findPeopleByName(name) {
	const url = `${API_URL}/?format=json&search=${name}`;
	const response = await axios.get(url);
	return response.data.results.map(mapPerson);
};

module.exports = { findPeopleByName };