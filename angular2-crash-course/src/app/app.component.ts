import { Component } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/Rx';

import { EventListComponent } from './events/event-list.component';
import { EventService }	from './events/event.service';
import { WelcomeComponent } from './home/welcome.component';
import {  EventDetailComponent } from './events/event-detail.component';

@Component({
    selector: 'events-app',
    template: `
    <div>
	    <nav class='navbar navbar-default'>
		    <div class='container-fluid'>
			    <a class='navbar-brand'>{{pageTitle}}</a>
			    <ul class='nav navbar-nav'>
				    <li><a [routerLink]="['/welcome']">Home</a></li>
				    <li><a [routerLink]="['/events']">Event List</a></li>
			    </ul>
		    </div>
	    </nav>
	    <div class='container'>
			<router-outlet></router-outlet>
		</div>
    </div>
    `,
    providers: [EventService, Http]
})
export class AppComponent {
    pageTitle: string = 'Local Events App';
}
