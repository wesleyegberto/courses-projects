import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import MapView from 'react-native-maps';
import { requestPermissionsAsync, getCurrentPositionAsync } from 'expo-location';

function Main() {
	const [currengRegion, setCurrentRegion] = useState(null);

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

	// only display the map if we could get the current position
	if (!currengRegion) {
		return (
			<View>
				<Text>Enable or wait for GPS to continue</Text>
			</View>
		);
	}

	return (
		<MapView style={styles.map} initialRegion={currengRegion} />
	);
}

const styles = StyleSheet.create({
  map: {
    flex: 1
  }
});

export default Main;