import { Injectable } from "@angular/core";
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import 'rxjs/add/operator/map';
import { Foto } from "./foto";

@Injectable()
export class FotoService {
  private BASE_URL = '/v1/fotos';

  // podemos injetar usando @Inject(<tipo>) ou tipando a var
  constructor(private _http: Http) { }

  private criaHeadersParaJson() {
    const headers = new Headers();
    headers.append("Content-Type", "application/json");
    return { headers: headers };
  }

  carregaFotos(): Observable<Foto[]> {
    return this._http.get(this.BASE_URL)
                    .map(resp => resp.json());
  }

  salvaFoto(foto: Foto): Observable<RetornoService> {
    if(foto._id) {
      return this._http
        .put(`${this.BASE_URL}/${foto._id}`, JSON.stringify(foto), this.criaHeadersParaJson())
        .map(() => new RetornoService(false, 'Foto alterada com sucesso.'));
    } else {
      return this._http
        .post(this.BASE_URL, JSON.stringify(foto), this.criaHeadersParaJson())
        .map(() => new RetornoService(true, 'Foto incluída com sucesso.'));
    }
  }

  pesquisaFotoPorId(id): Observable<Foto> {
    return this._http
      .get(`${this.BASE_URL}/${id}`)
      .map(resp => resp.json());
  }

  removeFoto(foto: Foto): Observable<Response> {
    return this._http
      .delete(`${this.BASE_URL}/${foto._id}`);
  }
}

export class RetornoService {
  constructor(private _inclusao, private _mensagem) {}

  get inclusao(): boolean {
    return this._inclusao;
  }

  get mensagem(): string {
    return this._mensagem;
  }
}