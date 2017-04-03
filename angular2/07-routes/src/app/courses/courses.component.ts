import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

import { CourseService } from './course.service';
import { Course } from './Course';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html'
})
export class CoursesComponent implements OnInit, OnDestroy {

  private subscription: Subscription;
  public page: number = 1;
  public courses: Course[];

  constructor(private route: ActivatedRoute, private router: Router, private courseService: CourseService) {
  }

  ngOnInit() {
    this.subscription = this.route.queryParams.subscribe((queryParams: any) => {
      this.page = queryParams['page'];
      if (this.page == null) {
        this.page = 1;
      }
    });
    this.courses = this.courseService.getCourses();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  previousPage() {
    this.navigateToPage(--this.page);
  }

  nextPage() {
    this.navigateToPage(++this.page);
  }

  navigateToPage(page: number) {
    this.router.navigate(['/courses'], { queryParams: { 'page': page } });
  }
}
