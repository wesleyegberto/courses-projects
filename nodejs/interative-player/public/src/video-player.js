class VideoPlayer {
	constructor({ network, manifestJson, videoComponent }) {
		this.network = network;
		this.manifestJson = manifestJson;
		this.videoComponent = videoComponent;
		this.videoElement = null;
		this.sourceBuffer = null;
		this.videoDuration = 0;
		this.activeItem = {};
		this.selections = [];
	}

	initializeCodec() {
		this.videoElement = document.getElementById('vid');

		const mediaSourceSupported = !!window.MediaSource;
		if (!mediaSourceSupported) {
			alert('Seu browser ou sistema não tem suporte ao MP4');
			return;
		}
		const codecSupported = MediaSource.isTypeSupported(this.manifestJson.codec);
		if (!codecSupported) {
			alert(`Seu browser não suporta o codec ${this.manifestJson.codec}`);
			return;
		}

		console.log('Inicializando buffer');
		const mediaSource = new MediaSource();
		this.videoElement.src = URL.createObjectURL(mediaSource);

		mediaSource.addEventListener('sourceopen', this.sourceOpenWrapper(mediaSource));
	}

	sourceOpenWrapper(mediaSource) {
		return async (_) => {
			console.log('Carregado');
			this.sourceBuffer = mediaSource.addSourceBuffer(this.manifestJson.codec);
			const selected = this.selected = this.manifestJson.intro;
			// evitar rodar como Live
			mediaSource.duration = this.videoDuration;
			await this.fileDownload(selected.url);
			setInterval(this.waitForQuestions.bind(this), 200);
		};
	}

	waitForQuestions() {
		// trunca para obter os segundos
		const currentTime = parseInt(this.videoElement.currentTime);
		const timeToSelect = this.selected.at === currentTime;
		if (!timeToSelect) {
			return;
		}
		// evitar que a modal seja exibida 2x
		if (this.activeItem.url === this.selected.url) {
			return;
		}
		this.videoComponent.configureModal(this.selected);
		this.activeItem = this.selected;
	}

	async nextChunk(data) {
		const key = data.toLowerCase();
		const selected = this.manifestJson[key];
		this.selected = {
			...selected,
			// adiciona o offset o tempo atual com a duração do novo buffer
			at: parseInt(this.videoElement.currentTime + selected.at)
		};
		this.manageLag(selected);

		// deixa o restante do video rodar enquanto baixamos a próxima parte
		this.videoElement.play();
		await this.fileDownload(this.selected.url);
	}

	manageLag(selected) {
		if (!!~this.selections.indexOf(selected.url)) {
			selected.at += 5;
			return;
		}
		this.selections.push(selected.url);
	}

	async fileDownload(url) {
		const fileResolution = await this.currentFileResolution();
		console.log('Carregando resolução', fileResolution);
		const prepareUrl = {
			url,
			fileResolution: fileResolution,
			fileResolutionTag: this.manifestJson.fileResolutionTag,
			hostTag: this.manifestJson.hostTag
		};
		const finalUrl = this.network.parseManifestUrl(prepareUrl);
		console.log('Proxima parte', finalUrl);
		this.setVideoPlayerDuration(finalUrl);
		const data = await this.network.fetchFile(finalUrl);
		this.processBufferSegments(data);
	}

	async currentFileResolution() {
		const LOWEST_RESOLUTION = 144;
		const prepareUrl = {
			url: this.manifestJson.encerramento.url,
			fileResolution: LOWEST_RESOLUTION,
			fileResolutionTag: this.manifestJson.fileResolutionTag,
			hostTag: this.manifestJson.hostTag
		};
		const url = this.network.parseManifestUrl(prepareUrl);
		return await this.network.getProperResolution(url);
	}

	setVideoPlayerDuration(finalUrl) {
		const bars = finalUrl.split('/');
		const [_, videoDuration] = bars[bars.length - 1].split('-');
		this.videoDuration += parseFloat(videoDuration);
		console.log('Aumento duração com', this.videoDuration, 's');
	}

	async processBufferSegments(allSegments) {
		const sourceBuffer = this.sourceBuffer;
		sourceBuffer.appendBuffer(allSegments);

		return new Promise((resolve, reject) => {
			const updateEnd = () => {
				sourceBuffer.removeEventListener('updateend', updateEnd);
				sourceBuffer.timestampOffset = this.videoDuration;
				return resolve();
			};
			sourceBuffer.addEventListener('updateend', updateEnd);
			sourceBuffer.addEventListener('error', reject);
		});
	}

}