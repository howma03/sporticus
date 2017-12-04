import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {User} from './users.service';

@Injectable()
export class GroupMemberService {
  constructor(private http: HttpClient) {
  }

  baseUrl = '/api/admin/group';

  public retrieveAll(groupId): Observable<User[]> {
    const url = `${this.baseUrl}/${groupId}/member`;
    return this.http.get<User[]>(url);
  }

  public addOne(groupId: number, member: User) {
    const url = `${this.baseUrl}/${groupId}/member`;
    return this.http.post<User>(url, member);
  }

  public removeOne(groupId: number, member: User) {
    const url = `${this.baseUrl}/${groupId}/member/${member.id}`;
    return this.http.delete(url);
  }
}
