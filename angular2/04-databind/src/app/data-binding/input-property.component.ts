import { Component, Input } from '@angular/core';

@Component({
  selector: 'curso',
  template: '{{ nomeCurso }}'//,
  //inputs: ['nomeCurso:nome']
})
export class InputPropertyComponent {

  @Input('nome') nomeCurso : string = '';

  constructor() {  }


}
