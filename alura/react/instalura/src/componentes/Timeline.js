import React, { Component } from 'react';
import Foto from './Foto';

export default class Timeline extends Component {

	constructor() {
		super();
		this.state = {
			fotos: []
		};
	}

	componentDidMount() {
		const authToken = localStorage.getItem('auth-token');
		fetch(`http://localhost:8080/api/fotos?X-AUTH-TOKEN=${authToken}`)
			.then(response => response.json())
			.then(fotos => this.setState({ fotos: fotos }))
			.catch(err => {
				console.log(err);
				this.setState({ fotos: [] });
			});
	}

	render(){
		return (
			<div className="fotos container">
				{this.state.fotos.map(ft => <Foto key={ft.id} foto={ft} />)}
			</div>
		);
	}
}