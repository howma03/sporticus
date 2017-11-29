import {Injectable} from '@angular/core';
import {BaseCrudService} from "./base-crud.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {User} from "./users.service";
import {List} from "./list";
import {Challenge} from './challenge.service';
import {AuthService} from '../auth/auth.service';

@Injectable()
export class LadderService extends BaseCrudService<Ladder> {

  url = '/api/ladder';

  challengeAbove : number = 2;
  challengeBelow : number = 2;

  constructor(public http: HttpClient, private authService : AuthService) {
    super(http);
  }

  getLadderUsers(id: number): Observable<LadderUser[]> {
    return this.http.get<List<LadderUser>>(`${this.url}/${id}/members`)
      .map(list=>{
      let loggedInUser = this.authService.getCurrentUser();
      let loggedInUserPosition = list.data.find(item => item.userId === loggedInUser.id).position;
      list.data.map(user => {
        if (user.challenger && user.challenger.challengedId === loggedInUser.id) {
          user.isChallenger = true;
        } else if (user.challenged && user.challenged.challengerId === loggedInUser.id) {
          user.isChallenged = true;
        } else if (user.position < loggedInUserPosition && user.position >= loggedInUserPosition - this.challengeAbove
          || user.position > loggedInUserPosition && user.position <= loggedInUserPosition + this.challengeBelow) {
          user.canChallenge = true;
        }

        return user;
      });
      return list.data;
    })

  }
}

export interface Ladder {
  id: number;
  name: string;
  created: Date;
}

export interface LadderUser extends User {
  position: number;
  isChallenger: boolean;
  isChallenged: boolean;
  canChallenge: boolean;
  userId: number;
  challenged: Challenge;
  challenger: Challenge;
}
