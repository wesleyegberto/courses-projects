import { Directive, OnInit, ElementRef, Renderer, HostListener, HostBinding } from '@angular/core';

@Directive({
  selector: '[mouse-events]'
})
export class MouseEventsDirective implements OnInit {

  @HostBinding('style.backgroundColor') backgroundColor: string;
  private foreColor: string;
  @HostBinding('style.color') get getColor() {
    if(this.foreColor == null || this.foreColor === '') {
      this.foreColor = 'black';
    }
    return this.foreColor;
  }

  constructor(private _elemRef: ElementRef, private _renderer: Renderer) { }

  ngOnInit() {
    this._renderer.setElementStyle(this._elemRef.nativeElement, 'cursor', 'pointer');
  }

  @HostListener('mouseover') onMouseOver() {
    this.backgroundColor = 'blue';
    this.foreColor = 'white';
  }

  @HostListener('mouseleave') onMouseLeave() {
    this.backgroundColor = 'white';
    this.foreColor = 'black';
  }
}
