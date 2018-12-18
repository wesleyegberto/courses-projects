import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { IEvent } from './event';

@Injectable()
export class EventService {
  private _eventUrl: string = 'http://www.mocky.io/v2/587d73f30f00004e105df647';

  constructor(private _http: Http) { }

  getEvents(): Observable<IEvent[]> {
    return this._http.get(this._eventUrl)
      .map((response: Response) => <IEvent[]>response.json())
      .do(data => console.log("All: " + JSON.stringify(data)))
      .catch(this.handleError);
  }

  private handleError(error: Response) {
    console.error(error);
    return Observable.throw(error.json().error || 'Server error');
  }
}