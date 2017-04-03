import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { CourseRouting } from './course.routing';
import { CourseGuard } from './guard/course.guard';
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
        CourseRouting
    ],
    exports: [],
    declarations: [
        CoursesComponent,
        CourseDetailsComponent,
        CourseNotFoundComponent,
        CourseFormComponent
    ],
    providers: [ CourseService, CourseGuard ]
})
export class CourseModule {}
