import { Injectable } from '@angular/core';
import { CanDeactivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { Observable } from 'rxjs/Observable';

import { FormCanDeactivate } from './form.candeactivate';

@Injectable()
export class FormDeactivateGuard implements CanDeactivate<FormCanDeactivate> {

  canDeactivate(component: FormCanDeactivate, route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> | boolean {
    console.log('[FormDeactivateGuard] Verifying if it can deactivate');
    return component.canDeactivate();
  }
}
