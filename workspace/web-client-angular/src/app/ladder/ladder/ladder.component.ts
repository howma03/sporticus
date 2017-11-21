import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Ladder, LadderService, LadderUser} from "../../services/ladder.service";
import {Subscription} from "rxjs/Subscription";
import {ChallengeDialogComponent} from "../../challenge/challenge-dialog/challenge-dialog.component";
import {MatDialog} from "@angular/material";
import {ChallengeService} from "../../services/challenge.service";

@Component({
  selector: 'app-ladder',
  templateUrl: './ladder.component.html',
  styleUrls: ['./ladder.component.css'],
})
export class LadderComponent implements OnInit, OnDestroy {

  constructor(
    private ladderService: LadderService,
    private dialog: MatDialog,
    private challengeService : ChallengeService) {
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
      this.reload();
    });

  }

  /**
   * Edit a challenge in order to set a date and optionally give a score (if the date was in the past)
   * Currently this is only called by the challenged user in order to accept a challenge.
   * @param {LadderUser} ladderUser
   */
  editChallenge(ladderUser : LadderUser) {
    let dialogRef = this.dialog.open(ChallengeDialogComponent, {
      disableClose: true,
      data: {
        rung: ladderUser
      }
    });

    dialogRef.afterClosed().subscribe(challenge => {
      this.reload();
    });
  }

  /**
   *
   * @param {LadderUser} ladderUser
   */
  cancelChallenge(ladderUser : LadderUser) {
    this.challengeService.deleteChallenge(this.ladder, ladderUser.challenged)
      .subscribe((thing : any) => {
        this.reload();
      }
    );
  }

  private reload() {
    this.subscription.unsubscribe();
    this.subscription = this.ladderService.getLadderUsers(this.ladder.id)
      .subscribe((ladderUsers: LadderUser[])=>{
        this.ladderUsers = ladderUsers;
      });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
