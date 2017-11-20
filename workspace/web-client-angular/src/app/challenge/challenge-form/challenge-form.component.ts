import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {LadderUser} from "../../services/ladder.service";
import {Challenge, ChallengeService} from '../../services/challenge.service';

@Component({
  selector: 'app-challenge-form',
  templateUrl: './challenge-form.component.html',
  styleUrls: ['./challenge-form.component.css'],
})
export class ChallengeFormComponent implements OnInit, OnChanges {

  constructor(private fb: FormBuilder, private challengeService : ChallengeService) {
    this.createForm();
  }

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

  get lastName() {
    return this.challengeForm.get('challengerScore')
  }

  get password() {
    return this.challengeForm.get('challengedScore')
  }

  ngOnInit() {
  }

  ngOnChanges() {
    this.challengeForm.reset({
      eventDate: null,
      challengerScore: null,
      challengedScore: null
    });
  }

  createForm() {
    let eventDate = new FormControl(null, Validators.required); // TODO: We're waiting upon the Material datetime picker
    let eventTime = new FormControl(null, Validators.required); // to enter date and time as a single field
    let challengerScore = new FormControl(null);
    let challengedScore = new FormControl(null);

    this.challengeForm = this.fb.group({
      eventDate,
      eventTime,
      challengerScore,
      challengedScore
    });
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
      challengerScore: formModel.challengerScore,
      challengedScore:formModel.challengedScore
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
    this.ngOnChanges();
    this.done.emit();
  }
}
