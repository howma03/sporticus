import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {ProfileService} from "../../services/profile.service";
import {User} from "../../services/users.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit, OnChanges {

  constructor(private profileService: ProfileService, private fb: FormBuilder) {
    this.createForm();
  }

  @Input()
  user: User;

  @Output()
  done = new EventEmitter<User>();

  profileForm: FormGroup;

  get firstName() {
    return this.profileForm.get('firstName')
  }

  get lastName() {
    return this.profileForm.get('lastName')
  }

  get password() {
    return this.profileForm.get('password')
  }

  get confirmPassword() {
    return this.profileForm ? this.profileForm.get('confirmPassword') : null;
  }

  ngOnInit() {
  }

  ngOnChanges() {
    this.profileForm.reset({
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      password: '',
      confirmPassword: ''
    });
  }

  createForm() {
    let firstName = new FormControl('', Validators.required);
    let lastName = new FormControl('', Validators.required);
    let password = new FormControl('');
    let confirmPassword = new FormControl('', me => password.value === me.value ? null : {mismatch: true});

    password.valueChanges.subscribe(() => confirmPassword.updateValueAndValidity());

    this.profileForm = this.fb.group({
      firstName,
      lastName,
      password,
      confirmPassword
    });
  }

  onSave() {
    const formModel = this.profileForm.value;
    const toSave: User = {
      id: this.user.id,
      email: this.user.email,

      firstName: formModel.firstName,
      lastName: formModel.lastName
    };
    if (formModel.password) {
      toSave.password = formModel.password;
    }

    this.profileService.saveUser(toSave)
      .subscribe(user => {
        this.user = user;
        this.onDone();
      })
  }

  onCancel() {
    this.onDone();
  }

  onDone() {
    this.ngOnChanges();
    this.done.emit(this.user);
  }

}
