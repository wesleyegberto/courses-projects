import { ModuleWithProviders, NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './auth/auth.guard';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';

const APP_ROUTES: Routes = [
    {
        path: 'courses', canActivate: [ AuthGuard ] , // AuthGuard só permite acesso se retorna true
        loadChildren: 'app/courses/course.module#CourseModule' // Lazy load
    },
    {
        path: 'students', canActivate: [AuthGuard],
        loadChildren: 'app/students/students.module#StudentsModule'
    },
    { path: 'login', component: LoginComponent },
    { path: '', component: HomeComponent, canActivate: [AuthGuard] }
];

// export const routing: ModuleWithProviders = RouterModule.forRoot(APP_ROUTES)

@NgModule({
    imports: [RouterModule.forRoot(APP_ROUTES)],
    exports: [RouterModule]
})
export class AppRoutingModule {}
