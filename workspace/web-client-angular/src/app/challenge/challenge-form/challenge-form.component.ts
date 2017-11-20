import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {LadderUser} from "../../services/ladder.service";

@Component({
  selector: 'app-challenge-form',
  templateUrl: './challenge-form.component.html',
  styleUrls: ['./challenge-form.component.css'],
})
export class ChallengeFormComponent implements OnInit, OnChanges {

  constructor(private fb: FormBuilder) {
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
    let eventDate = new FormControl(null, Validators.required);
    let challengerScore = new FormControl(null);
    let challengedScore = new FormControl(null);
//    let confirmPassword = new FormControl('', me => password.value === me.value ? null : {mismatch: true});
//
//     password.valueChanges.subscribe(() => confirmPassword.updateValueAndValidity());

    this.challengeForm = this.fb.group({
      eventDate,
      challengerScore,
      challengedScore
    });
  }

  onSave() {
    // const formModel = this.profileForm.value;
    // const toSave: User = {
    //   id: this.user.id,
    //   email: this.user.email,
    //
    //   firstName: formModel.firstName,
    //   lastName: formModel.lastName
    // };
    // if (formModel.password) {
    //   toSave.password = formModel.password;
    // }
    //
    // this.profileService.saveUser(toSave)
    //   .subscribe(user => {
    //     this.user = user;
    //     this.onDone();
    //   })
  }

  onCancel() {
    this.onDone();
  }

  onDone() {
    this.ngOnChanges();
    this.done.emit();
  }
}
