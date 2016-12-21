
import { Directive, OnInit, Input, ElementRef, Renderer, HostListener, HostBinding } from '@angular/core';

@Directive({
  selector: '[highlight]'
})
export class HighlightDirective implements OnInit {

  @HostBinding('style.backgroundColor') backgroundColor: string;

  @Input('default-color') defaultColor: string = 'red';
  @Input('highlight') highlightColor: string = 'red';

  constructor(private _elemRef: ElementRef, private _renderer: Renderer) { }

  ngOnInit() {
    this._renderer.setElementStyle(this._elemRef.nativeElement, 'cursor', 'pointer');
    this.backgroundColor = this.defaultColor;
  }

  @HostListener('mouseover') onMouseOver() {
    this.backgroundColor = this.highlightColor;
  }

  @HostListener('mouseleave') onMouseLeave() {
    this.backgroundColor = this.defaultColor;
  }
}
