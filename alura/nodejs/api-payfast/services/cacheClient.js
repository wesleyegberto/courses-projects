const memcached = require('memcached');

// create a class to encapsulate the memcached
class CacheClient {

	constructor() {
		// create the client setting some options
		this._client = new memcached('localhost:11211', {
			retries: 5, // retries before assume doesn't havei $timeout(function(){
			retry: 10000, // when a node doesn't awnser will wait at least ms to try again
			remove: true // when a node doesn't awnser it will be removed
		});
	}

	set(key, value, ttl = 60000, callbackError) {
		this._client.set(key, value, ttl, function(err) {
			if (err) {
				if (typeof(callbackError) == typeof(Function))
					callbackError(err);
				else
					console.log('An error has occured: ' + JSON.stringify(err));
			}
		});
	}

	get(key, callback) {
		this._client.get(key, function(err, result) {
			callback(err, result);
		});
	}
}

module.exports = function() {
	return function() {
		return new CacheClient();
	};
};