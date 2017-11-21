import {Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {LadderUser} from "../../services/ladder.service";
import {Challenge, ChallengeService} from '../../services/challenge.service';

@Component({
  selector: 'app-challenge-form',
  templateUrl: './challenge-form.component.html',
  styleUrls: ['./challenge-form.component.css'],
})
export class ChallengeFormComponent implements OnInit {

  constructor(private fb: FormBuilder, private challengeService : ChallengeService) {}

  @Input()
  rung: LadderUser;

  @Output()
  done = new EventEmitter<LadderUser>();

  challengeForm: FormGroup;

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
    this.challengeForm = this.fb.group({
      date: this.fb.group({
        eventDate: [null, [Validators.required]], // TODO: We're waiting upon the Material datetime picker,
        eventTime: [null, [Validators.required]], // to enter date and time as a single field,
      }),
      scores: this.fb.group({
        scoreChallenger: {value: null,  disabled: true},
        scoreChallenged: {value: null,  disabled: true}
      }, {validator: this.scoreMatcher}),
    });

    this.challengeForm.get('date').valueChanges.subscribe(value => this.enableScores(value));
  }

  onSave() {
    const toSave: Challenge = {
      id: this.rung.challenger.id,
      name: this.rung.challenger.name,
      created: this.rung.challenger.created,
      dateTime: this.challengeForm.get('date').get('eventDate').value, // TODO: Combine the time
      status: 'ACCEPTED',
      challengerId: this.rung.challenger.challengerId,
      challengedId: this.rung.challenger.challengedId,
      scoreChallenger: this.scoreChallenger.value,
      scoreChallenged: this.scoreChallenged.value
    };

    this.challengeService.putChallenge(toSave)
      .subscribe(challenge => {
        this.rung.challenger = challenge;
        this.onDone();
      })
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
   * @returns {[{[key: string]: boolean}]}
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
