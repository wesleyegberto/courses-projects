import { Injectable, EventEmitter } from '@angular/core';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';

import { Usuario } from './usuario';

@Injectable()
export class AuthService {
  autenticacaoEmitter = new EventEmitter<boolean>();

  constructor() { }

  autentica(usuario: Usuario): Observable<boolean> {
    let usuarioValido = false;
    if (usuario.email === 'odair@mail.com' && usuario.senha === '123') {
      usuarioValido = true;
    }
    this.autenticacaoEmitter.emit(usuarioValido);
    return Observable.of(usuarioValido);
  }
}
