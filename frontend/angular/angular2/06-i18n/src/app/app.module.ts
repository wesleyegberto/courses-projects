import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { SettingsService } from './settings.service';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [
    SettingsService,
    /*{
      provide: LOCALE_ID,
      useValue: 'pt-BR'
    },*/
    {
      provide: LOCALE_ID,
      deps: [ SettingsService ],
      useFactory: (settsService) => settsService.getLocale()
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
