import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

@Component({
  templateUrl: './event-detail.component.html'
})
export class EventDetailComponent {
  private subscription: Subscription;
  pageTitle: string = 'Event Detail';

  constructor(private _route: ActivatedRoute, private _router: Router) {
    this.subscription = this._route.params.subscribe((params: any) => {
      let id = params['id'];
      this.pageTitle += `: ${id}`;
    });
  }

  onBack(): void {
    this  ._router.navigate(['Events']);
  }
}