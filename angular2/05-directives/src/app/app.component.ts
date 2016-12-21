import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Using Directives!';

  public showContent: boolean = true;

  changeVisibility() {
    this.showContent = !this.showContent;
  }
}
