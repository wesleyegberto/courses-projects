import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

@Component({
  selector: 'app-course-not-found',
  templateUrl: './course-not-found.component.html',
  styleUrls: ['./course-not-found.component.css']
})
export class CourseNotFoundComponent implements OnInit, OnDestroy {

  public courseId: string;
  private subscription: Subscription;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.subscription = this.route.params.subscribe((params: any) => {
      this.courseId = params['id'];
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

}
