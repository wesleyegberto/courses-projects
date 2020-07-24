// to run `sequelize` from terminal
require('dotenv').config({
  path: process.env.NODE_ENV === 'test' ? '.env.test' : '.env',
});

module.exports = {
  host: process.env.DB_HOST,
  username: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME,
  dialect: process.env.DB_DIALECT,
  storage: './tests/database.sqlite',
  operatorsAliases: false,
  logging: false,
  define: {
    timestamps: true, // add field `createdAt` and `updatedAt`
    underscored: true, // use _ on table name (like `Users_Groups`)
    underscoredAll: true, // use _ on field name (like `User_Id`)
  },
};
