import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { CourseRoutingModule } from './course.routing';
import { CourseService } from './course.service';
import { CoursesComponent } from './courses.component';
import { CourseNotFoundComponent } from './course-not-found/course-not-found.component';
import { CourseDetailsComponent } from './course-details/course-details.component';
import { CourseFormComponent } from 'app/courses/course-form/course-form.component';

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        FormsModule,
        CourseRoutingModule
    ],
    exports: [],
    declarations: [
        CoursesComponent,
        CourseDetailsComponent,
        CourseNotFoundComponent,
        CourseFormComponent
    ],
    providers: [ CourseService ]
})
export class CourseModule {}
