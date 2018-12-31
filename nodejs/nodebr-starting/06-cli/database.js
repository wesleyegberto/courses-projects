const { readFile, writeFile } = require('fs');
const { promisify } = require('util');

const readFileAsync = promisify(readFile);
const writeFileAsync = promisify(writeFile);

class FileDatabase {
	constructor() {
		this.DATABASE_FILENAME = 'heroes.db.json';
	}

	async readDatabase() {
		const file = await readFileAsync(this.DATABASE_FILENAME, 'utf8');
		// here we could just read the .json with require() like:
		// const file = required('./heroes.db.json');
		return JSON.parse(file.toString());
	}

	async writeDatabase(heroes) {
		await writeFileAsync(this.DATABASE_FILENAME, JSON.stringify(heroes));
		return true;
	}

	async findById(id) {
		const heroes = await this.readDatabase();
		if (id) {
			return heroes.find(h => h.id === id);
		}
		return heroes;
	}

	async save(hero) {
		const dbHero = {
			id: Date.now(),
			...hero
		}
		const heroes = await this.readDatabase();
		heroes.push(dbHero);
		if (await this.writeDatabase(heroes)) {
			return dbHero;
		}
		throw new Error('Could save the hero =/');
	}
}

module.exports = new FileDatabase();