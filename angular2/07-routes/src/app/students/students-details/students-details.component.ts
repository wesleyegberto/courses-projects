import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Subscription } from 'rxjs/Rx';

import { Student } from '../student';
import { StudentService } from '../student.service';

@Component({
  selector: 'app-students-details',
  templateUrl: './students-details.component.html'
})
export class StudentsDetailsComponent implements OnInit, OnDestroy {
  public student: Student;
  public studentId: string;
  private subscription: Subscription;

  constructor(private route: ActivatedRoute, private router: Router, private studentService: StudentService) { }

  ngOnInit() {
    this.subscription = this.route.params.subscribe((params: any) => {
      this.studentId = params['id'];

      this.student = this.studentService.getStudent(this.studentId);

      if (this.student == null) {
        this.router.navigate(['/students']);
      }
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
