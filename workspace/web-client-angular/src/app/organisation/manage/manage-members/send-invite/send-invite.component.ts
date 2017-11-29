import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {User} from '../../../../services/users.service';
import {OrganisationUsersService} from '../organisation-users.service';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/forkJoin';

@Component({
  selector: 'app-send-invite',
  templateUrl: './send-invite.component.html',
  styleUrls: ['./send-invite.component.css'],
})
export class SendInviteComponent implements OnInit {

  @Input()
  organisationId: number;
  removable = true;
  selectable = true;
  users: User[] = [];

  @Output()
  done = new EventEmitter<any>();
  inviteForm: FormGroup;

  get firstNameInput() {
    return this.inviteForm.get('firstName');
  }

  get lastNameInput() {
    return this.inviteForm.get('lastName');
  }

  get emailInput() {
    return this.inviteForm.get('email');
  }

  constructor(private fb: FormBuilder, private memeberService: OrganisationUsersService) {
    this.inviteForm = this.fb.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.email, this.alreadyIn.bind(this)]]
    });
  }

  alreadyIn(emailInput: FormControl) {
    return this.users.findIndex(user => user.email === emailInput.value) < 0 ? null : {
      alreadyIn: {
        value: emailInput.value
      }
    };
  }

  ngOnInit() {
  }


  add(): void {

    this.emailInput.markAsTouched();
    this.emailInput.updateValueAndValidity();

    if (this.emailInput.valid && this.firstNameInput.valid && this.lastNameInput.valid) {
      const email = this.emailInput.value.trim();
      const firstName = this.firstNameInput.value.trim();
      const lastName = this.lastNameInput.value.trim();

      this.users.push({
        firstName,
        lastName,
        email
      });

      this.emailInput.reset('');
      this.firstNameInput.reset('');
      this.lastNameInput.reset('');
    }
  }


  remove(user: User): void {
    const index = this.users.indexOf(user);

    if (index >= 0) {
      this.users.splice(index, 1);
    }
  }

  send() {
    Observable.forkJoin(...this.users.map(user => this.memeberService.inviteUser(this.organisationId, user)))
      .subscribe(() => this.done.emit());
  }


}
