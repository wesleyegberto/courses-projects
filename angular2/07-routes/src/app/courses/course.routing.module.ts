import { ModuleWithProviders, NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CourseDetailsComponent } from './course-details/course-details.component';
import { CoursesComponent } from './courses.component';
import { CourseNotFoundComponent } from './course-not-found/course-not-found.component';

const COURSES_ROUTES: Routes = [
    { path: 'courses', component: CoursesComponent },
    { path: 'course/:id', component: CourseDetailsComponent },
    { path: 'course-not-found/:id', component: CourseNotFoundComponent }
];

@NgModule({
    imports: [RouterModule.forChild(COURSES_ROUTES)],
    exports: [RouterModule]
})
export class CourseRoutingModule {}
