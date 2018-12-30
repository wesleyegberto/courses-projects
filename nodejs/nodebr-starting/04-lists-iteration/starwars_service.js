const axios = require('axios');
const API_URL = 'https://swapi.co/api/people';

async function findPeopleByName(name) {
	const url = `${API_URL}/?format=json&search=${name}`;
	const response = await axios.get(url);
	return response.data;
}

module.exports = {
	findPeopleByName
};