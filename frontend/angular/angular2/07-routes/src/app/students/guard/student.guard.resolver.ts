import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { Observable } from 'rxjs/Observable';

import { Student } from '../student';
import { StudentService } from './../student.service';

@Injectable()
export class StudentGuardResolver implements Resolve<Student> {
  constructor(private _studentService: StudentService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Student> {
    console.log('[StudentGuardResolver] resolve');
    return Observable.of(this._studentService.getStudent(route.params['id']));
  }
}
