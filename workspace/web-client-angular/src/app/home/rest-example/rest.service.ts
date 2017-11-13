import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class RestService {

  REMOTE_URL = "https://jsonplaceholder.typicode.com";

  constructor(private http: HttpClient) { }

  getItem(id) {
    this.http.get(this.REMOTE_URL + "/posts/" + id).subscribe(
      (response: Response) => console.log(response)
    )
  }

}
