import { Injectable } from '@angular/core';

import { Student } from 'app/students/student';

@Injectable()
export class StudentService {

  private students: Student[] = [
    new Student(1, 'Bruce Banner'),
    new Student(2, 'Henry Pym'),
    new Student(3, 'Tony Stark')
  ];

  constructor() { }

  getStudents(): Student[] {
    return this.students;
  }

  getStudent(id): Student {
    return this.students.find(c => c.id == id);
  }
}
