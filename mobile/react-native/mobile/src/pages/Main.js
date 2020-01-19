import React, { useState, useEffect } from 'react';
import { View, Text, Image, StyleSheet, TextInput } from 'react-native';
import { TouchableOpacity } from 'react-native-gesture-handler';
import MapView, { Marker, Callout } from 'react-native-maps';
import { requestPermissionsAsync, getCurrentPositionAsync } from 'expo-location';
import { MaterialIcons } from '@expo/vector-icons';

import api from '../services/api';
import { connect, disconnect, subscribeToNewDevs } from '../services/socket';

function Main({ navigation }) {
	const [currentRegion, setCurrentRegion] = useState(null);
	const [devs, setDevs] = useState([]);
	const [techs, setTechs] = useState('');

	useEffect(() => {
		async function loadInitialPosition() {
			const { granted } = await requestPermissionsAsync();

			if (!granted) {
				return;
			}

			const { coords } = await getCurrentPositionAsync({
				enableHighAccuracy: true // will use mobile GPS (otherwise use WiFi or 3G/4G)
			});
			const { latitude, longitude } = coords;
			
			setCurrentRegion({
				latitude,
				longitude,
				latitudeDelta: 0.02,
				longitudeDelta: 0.02
			});
		}

		loadInitialPosition();
	}, []);

	useEffect(() => {
		subscribeToNewDevs(dev => setDevs([...devs, dev]));
	}, [devs]);

	// only display the map if we could get the current position
	if (!currentRegion) {
		return warningMessage();
	}

	async function loadDevs() {
		const { latitude, longitude } = currentRegion;

		const response = await api.get('/devs/search', {
			params: {
				latitude,
				longitude,
				techs
			}
		});
		setDevs(response.data.devs || []);
		setupWebsocket();
	}

	function setupWebsocket() {
		disconnect();

		const { latitude, longitude } = currentRegion;

		connect(latitude, longitude, techs);
	}

	async function handleRegionChanged(region) {
		setCurrentRegion(region);
		// await loadDevs();
	}

	function goToProfile(githubUsername) {
		navigation.navigate('Profile', { github_username: githubUsername });
	}

	return (
		<>
			<MapView
				style={styles.map}
				initialRegion={currentRegion}
				onRegionChangeComplete={handleRegionChanged}
			>
				{devs.map(dev => (
					<Marker
						key={dev._id}
						coordinate={{
							longitude: dev.location.coordinates[0],
							latitude: dev.location.coordinates[1]
						}}
					>
						<Image
							style={styles.avatar}
							source={{ uri: dev.avatar_url }}
						/>

						<Callout onPress={() => goToProfile(dev.github_username)}>
							<View style={styles.callout}>
								<Text style={styles.devName}>{dev.name}</Text>
								<Text style={styles.devBio}>{dev.bio}</Text>
								<Text style={styles.devTechs}>{dev.techs.join(', ')}</Text>
							</View>
						</Callout>
					</Marker>
				))}
			</MapView>

			<View style={styles.searchForm}>
				<TextInput
					style={styles.searchInput}
					placeholder="Search for devs by techs"
					placeholderTextColor="#999"
					autoCapitalize="words"
					autoCorrect={false}
					value={techs}
					onChangeText={setTechs}
				/>

				<TouchableOpacity style={styles.loadButton} onPress={loadDevs}>
					<MaterialIcons name="my-location" size={20} color="#fff" />
				</TouchableOpacity>
			</View>
		</>
	);
}

function warningMessage() {
	return (
		<View>
			<Text>Enable or wait for GPS to continue</Text>
		</View>
	);
}

const styles = StyleSheet.create({
  map: {
    flex: 1
	},

	avatar: {
		width: 54,
		height: 54,
		borderRadius: 4,
		borderWidth: 4,
		borderColor: '#fff'
	},

	callout: {
		width: 250
	},
	devName: {
		fontWeight: 'bold',
		fontSize: 16
	},
	devBio: {
		color: '#666',
		marginTop: 5
	},
	devtechs: {
		marginTop: 5
	},

	searchForm: {
		position: 'absolute',
		top: 20, // for while
		// bottom: 20, to use this we need to list to `Keyboard.addListener` so we can recalculate the bottom
		left: 20,
		right: 20,
		zIndex: 5,
		display: 'flex', // default
		flexDirection: 'row'
	},
	searchInput: {
		flex: 1,
		height: 50,
		backgroundColor: '#fff',
		color: '#333',
		borderRadius: 25,
		paddingHorizontal: 20,
		fontSize: 16,
		shadowColor: '#000',
		shadowOpacity: 0.2,
		shadowOffset: {
			width: 4,
			height: 4
		},
		elevation: 2 // Android shadow by elevation
	},
	loadButton: {
		width: 50,
		height: 50,
		backgroundColor: '#8e4dff',
		borderRadius: 25,
		justifyContent: 'center',
		alignItems: 'center',
		marginLeft: 15
	}
});

export default Main;