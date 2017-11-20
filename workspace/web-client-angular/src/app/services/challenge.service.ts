import { Injectable } from '@angular/core';
import {BaseCrudService} from "./base-crud.service";
import {Observable} from "rxjs/Observable";
import {Ladder, LadderUser} from "./ladder.service";
import {User} from "./users.service";

@Injectable()
export class ChallengeService extends BaseCrudService<Challenge> {

  url = '/api/ladder/challenge';

  public createChallenge(ladder : Ladder, challenger : User, challenged : LadderUser): Observable<Challenge> {
    return this.http.post<Challenge>(this.url + "/" + ladder.id + "/" + challenger.id + "/" + challenged.userId, null);
  }

  public putChallenge(challenge : Challenge) {
    return this.http.put<Challenge>(this.url + "/" + challenge.id, challenge);
  }
}

export interface Challenge {
  id: number;
  name: string;
  created: Date;
  status: string; // PROPOSED, ACCEPTED, REJECTED, CLOSED
  dateTime: Date;
  challengerId: number;
  challengedId: number;
  scoreChallenger: number;
  scoreChallenged: number;
}
