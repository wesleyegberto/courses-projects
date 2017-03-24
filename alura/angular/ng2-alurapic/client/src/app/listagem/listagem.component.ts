import { Component, Inject, OnInit } from '@angular/core';
import { Http } from '@angular/http';

import { FotoService } from "../foto/foto.service";
import { Foto } from "../foto/foto";

@Component({
    moduleId: module.id,
    selector: 'listagem',
    templateUrl: 'listagem.component.html'
})
export class ListagemComponent implements OnInit {
  // fotos: Array<Object> = [];
  fotos: Foto[] = [];

	mensagem: string;
	ocorreuErro: boolean;

  constructor(private fotoService: FotoService) {}

  ngOnInit() {
    this.fotoService.carregaFotos()
      .subscribe(fotos => {
        this.fotos = fotos;
        this.ocorreuErro = false;
        this.mensagem = null;
      }, err => {
        console.log(err);
        this.ocorreuErro = true;
        this.mensagem = 'Ocorreu um erro ao carregar as fotos.';
      });
  }

  remove(foto: Foto) {
    console.log(foto);
    this.fotoService.removeFoto(foto)
        .subscribe(() => {
          this.fotos = this.fotos.filter(ft => ft._id != foto._id);
          this.ocorreuErro = false;
          this.mensagem = 'Foto removida com sucesso.';
        }, err => {
          console.log(err);
          this.ocorreuErro = true;
          this.mensagem = 'Ocorreu um erro ao remover a foto.';
      });
  }
}
