import {Component, OnInit} from '@angular/core';
import 'rxjs/add/observable/of';
import {MatDialog} from "@angular/material";
import {User, UsersService} from "../../../services/users.service";
import {UserComponent} from "../user/user.component";
import {ConfirmDialogComponent} from "../../../shared/confirm-dialog/confirm-dialog.component";
import {Message} from "primeng/primeng";
import {MessageService} from "primeng/components/common/messageservice";

@Component({
  selector: 'user-table',
  templateUrl: './user-table.component.html',
  styleUrls: ['./user-table.component.css']
})
export class UserTableComponent implements OnInit {

  public users: User[] = [];
  protected msgs: Message[] = [];

  constructor(private usersService: UsersService,
              private dialog: MatDialog,
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.updateUserDetails()
  }

  updateUserDetails() {
    this.usersService.retrieveAll()
      .map(list => list.data)
      .subscribe((users: User[]) => {
        this.users = users;
      });
  }

  public view(itemId) {
    console.info("view - id=" + itemId);
    this.openModal(itemId);
  }

  public openModal(itemId): void {
    let item = this.users.find(item => item.id === itemId);

    console.info("Launch dialog");
    let dialogRef = this.dialog.open(UserComponent, {
      data: {
        item: item
      },
      height: '900px',
      width: '1200px',
    });
    dialogRef.afterClosed().subscribe((updateRequired) => {
      if (updateRequired) {
        this.messageService.add({severity:'success', summary:'User updated', detail:'User details updated'});
        this.updateUserDetails();
      }
    });
  }

  openDeleteDialog(item) {
    let title = 'Confirm delete';
    let description = "Are you sure you want to delete the user - '" + item.firstName + " " + item.lastName + "' (" + item.email + ")";

    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: title,
        description: description,
        organisationName: item.name
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.deleteUser(item.id);
      }
    });
  }

  public deleteUser(itemId) {
    this.usersService.deleteOne(itemId).subscribe(() => {
      this.updateUserDetails();
    });
  }
}


