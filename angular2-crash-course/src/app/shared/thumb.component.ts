import { Component, OnChanges, Input, Output, EventEmitter } from '@angular/core';

@Component({
    selector: 'acw-thumb',
    templateUrl: './thumb.component.html',
    styleUrls: ['./thumb.component.css']
})
export class ThumbComponent implements OnChanges {
    @Input() rating: number;
    thumbWidth: number;
    @Output() ratingClicked: EventEmitter<string> = new EventEmitter<string>();
    
    ngOnChanges(): void {
		this.thumbWidth = this.rating * 86 / 5;
	}
	
	onClick() {
	    this.ratingClicked.emit(`The rating ${this.rating} was clicked.`);
    }
}