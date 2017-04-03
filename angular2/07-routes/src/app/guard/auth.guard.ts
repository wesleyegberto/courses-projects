import { Injectable } from '@angular/core';
import { Route, CanLoad, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';

import { Observable } from 'rxjs/Observable';

import { AuthService } from './../login/auth.service';

@Injectable()
export class AuthGuard implements CanLoad, CanActivate {

  constructor(private _router: Router, private _authService: AuthService) { }

  canLoad(route: Route): boolean | Observable<boolean> {
    console.log('[AuthGuard] can load');
    return this.isAuthenticated();
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {
    console.log('[AuthGuard] can activate');
    return this.isAuthenticated();
  }

  private isAuthenticated(): boolean {
    if (this._authService.isUserAuthenticated()) {
      return true;
    }
    this._router.navigate(['/login']);
    return false;
  }
}
