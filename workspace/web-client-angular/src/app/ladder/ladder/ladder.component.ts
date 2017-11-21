import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Ladder, LadderService, LadderUser} from "../../services/ladder.service";
import {Subscription} from "rxjs/Subscription";
import {ChallengeService} from "../../services/challenge.service";
import {ChallengeDialogComponent} from "../../challenge/challenge-dialog/challenge-dialog.component";
import {MatDialog} from "@angular/material";

@Component({
  selector: 'app-ladder',
  templateUrl: './ladder.component.html',
  styleUrls: ['./ladder.component.css'],
})
export class LadderComponent implements OnInit, OnDestroy {

  constructor(
    private ladderService: LadderService,
    private dialog: MatDialog) {
  }

  public ladderUsers: LadderUser[] = [];
  private subscription: Subscription;

  private _ladder: Ladder;

  @Input()
  set ladder(ladder: Ladder) {
    if (ladder) {
      this._ladder = ladder;
      this.subscription = this.ladderService.getLadderUsers(ladder.id)
        .subscribe((ladderUsers: LadderUser[])=>{
          this.ladderUsers = ladderUsers;
        });
    }
  }

  get ladder() {
    return this._ladder;
  }

  dataSource = null;
  displayedColumns = ['name', 'position', 'challenged', 'actions'];

  ngOnInit() {
  }

  /**
   * Create a new challenge against another player in the ladder
   * @param {LadderUser} ladderUser the player being challenged
   */
  createChallenge(ladderUser : LadderUser) {
    let dialogRef = this.dialog.open(ChallengeDialogComponent, {
      disableClose: true,
      data: {
        ladder: this.ladder,
        rung: ladderUser
      }
    });

    dialogRef.afterClosed().subscribe(challenge => {
      debugger;
      this.subscription.unsubscribe();
      this.subscription = this.ladderService.getLadderUsers(this.ladder.id)
        .subscribe((ladderUsers: LadderUser[])=>{
          this.ladderUsers = ladderUsers;
        });
      // this.challengeService.createChallenge(this.ladder, challenge)
      //   .subscribe(
      //   item => {
      //     ladderUser.isChallenged = true;
      //     ladderUser.canChallenge = false;
      //   }
      // );

      this.subscription.unsubscribe();
      this.subscription = this.ladderService.getLadderUsers(this.ladder.id)
        .subscribe((ladderUsers: LadderUser[])=>{
          this.ladderUsers = ladderUsers;
        });
    });

  }

  editChallenge(ladderUser : LadderUser) {
    let dialogRef = this.dialog.open(ChallengeDialogComponent, {
      disableClose: true,
      data: {
        rung: ladderUser
      }
    });

    dialogRef.afterClosed().subscribe(challenge => {
      this.subscription.unsubscribe();
      this.subscription = this.ladderService.getLadderUsers(this.ladder.id)
        .subscribe((ladderUsers: LadderUser[])=>{
          this.ladderUsers = ladderUsers;
        });
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
