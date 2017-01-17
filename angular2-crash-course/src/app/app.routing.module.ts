import { ModuleWithProviders, NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { EventListComponent } from './events/event-list.component';
import { EventDetailComponent } from './events/event-detail.component';
import { WelcomeComponent } from './home/welcome.component';

const APP_ROUTES: Routes = [
    { path: 'welcome', component: WelcomeComponent },
    { path: 'events', component: EventListComponent },
    { path: 'event/:id', component: EventDetailComponent },
    { path: '', component: WelcomeComponent }
];

@NgModule({
    imports: [RouterModule.forRoot(APP_ROUTES)],
    exports: [RouterModule]
})
export class AppRoutingModule {

}