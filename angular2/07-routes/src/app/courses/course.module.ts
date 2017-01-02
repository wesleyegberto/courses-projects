import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { CourseService } from './course.service';
import { CourseNotFoundComponent } from './course-not-found/course-not-found.component';
import { CourseDetailsComponent } from './course-details/course-details.component';
import { CoursesComponent } from './courses.component';

@NgModule({
    imports: [
        CommonModule,
        RouterModule
    ],
    exports: [],
    declarations: [
        CoursesComponent,
        CourseDetailsComponent,
        CourseNotFoundComponent
    ],
    providers: [ CourseService ]
})
export class CourseModule {}
