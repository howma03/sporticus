import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
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
    return this.challengeForm.get('eventDate')
  }

  get eventTime() {
    return this.challengeForm.get('eventTime')
  }

  get scoreChallenger() {
    return this.challengeForm.get('scoreChallenger')
  }

  get scoreChallenged() {
    return this.challengeForm.get('scoreChallenged')
  }

  ngOnInit() {
    this.challengeForm = this.fb.group({
      eventDate: [null, [Validators.required]], // TODO: We're waiting upon the Material datetime picker,
      eventTime: [null, [Validators.required]], // to enter date and time as a single field,
      scoreChallenger: [null],
      scoreChallenged: [null]
    }, {validator: this.scoreMatcher});
  }

  onSave() {
    const formModel = this.challengeForm.value;
    const toSave: Challenge = {
      id: this.rung.challenger.id,
      name: this.rung.challenger.name,
      created: this.rung.challenger.created,
      dateTime: formModel.eventDate, // TODO: Combine the time
      status: 'ACCEPTED',
      challengerId: this.rung.challenger.challengerId,
      challengedId: this.rung.challenger.challengedId,
      scoreChallenger: formModel.scoreChallenger,
      scoreChallenged: formModel.scoreChallenged
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
   * @returns {{[p: string]: boolean}}
   */
  scoreMatcher(c: AbstractControl): {[key: string]: boolean} | null {
    let scoreChallengerControl = c.get('scoreChallenger');
    let scoreChallengedControl = c.get('scoreChallenged');

    if ((scoreChallengerControl.value == null) !== (scoreChallengedControl.value == null)) {
      return {'bothscores': true}
    }
    return null;
  }
}
