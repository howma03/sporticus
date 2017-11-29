import { Injectable } from '@angular/core';
import {BaseCrudService} from "./base-crud.service";
import {Observable} from "rxjs/Observable";
import {Ladder, LadderUser} from "./ladder.service";
import {User} from "./users.service";

@Injectable()
export class ChallengeService extends BaseCrudService<Challenge> {

  url = '/api/ladder/challenge';

  public deleteChallenge(ladder : Ladder, challenge : Challenge): Observable<any> {
    return this.http.delete(this.url + "/" + ladder.id + "/" + challenge.id, {
      responseType: 'text'
    });
  }

  public postChallenge(ladder : Ladder, challenge : Challenge): Observable<Challenge> {
    return this.http.post<Challenge>(this.url + "/" + ladder.id, challenge);
  }

  public putChallenge(challenge : Challenge) {
    return this.http.put<Challenge>(this.url + "/" + challenge.id, challenge);
  }

  public getAvailableChallenges() {
    return this.http.get<LadderNode[]>(this.url);
  }
}

export interface LadderNode extends Ladder {
  above: LadderUser[],
  below: LadderUser[]
}

export interface Challenge {
  id: number;
  name: string;
  created: Date;
  status: string; // PROPOSED, ACCEPTED, REJECTED, CLOSED
  dateTime: Date;
  dateTimeEnd: Date;
  challengerId: number;
  challengedId: number;
  scoreChallenger: number;
  scoreChallenged: number;
}
