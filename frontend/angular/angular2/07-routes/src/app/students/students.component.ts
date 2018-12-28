import { Component, OnInit } from '@angular/core';

import { Student } from './student';
import { StudentService } from './student.service';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html'
})
export class StudentsComponent implements OnInit {

  students: Student[];

  constructor(private _studentsService: StudentService) {}

  ngOnInit() {
    this.students = this._studentsService.getStudents();
  }

}
