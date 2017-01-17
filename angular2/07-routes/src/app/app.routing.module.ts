import { ModuleWithProviders, NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';

const APP_ROUTES: Routes = [
    // Lazy load
    { path: 'courses', loadChildren: 'app/courses/course.module#CourseModule' },
    { path: 'students', loadChildren: 'app/students/students.module#StudentsModule' },
    { path: 'login', component: LoginComponent },
    { path: '', component: HomeComponent }  
];

// export const routing: ModuleWithProviders = RouterModule.forRoot(APP_ROUTES)

@NgModule({
    imports: [RouterModule.forRoot(APP_ROUTES)],
    exports: [RouterModule]
})
export class AppRoutingModule {}
