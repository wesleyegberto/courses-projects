import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";

import { FotoService } from "../foto/foto.service";
import { Foto } from "../foto/foto";

@Component({
	moduleId: module.id,
	selector: 'cadastro',
	templateUrl: 'cadastro.component.html'
})
export class CadastroComponent implements OnInit {
	foto: Foto = new Foto();
	frmGrpFoto: FormGroup;

	mensagem: string;
	ocorreuErro: boolean;

	constructor(private _fotoService: FotoService, private _formBuilder: FormBuilder,
							private _router: Router, routes: ActivatedRoute) {
		// observable para o parâmetro
		routes.params.subscribe(params => {
			let id = params['id'];
			if(id) {
				this._fotoService.pesquisaFotoPorId(id)
					.subscribe(foto => {
						this.foto = foto;
						this.ocorreuErro = false;
						this.mensagem = '';
					}, err => {
						console.log(err);
						this.ocorreuErro = true;
						this.mensagem = 'Ocorreu um erro ao carregar a foto.';
					});
			}
		});
	}

	ngOnInit() {
		// controi o grupo de FormControl com suas validações
		this.frmGrpFoto = this._formBuilder.group({
			titulo: [this.foto.titulo, Validators.compose([Validators.required, Validators.minLength(4)])],
			url: [this.foto.url, Validators.required],
			descricao: [this.foto.descricao]
		});
	}

	cadastra(event) {
		// cancela o submit
		event.preventDefault();
		console.log(this.foto);

		this._fotoService.salvaFoto(this.foto)
			.subscribe(retorno => {
				if(retorno.inclusao) {
					this.foto = new Foto();
					this.ocorreuErro = false;
					this.mensagem = retorno.mensagem;
				} else {
					this._router.navigate(['/']);
				}
			}, err => {
				console.log(err);
				this.ocorreuErro = true;
				this.mensagem = 'Ocorreu um erro ao salvar a foto.';
			});
	}
}
