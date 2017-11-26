import {Component, OnInit} from '@angular/core';
import {Organisation, OrganisationService} from "../../../services/organisation.service";
import {Subscription} from "rxjs/Subscription";
import 'rxjs/add/observable/of';
import {OrganisationComponent} from "../organisation/organisation.component";
import {MatDialog} from "@angular/material";
import {ConfirmDialogComponent} from "../../../shared/confirm-dialog/confirm-dialog.component";
import {Message} from "primeng/primeng";
import {MessageService} from "primeng/components/common/messageservice";

@Component({
  selector: 'organisation-table',
  templateUrl: './organisation-table.component.html',
  styleUrls: ['./organisation-table.component.css']
})
export class OrganisationTableComponent implements OnInit {

  public orgs: Organisation[] = [];
  private subscription: Subscription;
  protected msgs: Message[] = [];

  constructor(private organisationService: OrganisationService,
              private dialog: MatDialog,
              private messageService: MessageService) {
  }

  ngOnInit() {
    this.updateOrganisationDetails()
  }

  public view(itemId) {
    this.openModal(itemId);
  }

  updateOrganisationDetails() {
    this.organisationService.retrieveAll()
      .map(list=>list.data)
      .subscribe((orgs: Organisation[])=>{
        this.orgs = orgs;
      });
  }

  public openModal(itemId): void {
    let item = this.orgs.find(item => item.id === itemId);

    let dialogRef = this.dialog.open(OrganisationComponent, {
      data: {
        item: item
      },
      height: '900px',
      width: '1200px',
    });
    dialogRef.afterClosed().subscribe((updateRequired) => {
      if (updateRequired) {
        this.updateOrganisationDetails();
        this.messageService.add({severity:'success', summary:'Organisation updated', detail:'Organisation details updated'});
      }
    });
  }

  openDeleteDialog(item) {
    let title = 'Confirm delete';
    let description = "Are you sure you want to delete the organisation - " + item.name;

    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: title,
        description: description
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result === true) {
        this.deleteOrganisation(item.id);
      }
    });
  }

  public deleteOrganisation(itemId) {
    this.organisationService.deleteOne(itemId).subscribe(() => {
      this.updateOrganisationDetails();
      this.messageService.add({
        severity: 'warn',
        summary: 'Organisation deleted',
        detail: 'Organisation deleted successfully'
      });
    });
  }
}


