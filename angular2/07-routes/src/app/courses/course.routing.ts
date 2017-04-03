import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CourseGuard } from './guard/course.guard';
import { CoursesComponent } from './courses.component';
import { CourseDetailsComponent } from './course-details/course-details.component';
import { CourseFormComponent } from 'app/courses/course-form/course-form.component';
import { CourseNotFoundComponent } from './course-not-found/course-not-found.component';

const COURSES_ROUTES: Routes = [
  // Guard will protect only the children, the CoursesComponent won't be protected
  // to protect all we must use Guard at Module that import CourseRoutingModule
  { path: '', component: CoursesComponent, canActivateChild: [ CourseGuard ], children: [
    { path: 'course-not-found/:id', component: CourseNotFoundComponent },
    { path: ':id', component: CourseDetailsComponent },
    { path: ':id/edit', component: CourseFormComponent }
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(COURSES_ROUTES)],
  exports: [RouterModule]
})
export class CourseRouting {}
