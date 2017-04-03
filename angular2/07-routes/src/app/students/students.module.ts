import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { StudentsRoutingModule } from './students.routing';
import { StudentService } from './student.service';
import { StudentsComponent }   from './students.component';
import { StudentsDetailsComponent } from './students-details/students-details.component';
import { StudentsFormComponent } from './students-form/students-form.component';

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        FormsModule,
        StudentsRoutingModule
    ],
    exports: [],
    declarations: [
        StudentsComponent,
        StudentsDetailsComponent,
        StudentsFormComponent
    ],
    providers: [ StudentService ],
})
export class StudentsModule { }
