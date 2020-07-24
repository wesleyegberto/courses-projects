const routes = require('express').Router();
const authMiddleware = require('./app/middlewares/auth.middleware');

const sessionController = require('./app/controllers/SessionController');

routes.post('/sessions', sessionController.store);

routes.use(authMiddleware);

routes.get('/dashboard', (req, resp) => {
  return resp.status(200).send();
});

module.exports = routes;
