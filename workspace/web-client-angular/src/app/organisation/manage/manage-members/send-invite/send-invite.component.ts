import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

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
  addOnBlur = false;
  separatorKeysCodes = [ENTER, COMMA];
  emails: string[] = [];
  @Output()
  done = new EventEmitter<any>();
  inviteForm: FormGroup;

  get emailInput() {
    return this.inviteForm.get('email');
  }

  constructor(private fb: FormBuilder) {
    this.inviteForm = this.fb.group({
      email: ['', [Validators.email, this.alreadyIn.bind(this)]]
    });
  }

  alreadyIn(emailInput: FormControl) {
    return this.emails.indexOf(emailInput.value) < 0 ? null : {
      alreadyIn: {
        value: emailInput.value
      }
    };
  }

  ngOnInit() {
  }


  add(): void {
    const input = this.emailInput;
    const value = input.value;
    input.markAsTouched();
    input.updateValueAndValidity();
    if (input.valid) {
      if ((value || '').trim()) {
        this.emails.push(value);
      }

      // Reset the input value
      if (input) {
        input.reset('');
      }
    }
  }


  remove(email: string): void {
    const index = this.emails.indexOf(email);

    if (index >= 0) {
      this.emails.splice(index, 1);
    }
  }

  send() {
    this.done.emit();
  }


}
