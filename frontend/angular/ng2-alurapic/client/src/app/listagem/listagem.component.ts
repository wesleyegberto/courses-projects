import { Component, Inject, OnInit } from '@angular/core';
import { Http } from '@angular/http';

import { Foto } from "../foto/foto";
import { FotoService } from "../foto/foto.service";
import { PainelComponent } from "../painel/painel.component";

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

  remove(foto: Foto, painel: PainelComponent) {
    this.fotoService.removeFoto(foto)
        .subscribe(() => {
          // chama o fadeOut que usa o jQuery e depois executa nosso callback
          painel.fadeOut(() => {
            this.fotos = this.fotos.filter(ft => ft._id != foto._id);
            this.ocorreuErro = false;
            this.mensagem = 'Foto removida com sucesso.';
          });
        }, err => {
          console.log(err);
          this.ocorreuErro = true;
          this.mensagem = 'Ocorreu um erro ao remover a foto.';
      });
  }
}
