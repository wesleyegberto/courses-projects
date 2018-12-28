import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filtroPorTitulo'
})
export class FiltroPorTitulo implements PipeTransform {
  transform(fotos: any, titulo: string) {
    return fotos.filter(foto => foto.titulo.toLowerCase()
                                  .includes(titulo.toLowerCase()));
  }
}