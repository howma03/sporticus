import { Component, OnInit } from '@angular/core';
import {RestService} from './rest.service';
import {User} from './user';

@Component({
  selector: 'app-track-competitions',
  templateUrl: './track-competitions.component.html',
  styleUrls: ['./track-competitions.component.css']
})
export class TrackCompetitionsComponent implements OnInit {

  constructor(
   private restService: RestService
  ) { }

  user: User;

  ngOnInit() {
  }

  getItem(id) {
    this.restService.getItem(id).subscribe(user => this.user = user);
  }

}
