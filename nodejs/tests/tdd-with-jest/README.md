# NodeJS: TDD with Jest

## Setup

Run database:

```bash
docker run -d --name postgres-db -e POSTGRES_USER=docker -e POSTGRES_PASSWORD=docker -e POSTGRES_DB=tdd-dev -p 5432:5432 postgres:12
```

## Libs

### Sequelize

#### Migrations

To create a migration:

```bash
npx sequelize migration:create --name=create-users
```

Run the migration:

```bash
npx sequelize db:migrate
```