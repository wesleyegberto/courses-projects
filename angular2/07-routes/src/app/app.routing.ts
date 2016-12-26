import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { CourseDetailsComponent } from './course-details/course-details.component';
import { CoursesComponent } from './courses/courses.component';
import { CourseNotFoundComponent } from './course-not-found/course-not-found.component';
import { LoginComponent } from './login/login.component';

const APP_ROUTES: Routes = [
    { path: 'courses', component: CoursesComponent },
    { path: 'course/:id', component: CourseDetailsComponent },
    { path: 'course-not-found/:id', component: CourseNotFoundComponent },
    { path: 'login', component: LoginComponent },
    { path: '', component: HomeComponent }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(APP_ROUTES)