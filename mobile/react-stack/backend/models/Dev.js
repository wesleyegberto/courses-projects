const mongoose = require('mongoose');

const PointSchema = new mongoose.Schema({
  type: {
    type: String,
    enum: ['Point'],
    required: true
  },
  coordinates: {
    type: [Number], // [long, lat]
    required: true
  }
});

// defines a Mongodb's collection schema
const DevSchema = new mongoose.Schema({
	name: String,
	github_username: String,
	bio: String,
	avatar_url: String,
	techs: [String],
  location: {
    type: PointSchema,
    index: '2dsphere'
  }
});

module.exports = mongoose.model('Dev', DevSchema);

