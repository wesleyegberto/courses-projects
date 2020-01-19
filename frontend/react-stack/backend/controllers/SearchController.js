const Dev = require('../models/Dev');
const parseStringAsArray = require('../utils/parseStringAsArray');

module.exports = {
  find: async(request, response) => {
    const { latitude, longitude, techs } = request.query;

    if (latitude == undefined || longitude == undefined) {
      return response.json({ error: 'Provide your location' });
    }

    const techsArray = parseStringAsArray(techs);

    const devs = await Dev.find({
      techs: {
        $in: techsArray
      },
      location: {
        $near: {
          $geometry: {
            type: 'Point',
            coordinates: [longitude, latitude]
          },
          $maxDistance: 10000 // 10km
        }
      }
    });

    return response.json({ devs });
  }
};
