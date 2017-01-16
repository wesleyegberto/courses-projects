import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CourseDetailsComponent } from './course-details/course-details.component';
import { CoursesComponent } from './courses.component';
import { CourseNotFoundComponent } from './course-not-found/course-not-found.component';

const COURSES_ROUTES: Routes = [
  { path: '', component: CoursesComponent, children: [
    { path: 'course-not-found/:id', component: CourseNotFoundComponent },
    { path: ':id', component: CourseDetailsComponent }
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(COURSES_ROUTES)],
  exports: [RouterModule]
})
export class CourseRoutingModule {}
