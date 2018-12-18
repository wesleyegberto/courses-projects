import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app.routing.module';
import { WelcomeComponent } from './home/welcome.component';
import { EventDetailComponent } from './events/event-detail.component';
import { EventListComponent } from './events/event-list.component';
import { ThumbComponent } from './shared/thumb.component';
import { EventService } from './events/event.service';
import { EventFilterPipe } from './events/event-filter.pipe';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule,
    AppRoutingModule
  ],
  declarations: [
    AppComponent,
    WelcomeComponent,
    EventListComponent,
    EventDetailComponent,
    ThumbComponent,
    EventFilterPipe
  ],
  providers: [ EventService ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
