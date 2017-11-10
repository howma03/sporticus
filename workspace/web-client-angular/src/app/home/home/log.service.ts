//import { Injectable } from '@angular/core';

//@Injectable()
export class LogService {

  constructor() { }

  logMessage(message: string) {
    console.log(new Date() + ": " + message);
  }

}
