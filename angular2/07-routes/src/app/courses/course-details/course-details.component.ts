import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

import { CourseService } from '../course.service';
import { Course } from '../Course';

@Component({
  selector: 'app-course-details',
  templateUrl: './course-details.component.html',
  styleUrls: ['./course-details.component.css']
})
export class CourseDetailsComponent implements OnInit, OnDestroy {

  public course: Course;
  public courseId: string;
  private subscription: Subscription;

  constructor(private route: ActivatedRoute, private router: Router, private courseService: CourseService) { }

  ngOnInit() {
    this.subscription = this.route.params.subscribe((params: any) => {
      this.courseId = params['id'];

      this.course = this.courseService.getCourse(this.courseId);

      if (this.course == null) {
        this.router.navigate(['/course-not-found', this.courseId]);
      }
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
