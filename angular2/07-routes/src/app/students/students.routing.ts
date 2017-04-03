import { StudentGuardResolver } from './guard/student.guard.resolver';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { FormDeactivateGuard } from '../form-state-guard/form.deactivate.guard';
import { StudentsComponent } from './students.component';
import { StudentsDetailsComponent } from './students-details/students-details.component';
import { StudentsFormComponent } from './students-form/students-form.component';

const STUDENTS_ROUTES: Routes = [
  { path: '', component: StudentsComponent, children: [
    { path: 'new', component: StudentsFormComponent },
    { path: ':id', component: StudentsDetailsComponent },
    { path: ':id/edit', component: StudentsFormComponent,
      canDeactivate: [ FormDeactivateGuard ], // to verify if it can leave the component
      resolve: { student: StudentGuardResolver } // using a resolver to load before the view (will load 'together')
    }
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(STUDENTS_ROUTES)],
  exports: [RouterModule]
})
export class StudentsRouting { }
