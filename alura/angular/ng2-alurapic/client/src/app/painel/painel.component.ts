import { Component, Input, OnInit } from '@angular/core';

@Component({
  moduleId: module.id,
  selector: 'painel',
  templateUrl: './painel.component.html',
  styleUrls: [ './painel.component.css' ]
})
export class PainelComponent implements OnInit {
  @Input() titulo;

  ngOnInit() {
    this.titulo = (this.titulo.length > 15)
                  ? `${this.titulo.substr(0, 7)}...` // ES6
                  : this.titulo;
  }
}