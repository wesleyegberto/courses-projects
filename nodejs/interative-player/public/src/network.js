class Network {
	constructor({ host, resolutions, lowestResolution }) {
		this.host = host;
		this.resolutions = resolutions;
		this.lowestResolution = lowestResolution;
	}

	parseManifestUrl({ url, fileResolution, fileResolutionTag, hostTag }) {
		return url.replace(fileResolutionTag, fileResolution)
			.replace(hostTag, this.host);
	}

	async fetchFile(url) {
		const response = await fetch(url);
		return response.arrayBuffer();
	}

	async getProperResolution(url) {
		const startMS = Date.now();
		await this.fetchFile(url);
		const endMS = Date.now();

		const durationMS = (endMS - startMS);
		console.log('Ping', durationMS, 'ms');

		const item = this.resolutions.find(item => {
			return item.start <= durationMS && item.end >= durationMS;
		});
		if (!item)
			return this.lowestResolution;
		return item.resolution;
	}
}