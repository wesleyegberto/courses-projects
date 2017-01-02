import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { StudentsComponent } from './students.component';
import { StudentsDetailsComponent } from './students-details/students-details.component';
import { StudentsFormComponent } from './students-form/students-form.component';

const STUDENTS_ROUTES: Routes = [
  { path: 'students', component: StudentsComponent, children: [
    { path: 'new', component: StudentsFormComponent },
    { path: ':id', component: StudentsDetailsComponent },
    { path: ':id/edit', component: StudentsFormComponent }
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(STUDENTS_ROUTES)],
  exports: [RouterModule]
})
export class StudentsRoutingModule { }
