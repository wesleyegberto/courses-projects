import { NgModule }      from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormDeactivateGuard } from './form.deactivate.guard';

@NgModule({
  imports: [ CommonModule ],
  providers: [ FormDeactivateGuard ]
})
export class FormStateGuardModule {}
