import { Injectable } from '@angular/core';
import {EventSourcePolyfill} from "ng-event-source";

@Injectable()
export class PushService {

  constructor() {
    let eventSource = new EventSourcePolyfill('/api/push', {headers: { headerName: 'HeaderValue', header2: 'HeaderValue2' }});

    eventSource.onmessage = (data => {
      alert("beef")
    });

    eventSource.onopen = (a) => {
      // Do stuff here
    };
    eventSource.onerror = (e) => {
      // Do stuff here
    }
  }
}
