const bcrypt = require('bcryptjs');

const { User } = require('../../src/app/models');

const truncate = require('../utils/truncate');

describe('User', () => {
  beforeEach(async () => {
    await truncate();
  });

  it('should create an use', async () => {
    // example without use factory
    const user = await User.create({
      name: 'wesley',
      email: 'wesley@github.com',
      password_hash: 'super-secret-password',
    });

    expect(user.id).toBeGreaterThan(0);
  });

  it('should encrypt user password', async () => {
    const user = await User.create({
      name: 'Wesley',
      email: 'wesley@github.com',
      password: 'secret',
    });

    const isEquals = await bcrypt.compare('secret', user.password_hash);

    expect(isEquals).toBeTruthy();
  });
});
