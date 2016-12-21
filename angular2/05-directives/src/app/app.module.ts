import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { YellowBackgroundDirective } from './directives/yellow-background.directive';
import { MouseEventsDirective } from './directives/mouse-events.directive';
import { HighlightDirective } from './directives/highlight.directive';
import { NgElseDirective } from './directives/ng-else.directive';

@NgModule({
  declarations: [
    AppComponent,
    YellowBackgroundDirective,
    MouseEventsDirective,
    HighlightDirective,
    NgElseDirective
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
