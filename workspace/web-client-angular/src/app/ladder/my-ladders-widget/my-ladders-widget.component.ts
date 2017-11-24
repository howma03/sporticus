import {Component, OnInit} from '@angular/core';
import {Ladder, LadderService, LadderUser} from "../../services/ladder.service";
import {AuthService} from "../../auth/auth.service";
import {User} from "../../services/users.service";

@Component({
  selector: 'app-my-ladders-widget',
  templateUrl: './my-ladders-widget.component.html',
  styleUrls: ['./my-ladders-widget.component.css'],
})
export class MyLaddersWidgetComponent implements OnInit {

  loading: boolean = true;
  ladders: Ladder[] = [];
  rungs: LadderUser[] = [];

  private _currentLadder: Ladder;

  constructor(private ladderService: LadderService, private authService: AuthService) {
  }

  ngOnInit() {
    this.ladderService.retrieveAll()
      .map(list => list.data)
      .subscribe(ladders => {
        this.ladders = ladders;
        this.currentLadder = ladders[0];
      });
  }

  get currentLadder(): Ladder {
    return this._currentLadder;
  }

  set currentLadder(value: Ladder) {
    this._currentLadder = value;
    this.loadLadder(value);
  }

  loadLadder(ladder: Ladder) {
    if (!ladder) {
      this.rungs = [];
      this.loading = false;
      return;
    }
    this.loading = true;
    this.ladderService.getLadderUsers(ladder.id)
      .subscribe(users => {
        this.loading = false;
        this.rungs = users
      });
  }

  isMe(rung): boolean {
    let currentUser: User = this.authService.getCurrentUser();
    return currentUser.id === rung.userId;
  }

}
