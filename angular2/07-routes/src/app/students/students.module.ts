import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { StudentsRoutingModule } from './students.routing';
import { StudentsComponent }   from './students.component';
import { StudentsDetailsComponent } from './students-details/students-details.component';
import { StudentsFormComponent } from './students-form/students-form.component';

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        StudentsRoutingModule
    ],
    exports: [],
    declarations: [
        StudentsComponent,
        StudentsDetailsComponent,
        StudentsFormComponent
    ],
    providers: [],
})
export class StudentsModule { }
