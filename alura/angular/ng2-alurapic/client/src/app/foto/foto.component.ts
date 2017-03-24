import { Input, Component, ViewEncapsulation } from '@angular/core';

@Component({
  moduleId: module.id,
  selector: 'foto',
  templateUrl: './foto.component.html',
  styleUrls: ['./foto.component.css'],
  // forma de encapsular o CSS - padrão Emulated
  encapsulation: ViewEncapsulation.Emulated
})
export class FotoComponent {
  @Input() url;
  @Input() tiulo;
}