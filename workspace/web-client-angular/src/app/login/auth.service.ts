import {Injectable} from '@angular/core';

@Injectable()
export class AuthService {
  private storageKey: "currentUser";


  private token: string = null;

  constructor() {
    let storage = localStorage.getItem(this.storageKey);
    if (storage === null) {
      this.token = null;
    } else {
      this.token = JSON.parse(storage).token;
    }
  }

  isLoggedIn(): boolean {
    return this.token !== null;
  }

  getAuthToken(): string {
    return this.token;
  }

  startSession(user: string, token: string) {
    localStorage.setItem(this.storageKey, JSON.stringify({user, token}));
    this.token = token;
  }

  endSession() {
    localStorage.removeItem(this.storageKey);
    this.token = null;
  }
}

