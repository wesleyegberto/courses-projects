import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Usuario } from './usuario';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  usuario = new Usuario();
  message: string;

  constructor(private route: Router, private _authService: AuthService) { }

  ngOnInit() {
  }

  autenticaUsuario() {
    console.log(this.usuario);
    this._authService.autentica(this.usuario)
      .subscribe(usuarioValido => {
        if (usuarioValido) {
          this.route.navigate(['/']);
        } else {
          this.message = 'Usuário e/ou senha inválidos.';
        }
      });
  }
}
