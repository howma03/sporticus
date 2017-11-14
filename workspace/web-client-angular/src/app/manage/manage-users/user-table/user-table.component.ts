import { Component, OnInit } from '@angular/core';
import {DataSource} from '@angular/cdk/collections';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import {User, UsersService} from "../../../services/users.service";

@Component({
  selector: 'user-table',
  templateUrl: './user-table.component.html',
  styleUrls: ['./user-table.component.css']
})
export class UserTableComponent implements OnInit {

  constructor(private usersService: UsersService ) {
  }

  ngOnInit() {
  }

  displayedColumns = ['name', 'email'];
  dataSource = new MyUsersDataSource(this.usersService);

}

export class MyUsersDataSource extends DataSource<any> {
  constructor(private usersService: UsersService) {
    super();
  }
  connect(): Observable<User[]> {
    return this.usersService.retrieveAll().map(list=> list.data);
  }
  disconnect() {}
}

