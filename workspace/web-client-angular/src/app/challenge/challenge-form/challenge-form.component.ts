import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Ladder, LadderUser} from "../../services/ladder.service";
import {Challenge, ChallengeService} from '../../services/challenge.service';
import {AuthService} from '../../login/auth.service';

@Component({
  selector: 'app-challenge-form',
  templateUrl: './challenge-form.component.html',
  styleUrls: ['./challenge-form.component.css'],
})
export class ChallengeFormComponent implements OnInit {

  constructor(private fb: FormBuilder,
              private challengeService : ChallengeService,
              private authService: AuthService) {}

  @Input()
  rung: LadderUser;

  @Input()
  ladder: Ladder;

  @Output()
  done = new EventEmitter<LadderUser>();

  challengeForm: FormGroup;

  isNew: boolean = false;

  get eventDate() {
    return this.challengeForm.get('date').get('eventDate')
  }

  get eventTime() {
    return this.challengeForm.get('date').get('eventTime')
  }

  get scoreChallenger() {
    return this.challengeForm.get('scores').get('scoreChallenger')
  }

  get scoreChallenged() {
    return this.challengeForm.get('scores').get('scoreChallenged')
  }

  ngOnInit() {

    if (!this.rung.challenger) {
      // this is a new challenge from a challenger
      this.isNew = true;

      this.rung.challenger = {
        id: null,
        name: 'New Challenge',
        created: new Date(),
        status: 'PROPOSED',
        dateTime: new Date(),
        challengerId: this.authService.getCurrentUser().id,
        challengedId: this.rung.userId,
        scoreChallenger: null,
        scoreChallenged: null,
      }
    }

    let dateOnly : string = null;
    let timeOnly : string = null;
    if (this.rung.challenger.dateTime) {
      let datetime : Date = new Date(this.rung.challenger.dateTime);
      dateOnly = datetime.toISOString().substring(0, 10);
      timeOnly = ('0' + datetime.getHours()).slice(-2) + ":" + ('0' + datetime.getMinutes()).slice(-2);
    }

    this.challengeForm = this.fb.group({
      date: this.fb.group({
        eventDate: [dateOnly, [Validators.required]], // TODO: We're waiting upon the Material datetime picker,
        eventTime: [timeOnly, [Validators.required]], // to enter date and time as a single field,
      }),
      scores: this.fb.group({
        scoreChallenger: {value: this.rung.challenger.scoreChallenger,  disabled: true},
        scoreChallenged: {value: this.rung.challenger.scoreChallenged,  disabled: true}
      }, {validator: this.scoreMatcher}),
    });

    this.enableScores(this.challengeForm.get('date').value);
    this.challengeForm.get('date').valueChanges.subscribe(value => this.enableScores(value));
  }

  onSave() {
    let dateOnly = this.challengeForm.get('date').get('eventDate').value;
    let timeOnly = this.challengeForm.get('date').get('eventTime').value;

    let dateTime = new Date(dateOnly);
    let splitTime = timeOnly.split(":");
    dateTime.setHours(+splitTime[0]);
    dateTime.setMinutes(+splitTime[1]);

    const toSave: Challenge = {
      id: this.rung.challenger.id,
      name: this.rung.challenger.name,
      created: this.rung.challenger.created,
      dateTime: dateTime,
      status: 'ACCEPTED',
      challengerId: this.rung.challenger.challengerId,
      challengedId: this.rung.challenger.challengedId,
      scoreChallenger: this.scoreChallenger.value,
      scoreChallenged: this.scoreChallenged.value
    };

    if (this.isNew) {
      this.challengeService.postChallenge(this.ladder, toSave)
        .subscribe(challenge => {
          this.rung.challenger = challenge;
          this.onDone();
        })
    } else {
      this.challengeService.putChallenge(toSave)
        .subscribe(challenge => {
          this.rung.challenger = challenge;
          this.onDone();
        })
    }
  }

  onCancel() {
    this.onDone();
  }

  onDone() {
    this.done.emit();
  }

  /**
   * Test that both scores have been provided if any score is provided
   * @param {AbstractControl} c
   * @returns {{[key: string]: boolean}}
   */
  scoreMatcher(c: AbstractControl): {[key: string]: boolean} | null {
    let scoreChallengerControl = c.get('scoreChallenger');
    let scoreChallengedControl = c.get('scoreChallenged');

    if ((scoreChallengerControl.value == null) !== (scoreChallengedControl.value == null)) {
      return {'bothscores': true}
    }
    return null;
  }

  /**
   * Enable the scores only of the date is valid and is a past date
   * @param values
   */
  enableScores(values) {
    if (this.challengeForm.get('date').valid && new Date(this.eventDate.value) < new Date()) {
      this.challengeForm.get('scores').enable();
    } else {
      this.challengeForm.get('scores').disable();
    }
  }
}
