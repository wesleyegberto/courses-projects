const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const routes = require('./routes');

const app = express();

// activates JSON to all requests
app.use(express.json());
// would active JSON for only GET requests
// app.get(express.json());

app.use(cors({
  origin: 'http://localhost:3000'
}));
app.use(routes);

mongoose.connect('mongodb://root:supersecret@localhost?retryWrites=true&w=majority', {
	useNewUrlParser: true,
	useUnifiedTopology: true
});

app.listen(3333);
