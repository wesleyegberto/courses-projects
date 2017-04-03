import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ModuleWithProviders, NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './guard/auth.guard';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';

const APP_ROUTES: Routes = [
    {
        path: 'courses', canActivate: [ AuthGuard ], // AuthGuard só permite acesso se retorna true
        loadChildren: 'app/courses/course.module#CourseModule', // Lazy load
        canLoad: [ AuthGuard ] // used with lazy load module
    },
    {
        path: 'students', canActivate: [AuthGuard],
        loadChildren: 'app/students/students.module#StudentsModule',
        canLoad: [ AuthGuard ]
    },
    { path: 'login', component: LoginComponent },
    { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: '**', component: PageNotFoundComponent }
];

// export const routing: ModuleWithProviders = RouterModule.forRoot(APP_ROUTES)

@NgModule({
    imports: [RouterModule.forRoot(APP_ROUTES)],
    exports: [RouterModule]
})
export class AppRoutingModule {}
