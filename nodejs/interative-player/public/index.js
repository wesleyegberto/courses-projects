const MANIFEST_JSON = 'manifest.json';
const localHost = ['127.0.0.1', 'localhost'];

async function main() {
	const isLocal = !!~localHost.indexOf(window.location.hostname);
	const manifestJson = await (await fetch(MANIFEST_JSON)).json();
	const host = isLocal ? manifestJson.localHost : manifestJson.productionHost;

	const network = new Network({
		host,
		resolutions: manifestJson.resolutions,
		lowestResolution: manifestJson.lowestResolution
	});

	const videoComponent = new VideoComponent();
	const videoPlayer = new VideoPlayer({
		network,
		manifestJson,
		videoComponent
	});
	videoPlayer.initializeCodec();
	videoComponent.initializePlayer();

	// usar a main para vincular eventos no main
	window.nextChunk = data => videoPlayer.nextChunk(data);
}

window.onload = main;
