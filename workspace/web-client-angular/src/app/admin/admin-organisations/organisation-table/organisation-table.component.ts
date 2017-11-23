import {Component, OnInit} from '@angular/core';
import {Organisation, OrganisationService} from "../../../services/organisation.service";
import {Subscription} from "rxjs/Subscription";
import 'rxjs/add/observable/of';
import {OrganisationComponent} from "../organisation/organisation.component";
import {MatDialog} from "@angular/material";
import {ConfirmDialogComponent} from "../../../shared/confirm-dialog/confirm-dialog.component";

@Component({
  selector: 'organisation-table',
  templateUrl: './organisation-table.component.html',
  styleUrls: ['./organisation-table.component.css']
})
export class OrganisationTableComponent implements OnInit {

  public orgs: Organisation[] = [];
  private subscription: Subscription;

  constructor(private organisationService: OrganisationService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.updateOrganisationDetails()
  }

  public view(itemId) {
    console.info("view - id="+itemId);
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

    console.info("Launch dialog");
    let dialogRef = this.dialog.open(OrganisationComponent, {
      data: {
        item: item
      },
      height: '900px',
      width: '1200px',
    });
    dialogRef.afterClosed().subscribe((updateRequired) => {
      debugger;
      if (updateRequired) {
        this.updateOrganisationDetails();
      }
    });
  }

  openDeleteDialog(item) {
    let title = 'Confirm delete';
    let description = "Are you sure you want to delete the organisation - " + item.name;

    let dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: {
        title: title,
        description: description,
        organisationName: item.name
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result === true) {
        this.deleteOrganisation(item.id);
      }
    });
  }

  public deleteOrganisation(itemId) {
    console.info("delete - id="+itemId);
    this.organisationService.deleteOne(itemId).subscribe(() => {
      this.updateOrganisationDetails();
    });
  }
}


