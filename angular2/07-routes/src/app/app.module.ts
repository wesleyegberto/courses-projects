import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

// import { routing } from './app.routing';
import { AppRoutingModule } from './app.routing.module';
import { CourseModule } from './courses/course.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';

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
    CourseModule
  ],
  providers: [],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
