import { Injectable } from '@angular/core';

import { Course } from './Course';

@Injectable()
export class CourseService {

  private courses: Course[] = [
    new Course('1', 'Angular 2'),
    new Course('2', 'Java EE'),
    new Course('3', 'Testing')
  ];

  constructor() { }

  getCourses(): Course[] {
    return this.courses;
  }

  getCourse(id): Course {
    return this.courses.find(c => c.id === id);
  }
}
