const request = require('supertest');
const factories = require('../factories');

const app = require('../../src/app');

const { User } = require('../../src/app/models');

const truncate = require('../utils/truncate');

describe('Authentication', () => {
  beforeEach(async () => {
    await truncate();
  });

  it('should authenticate with valid credentials', async () => {
    await factories.create('User', {
      email: 'wesley@github.com',
      password: 'super-secret-password',
    });

    const response = await request(app).post('/sessions').send({
      email: 'wesley@github.com',
      password: 'super-secret-password',
    });

    expect(response.status).toBe(200);
  });

  it('should not authenticate with invalid email', async () => {
    await factories.create('User');

    const response = await request(app).post('/sessions').send({
      email: 'anonymous@github.com',
      password: 'invalid-password',
    });

    expect(response.status).toBe(401);
  });

  it('should not authenticate with invalid password', async () => {
    await factories.create('User', {
      email: 'wesley@github.com',
    });

    const response = await request(app).post('/sessions').send({
      email: 'wesley@github.com',
      password: 'invalid-password',
    });

    expect(response.status).toBe(401);
  });

  it('should return JWT token when authenticated', async () => {
    await factories.create('User', {
      email: 'wesley@github.com',
      password: 'super-secret-password',
    });

    const response = await request(app).post('/sessions').send({
      email: 'wesley@github.com',
      password: 'super-secret-password',
    });

    expect(response.status).toBe(200);
    expect(response.body).toHaveProperty('token');
  });

  it('should be able to access private private routes when authenticated', async () => {
    await factories.create('User', {
      email: 'wesley@github.com',
      password: 'super-secret-password',
    });

    const authResponse = await request(app).post('/sessions').send({
      email: 'wesley@github.com',
      password: 'super-secret-password',
    });

    const token = authResponse.body.token;

    const response = await request(app).get('/dashboard').set('Authorization', `Bearer ${token}`);

    expect(response.status).toBe(200);
  });

  it('should not be able to access private private routes without a token', async () => {
    const response = await request(app).get('/dashboard');

    expect(response.status).toBe(401);
  });

  it('should not be able to access private private routes with an invalid token', async () => {
    const response = await request(app).get('/dashboard').set('Authorization', 'Bearer invalid-token');

    expect(response.status).toBe(401);
  });
});
