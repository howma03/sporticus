import {Injectable} from '@angular/core';
import {BaseCrudService} from "./base-crud.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {User} from "./users.service";
import {List} from "./list";

@Injectable()
export class LadderService extends BaseCrudService<Ladder> {

  url = '/api/ladder';

  constructor(public http: HttpClient) {
    super(http);
  }

  getLadderUsers(id: number): Observable<List<LadderUser>> {
    return this.http.get<List<LadderUser>>(this.url + '/${id}/members');
  }
}

export interface Ladder {
  id: number;
  name: string;
  created: Date;
}

export interface LadderUser extends User {
  position: number;
  isChallenged: boolean;
  canChallenge: boolean;
  userId: number;
}
