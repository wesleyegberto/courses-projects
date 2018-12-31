const assert = require('assert');

const heroesDatabase = require('./database.js');

describe('Heroes CRUD', function() {
	const DEFAULT_FIRST_HERO = {
		id: 1,
		name: 'Wolverine',
		powers: [ 'Healing', 'Claws', 'Animality' ]
	};

	// reset the DB
	this.beforeAll(async () => {
		await heroesDatabase.writeDatabase([DEFAULT_FIRST_HERO]);
	});

	it('Should read a hero', async () => {
		const expected = DEFAULT_FIRST_HERO;

		const actual = await heroesDatabase.findById(1);

		assert.deepEqual(actual, expected);
	});

	it('Should create hero', async () => {
		const savedHero = await heroesDatabase.save({ name: 'Hulk', power: [ 'Strength', 'Superjump', 'Speed' ] });
		const persistedHero = await heroesDatabase.findById(savedHero.id);
		// savedHero.name = 'Hulk to Break'; just to test de reports =)
		assert.deepEqual(persistedHero, savedHero);
	});
});