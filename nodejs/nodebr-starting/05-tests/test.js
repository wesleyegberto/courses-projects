const assert = require('assert');
const nock = require('nock');

const service = require('./starwars_service');

describe('Star Wars Test', function () { // didn't use arrow fn because we need the describe scope
	this.beforeAll(() => {
		const response = {
			count: 1,
			next: null,
			previous: null,
			results: [{
				name: 'R2-D2',
				height: '96',
				mass: '32',
				hair_color: 'n/a',
				skin_color: 'white, blue',
				eye_color: 'red',
				birth_year: '33BBY',
				gender: 'n/a',
				homeworld: 'https://swapi.co/api/planets/8/',
				films: [],
				species: [],
				vehicles: [],
				starships: [],
				created: '2014-12-10T15:11:50.376000Z',
				edited: '2014-12-20T21:17:50.311000Z',
				url: 'https://swapi.co/api/people/3/'
			}]
		};

		nock('https://swapi.co/api/people')
			.get('/?format=json&search=r2-d2')
			.reply(200, response);
	});

	it('Should requset R2-D2 with correct format', async () => {
		const expected = [{
			name: 'R2-D2',
			height: 96
		}];
		const nameFilter = 'r2-d2';

		const searchResult = await service.findPeopleByName(nameFilter);

		assert.deepEqual(searchResult, expected);
	});
});