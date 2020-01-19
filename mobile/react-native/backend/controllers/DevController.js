const axios = require('axios');
const Dev = require('../models/Dev');
const parseStringAsArray = require('../utils/parseStringAsArray');

const { findConnections, sendMessage } = require('../websocket');

module.exports = {
  getAll: async (request, response) => {
    const devs = await Dev.find();
    return response.json(devs);
  },

  save: async (request, response) => {
    const { github_username, techs, latitude, longitude } = request.body;

    const apiResponse = await axios.get(`https://api.github.com/users/${github_username}`);

    const { name = login, avatar_url, bio } = apiResponse.data;

    const techsArray = parseStringAsArray(techs);

    const location = {
      type: 'Point',
      coordinates: [longitude, latitude]
    };

    const dev = await Dev.create({
      github_username,
      name,
      avatar_url,
      bio,
      techs: techsArray,
      location
    });

    // find connected devices to notify the created dev
    const socketsToNotify = findConnections({ latitude, longitude }, techsArray);
    if (socketsToNotify && socketsToNotify.length) {
      sendMessage(socketsToNotify, 'NewDev', dev);
    }

    return response.json(dev);
  }
};