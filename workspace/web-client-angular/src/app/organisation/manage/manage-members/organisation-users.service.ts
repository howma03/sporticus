import {Injectable} from '@angular/core';
import {User} from '../../../services/users.service';
import {Observable} from 'rxjs/Observable';
import {List} from '../../../services/list';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class OrganisationUsersService {

  constructor(private http: HttpClient) {
  }

  retrieveAll(orgId: number): Observable<List<User>> {
    return this.http.get<List<User>>(`/api/organisation/${orgId}/member`);
  }

  removeOne(orgId: number, userId: number): Observable<any> {
    return this.http.delete(`/api/organisation/${orgId}/member/${userId}`);
  }

  inviteUser(orgId: number, user: User): Observable<any> {
    return this.http.post(`/api/organisation/${orgId}/member`, user);
  }

}
