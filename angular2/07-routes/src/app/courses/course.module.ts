import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { CourseRoutingModule } from './course.routing.module';
import { CourseService } from './course.service';
import { CourseNotFoundComponent } from './course-not-found/course-not-found.component';
import { CourseDetailsComponent } from './course-details/course-details.component';
import { CoursesComponent } from './courses.component';

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        CourseRoutingModule
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
