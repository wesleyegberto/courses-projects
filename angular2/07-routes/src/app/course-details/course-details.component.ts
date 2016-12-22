import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

@Component({
  selector: 'app-course-details',
  templateUrl: './course-details.component.html',
  styleUrls: ['./course-details.component.css']
})
export class CourseDetailsComponent implements OnInit, OnDestroy {

  public courseId: string;
  private subscription: Subscription;

  constructor(private router: ActivatedRoute) { }

  ngOnInit() {
    this.subscription = this.router.params.subscribe((params: any) => this.courseId = params['id']);
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
