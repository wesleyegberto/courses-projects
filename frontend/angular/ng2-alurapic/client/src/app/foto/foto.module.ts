import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpModule } from "@angular/http";

import { FiltroPorTitulo } from './filtro.pipes';
import { FotoService } from "./foto.service";
import { FotoComponent } from './foto.component';

@NgModule({
  imports: [ CommonModule, HttpModule ],
  declarations: [ FotoComponent, FiltroPorTitulo ],
  // exporta o componente para ser utilizado em outros módulos
  exports: [ FotoComponent, FiltroPorTitulo ],
  providers: [ FotoService ]
})
export class FotoModule {
}