import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

// import { routing } from './app.routing';
import { PageNotFoundModule } from './page-not-found/page-not-found.module';
import { AppRoutingModule } from './app.routing';
import { AuthGuard } from './guard/auth.guard';
import { FormDeactivateGuard } from './form-state-guard/form.deactivate.guard';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';

import { AuthService } from './login/auth.service';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    // routing
    AppRoutingModule,
    PageNotFoundModule
    // Tirado para poder fazer lazy load
    // CourseRoutingModule,
    // StudentsRoutingModule,
    // CourseModule,
    // StudentsModule
  ],
  providers: [ AuthService, AuthGuard ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
