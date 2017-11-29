import {Component, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {DatePipe} from '@angular/common';
import {Ladder, LadderService, LadderUser} from "../../services/ladder.service";
import {Subscription} from "rxjs/Subscription";
import {ChallengeDialogComponent} from "../../challenge/challenge-dialog/challenge-dialog.component";
import {MatDialog, MatMenuTrigger} from "@angular/material";
import {ChallengeService} from "../../services/challenge.service";
import {ConfirmDialogComponent} from '../../shared/confirm-dialog/confirm-dialog.component';
import {AuthService} from '../../auth/auth.service';
import {User} from '../../services/users.service';

const dateFormat : string = 'MMM d, y, h:mm a'; // TODO: Using short format does not internationalize well, need to work out why and keep the format in some common place.

@Component({
  selector: 'app-ladder',
  templateUrl: './ladder.component.html',
  styleUrls: ['./ladder.component.css']
})
export class LadderComponent implements OnInit, OnDestroy {

  constructor(
    private ladderService: LadderService,
    private challengeService : ChallengeService,
    private authService: AuthService,
    private dialog: MatDialog,
    private datePipe: DatePipe) {
  }

  @ViewChild(MatMenuTrigger) dayMenu: MatMenuTrigger;

  public ladderUsers: LadderUser[] = [];
  private subscription: Subscription;

  private _ladder: Ladder;

  @Input()
  set ladder(ladder: Ladder) {
    if (ladder) {
      this._ladder = ladder;
      this.reload();
    }
  }

  get ladder() {
    return this._ladder;
  }

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
    alert("not yet available - currently only the challenged person can edit");
    // let dialogRef = this.dialog.open(ChallengeDialogComponent, {
    //   disableClose: true,
    //   data: {
    //     rung: ladderUser
    //   }
    // });
    //
    // dialogRef.afterClosed().subscribe(challenge => {
    //   this.reload();
    // });
  }

  /**
   *
   * @param {LadderUser} ladderUser
   */
  cancelChallenge(ladderUser : LadderUser) {
    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: 'Confirm delete',
        description: 'Are you sure you want to delete the challenge'
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result === true) {
        this.challengeService.deleteChallenge(this.ladder, ladderUser.challenged)
          .subscribe(() => {
              this.reload();
            }
          );
      }
    });
  }

  /**
   * Return a tooltip customized for the item being hovered over
   * @param {LadderUser} rung
   * @returns {string}
   */
  getTooltip(rung : LadderUser) {
    if (rung.isChallenged) {
      return `You have challenged ${rung.userName} to a match on ${this.datePipe.transform(new Date(rung.challenged.dateTime), dateFormat)}. Click here to cancel that challenge.`;
    }
    if (rung.isChallenger) {
      return `You have an outstanding challenge from ${rung.userName} to a match on ${this.datePipe.transform(new Date(rung.challenger.dateTime), dateFormat)}. Click here to accept the challenge.`;
    }
    if (rung.canChallenge) {
      return `Click here to create a new challenge against ${rung.userName}.`;
    }
  }

  /**
   * Reload the ladder
   */
  private reload() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.subscription = this.ladderService.getLadderUsers(this.ladder.id)
      .subscribe((ladderUsers: LadderUser[])=>{
        this.ladderUsers = ladderUsers;
      });
  }

  isMe(rung) : boolean {
    let currentUser : User = this.authService.getCurrentUser();
    return currentUser.id === rung.userId;
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
