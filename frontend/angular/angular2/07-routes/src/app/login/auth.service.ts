import { Injectable, EventEmitter } from '@angular/core';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';

import { User } from './user';

@Injectable()
export class AuthService {
  private userAuthenticated: true;
  authenticationEmitter = new EventEmitter<boolean>();

  constructor() { }

  autentica(user: User): Observable<boolean> {
    if (user.email === 'user@email.com' && user.password === '123') {
      this.userAuthenticated = true;
    }
    this.authenticationEmitter.emit(this.userAuthenticated);
    return Observable.of(this.userAuthenticated);
  }

  isUserAuthenticated() {
    return this.userAuthenticated;
  }
}
