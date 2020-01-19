const { Router } = require('express');
const DevController = require('./controllers/DevController');
const SearchController = require('./controllers/SearchController');

const routes = Router();

routes.get('/devs', DevController.getAll);
routes.post('/devs', DevController.save);

routes.get('/devs/search', SearchController.find);

module.exports = routes;
