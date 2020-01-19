const socketio = require('socket.io');
const parseStringAsArray = require('./utils/parseStringAsArray');
const calculateCoordinatesDistanceInKm = require('./utils/calculateCoordinatesDistanceInKm');

const connections = [];

let io;

exports.setupWebsocket = (server) => {
	io = socketio(server);

	io.on('connection', socket => {
		console.log('Websokect id:', socket.id);
		console.log('Websocket query:', socket.handshake.query);

		const { latitude, longitude, techs } = socket.handshake.query;

		connections.push({
			id: socket.id,
			coordinates: {
				latitude: Number(latitude),
				longitude: Number(longitude)
			},
			techs: parseStringAsArray(techs)
		});
	});
};

exports.findConnections = (coordinates, techs) => {
	return connections.filter(conn => {
		return calculateCoordinatesDistanceInKm(coordinates, conn.coordinates) < 10
			&& conn.techs.some(item => techs.includes(item));
	})
};

exports.sendMessage = (to, event, data) => {
	to.forEach(conn => {
		io.to(conn.id).emit(event, data);
	});
};