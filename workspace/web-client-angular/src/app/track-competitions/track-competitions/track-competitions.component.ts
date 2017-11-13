import { Component, OnInit } from '@angular/core';
import {UserService} from './user.service';
import {User} from './user';

@Component({
  selector: 'app-track-competitions',
  templateUrl: './track-competitions.component.html',
  styleUrls: ['./track-competitions.component.css']
})
export class TrackCompetitionsComponent implements OnInit {

  constructor(
   private userService: UserService
  ) { }

  user: User;

  ngOnInit() {
  }

  getItem(id) {
    this.userService.getItem(id).subscribe(user => this.user = user);
  }

}
