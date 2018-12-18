import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { User } from './user';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user = new User();
  message: string;

  constructor(private route: Router, private _authService: AuthService) { }

  ngOnInit() {
  }

  authenticateUser() {
    console.log(this.user);
    this._authService.autentica(this.user)
      .subscribe(usuarioValido => {
        if (usuarioValido) {
          this.route.navigate(['/']);
        } else {
          this.message = 'Usuário e/ou senha inválidos.';
        }
      });
  }
}
