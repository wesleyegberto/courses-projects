import React, { Component } from 'react';
import Foto from './Foto';

export default class Timeline extends Component {

	constructor(props) {
		super(props);
		this.state = {
			fotos: []
		};
		this.login = this.props.login;
	}

	carregaFotos() {
		let urlPerfil;
		if (this.login === undefined) {
			urlPerfil = `http://localhost:8080/api/fotos?X-AUTH-TOKEN=${localStorage.getItem('auth-token')}`;
		} else {
			urlPerfil = `http://localhost:8080/api/public/fotos/${this.login}`;
		}
		fetch(urlPerfil)
			.then(response => response.json())
			.then(fotos => this.setState({ fotos: fotos }))
			.catch(err => {
				console.log(err);
				this.setState({ fotos: [] });
			});
	}

	componentDidMount() {
		this.carregaFotos();
	}

	componentWillReceiveProps(nextProps) {
		if (nextProps.login !== undefined) {
			this.login = nextProps.login;
			this.carregaFotos();
		}
	}

	render() {
		return (
			<div className="fotos container">
				{this.state.fotos.map(ft => <Foto key={ft.id} foto={ft} />)}
			</div>
		);
	}
}