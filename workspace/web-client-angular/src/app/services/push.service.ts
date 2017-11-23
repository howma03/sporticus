import {Injectable} from '@angular/core';
import {EventSourcePolyfill} from "ng-event-source";

@Injectable()
export class PushService {

  constructor() {
    let eventSource = new EventSourcePolyfill('/api/notification/feed', {});

    eventSource.onmessage = (data => {
      console.log("data " + data);
    });

    eventSource.onopen = (a) => {
      // Do stuff here
      console.log("onOpen " + a);
    };
    eventSource.onerror = (e) => {
      // Do stuff here
      console.log("onerror " + e);
    }
  }

  isEnabled() {
    return true;
  }
}
