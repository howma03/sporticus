import { Injectable } from '@angular/core';
import {BaseCrudService} from "./base-crud.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Ladder, LadderUser} from "./ladder.service";
import {User} from "./users.service";

@Injectable()
export class ChallengeService extends BaseCrudService<Challenge> {

  url = '/api/ladder/challenge';

  public createChallenge(ladder : Ladder, challenger : User, challenged : LadderUser): Observable<Challenge> {
    debugger;
    return this.http.post<Challenge>(this.url + "/" + ladder.id + "/" + challenger.id + "/" + challenged.userId, null);
  }
}

export interface Challenge {
  id: number;
  name: string;
  created: Date;
}
