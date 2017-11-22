import { Injectable } from '@angular/core';
import {EventSourcePolyfill} from "ng-event-source";

@Injectable()
export class PushService {

  constructor() {
    let eventSource = new EventSourcePolyfill('/api/notification/feed', {});

    eventSource.onmessage = (data => {
      alert(data);
    });

    eventSource.onopen = (a) => {
      // Do stuff here
    };
    eventSource.onerror = (e) => {
      // Do stuff here
    }
  }

  isEnabled() {
    return true;
  }
}
