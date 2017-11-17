import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Ladder, LadderService, LadderUser} from "../../services/ladder.service";
import {AuthService} from "../../login/auth.service";
import {Subscription} from "rxjs/Subscription";
import {ChallengeService} from "../../services/challenge.service";

@Component({
  selector: 'app-ladder',
  templateUrl: './ladder.component.html',
  styleUrls: ['./ladder.component.css'],
})
export class LadderComponent implements OnInit, OnDestroy {

  challengeAbove : number = 2;
  challengeBelow : number = 1;

  constructor(
    private ladderService: LadderService,
    private authService: AuthService,
    private challengeService: ChallengeService) {
  }

  public ladderUsers: LadderUser[] = [];
  private subscription: Subscription;

  private myLadder: Ladder; // TODO: Get rid of this once we know why this.ladder not working

  @Input()
  set ladder(ladder: Ladder) {
    console.log(ladder);
    if (ladder) {
      this.subscription = this.ladderService.getLadderUsers(ladder.id)
        .map(list=>{
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
        })
        .subscribe((ladderUsers: LadderUser[])=>{
          this.ladderUsers = ladderUsers;
        });
      this.myLadder = ladder;// TODO: Why does is this.ladder not set?
    }
  }

  dataSource = null;
  displayedColumns = ['name', 'position', 'challenged', 'actions'];

  ngOnInit() {
  }

  challenge(ladderUser : LadderUser) {
    this.challengeService.createChallenge(
      this.myLadder, // TODO: Why does this.ladder not work?
      this.authService.getCurrentUser(),
      ladderUser).subscribe(
        item => {
          debugger;
          ladderUser.isChallenged = true;
        }
    );
  }

  ngOnDestroy() {
    if(this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
