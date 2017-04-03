import { FormCanDeactivate } from './../../form-state-guard/form.candeactivate';
import { ViewChild, Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { Subscription } from 'rxjs/Rx';

import { Student } from './../student';
import { StudentService } from './../student.service';

@Component({
  selector: 'app-students-form',
  templateUrl: './students-form.component.html'
})
export class StudentsFormComponent implements OnInit, FormCanDeactivate {
  public student: Student;
  public studentId: string;

  // inject the form to see if it is dirty
  @ViewChild('frm') public studentForm: NgForm;

  constructor(private route: ActivatedRoute, private router: Router, private studentService: StudentService) { }

  ngOnInit() {
    console.log('[StudentsFormComponent]: ngOninit');
    this.route.data.subscribe(
      (info: {student: Student}) => {
        console.log('[StudentsFormComponent]: route.data subscription');
        this.student = info.student;

        if (this.student == null) {
          this.router.navigate(['/students']);
        }
      }
    );
  }

  canDeactivate(): boolean {
    if (this.studentForm.dirty) {
      if (confirm('The form has been changed, do you still want to leave without save?')) {
        return true;
      }
    }
    return false;
  }
}
