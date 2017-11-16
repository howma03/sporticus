import {Component, Input, OnInit} from '@angular/core';
import {Ladder, LadderService, LadderUser} from "../../services/ladder.service";
import {DataSource} from "@angular/cdk/collections";
import {Observable} from "rxjs/Observable";
import {AuthService} from "../../login/auth.service";

@Component({
  selector: 'app-ladder',
  templateUrl: './ladder.component.html',
  styleUrls: ['./ladder.component.css'],
})
export class LadderComponent implements OnInit {

  constructor(private ladderService: LadderService, private authService: AuthService) {
  }

  @Input()
  set ladder(ladder: Ladder) {
    if (ladder) {
      this.dataSource = new MyDataSource(this.ladderService, ladder, this.authService)
    }

  }

  dataSource = null;
  displayedColumns = ['name', 'position', 'actions'];

  ngOnInit() {
  }

  challenge(id : number) {
    alert("You are challenging user " + id);
  }
}

class MyDataSource extends DataSource<LadderUser> {

  challengeAbove : number = 2;
  challengeBelow : number = 1;

  constructor(private ladderService: LadderService, private ladder: Ladder, private authService : AuthService) {
    super();
  }

  connect(): Observable<LadderUser[]> {
    return this.ladderService.getLadderUsers(this.ladder.id).map(list => {
      let loggedInUser = this.authService.getCurrentUser();
      let loggedInUserPosition = list.data.find(item => item.userId === loggedInUser.id).position;
      list.data.map(user => {
        if (user.position < loggedInUserPosition && user.position >= loggedInUserPosition - this.challengeAbove
          || user.position > loggedInUserPosition && user.position <= loggedInUserPosition + this.challengeBelow) {
          user.canChallenge = true;
        }
        return user;
      });
      return list.data;
    });
  }

  disconnect() {
  }
}
