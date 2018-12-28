import { Directive, ElementRef, Renderer } from '@angular/core';

@Directive({
  selector: '[yellow-background]'
})
export class YellowBackgroundDirective {

  constructor(private _elemRef: ElementRef, private _renderer: Renderer) {
    // unsafe
    // _elemRef.nativeElement.style.backgroundColor = 'yellow';
    _renderer.setElementStyle(_elemRef.nativeElement, 'background-color', 'yellow');
  }

}
