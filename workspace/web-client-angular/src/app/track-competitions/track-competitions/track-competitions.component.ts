import {Component, OnInit} from '@angular/core';
import {User, UsersService} from "../../services/users.service";

@Component({
  selector: 'app-track-competitions',
  templateUrl: './track-competitions.component.html',
  styleUrls: ['./track-competitions.component.css']
})
export class TrackCompetitionsComponent implements OnInit {

  constructor(private usersService: UsersService
  ) { }

  user: User;

  ngOnInit() {
  }

  getItem(id) {
    this.usersService.retrieveOne(id).subscribe(user => this.user = user);
  }

}
