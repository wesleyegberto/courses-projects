import { Component, Input, OnInit, ElementRef } from '@angular/core';

@Component({
  moduleId: module.id,
  selector: 'painel',
  templateUrl: './painel.component.html',
  styleUrls: [ './painel.component.css' ]
})
export class PainelComponent implements OnInit {
  @Input() titulo;

  // injeta o elemento do DOM do componente
  constructor(private _elem: ElementRef) {}

  ngOnInit() {
    this.titulo = (this.titulo.length > 15)
                  ? `${this.titulo.substr(0, 7)}...` // ES6
                  : this.titulo;
  }

  fadeOut(callback) {
    $(this._elem.nativeElement).fadeOut(callback);
  }
}