import {Injectable} from '@angular/core';
import {EventSourcePolyfill} from "ng-event-source";
import {Observable} from "rxjs/Observable";

@Injectable()
export class PushService {

  constructor() {
    this.eventSource = new EventSourcePolyfill('/api/notification/feed', {});

    this.eventSource.onopen = (a) => {
      // Do stuff here
      console.log("onOpen " + a);
    };

    this.eventSource.onerror = (e) => {
      // Do stuff here
      console.log("onerror " + e);
    }
  }

  eventSource;

  registerForEvents(): Observable<Object> {

    let observable = Observable.create(observer => {
      this.eventSource.onmessage = (data => {
        observer.next(data)
      });
    });

    return observable;
  }
}
