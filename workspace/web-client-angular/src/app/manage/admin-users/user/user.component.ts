import {Component, Inject, OnInit} from '@angular/core';
import {ErrorHandlingService} from "../../../services/error-handling.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {User, UsersService} from "../../../services/users.service";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  editMode: boolean = false;
  submitText: string = "CREATE";
  creationDescription: String = "To create a new user, please enter a few details";
  details: User = <User>{};

  constructor(private usersService: UsersService,
              private errorHandlingService: ErrorHandlingService,
              public dialogRef: MatDialogRef<UserComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit() {
    if (this.data.item !== undefined) {
      this.details.id = this.data.item.id;
      this.details.email = this.data.item.email;
      this.details.firstName = this.data.item.firstName;
      this.details.lastName = this.data.item.lastName;

      this.editMode = true;
      this.submitText = "SAVE";
      this.creationDescription = "This allows the user admin to modify the details for the user"
    }
  }

  makeUserChange() {
    let user: User = {
      id: this.details.id,
      name: this.details.name,
      firstName: this.details.firstName,
      lastName: this.details.lastName,
      email: this.details.email
    };

    if (this.editMode === true) {
      this.editUser(user)
    } else {
      this.addUser(user)
    }
  }

  addUser(user) {
    this.usersService.createOne(user)
      .subscribe(success => {
        if (success) {
          alert("The user - " + user.email + " successfully created.");
          this.closeWindow(true);
        }
      }, err => {
        this.errorHandlingService.handleError(err);
      });
  }

  editUser(user) {
    this.usersService.updateOne(user.id, user)
      .subscribe(success => {
        if (success) {
          alert("The organisation - " + user.email + " successfully edited.");
          this.closeWindow(true);
        }
      }, err => {
        this.errorHandlingService.handleError(err);
      });
  }

  cancel() {
    this.closeWindow(false)
  }

  closeWindow(requiresUpdate: boolean) {
    this.dialogRef.close(requiresUpdate);
  }
}
