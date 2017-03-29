# API Payfast

Simple API using Node

### Modules
* Express
* Body-Parser
* Consign
* MySQL


### Table
```
CREATE TABLE Payment (
  id INT PRIMARY KEY AUTO_INCREMENT,
  type VARCHAR(20),
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

* Cancel
```
curl -X DELETE http://localhost:3000/payments/:id
```
