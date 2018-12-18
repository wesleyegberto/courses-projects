# API Payfast

Simple API using Node

### Modules
* Express
* Express-Validator
* Body-Parser
* Consign
* Morgan
* Winston
* Restify
* SOAP
* MySQL
* Memcached
* OS
* Cluster
* PM2


### Table
```
CREATE TABLE Payment (
  id INT PRIMARY KEY AUTO_INCREMENT,
  type VARCHAR(20),
  card_number CHAR(20),
  amount DECIMAL(10,2),
  currency VARCHAR(5),
  description VARCHAR(250),
  status VARCHAR(20),
  date DATETIME
);
```

* Create a payment
```
curl -X POST http://localhost:3000/payments -H "Content-Type: application/json" -d @files/payment.json
```

* Pay
```
curl -X PUT http://localhost:3000/payments/:id
```

* Get
```
curl http://localhost:3000/payments/:id
```

* Cancel
```
curl -X DELETE http://localhost:3000/payments/:id
```

* Upload image
```
curl -X POST http://localhost:3000/upload/image -H "Content-Type: application/octet-stream" -H "filename: smith.jpg"  --data-binary @misc/smith.jpg
```