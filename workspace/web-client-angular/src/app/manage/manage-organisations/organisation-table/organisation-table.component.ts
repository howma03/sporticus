import {Component, OnDestroy, OnInit} from '@angular/core';
import {Organisation, OrganisationService} from "../../../services/organisation.service";
import {Subscription} from "rxjs/Subscription";
import 'rxjs/add/observable/of';
import {OrganisationComponent} from "../organisation/organisation.component";
import {MatDialog} from "@angular/material";
import {DeletePromptComponent} from "../../delete-prompt/delete-prompt.component";

@Component({
  selector: 'organisation-table',
  templateUrl: './organisation-table.component.html',
  styleUrls: ['./organisation-table.component.css']
})
export class OrganisationTableComponent implements OnInit, OnDestroy {

  public orgs: Organisation[] = [];
  private subscription: Subscription;

  constructor(private organisationService: OrganisationService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.subscription = this.organisationService.retrieveAll()
      .map(list=>list.data)
      .subscribe((orgs: Organisation[])=>{
        this.orgs = orgs;
      });
  }
  ngOnDestroy() {
    if(this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  public view(itemId) {
    console.info("view - id="+itemId);
    this.openModal(itemId);
  }

  openDeleteDialog(item) {
    let dialogRef = this.dialog.open(DeletePromptComponent, {
      data: {
        id: item.id,
        type: 'organisation',
        organisationName: item.name
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result === true) {
        this.delete(item.id);
      }
    });
  }

  public delete(itemId) {
    console.info("delete - id="+itemId);
    this.organisationService.deleteOne(itemId).subscribe();
  }

  public openModal(itemId) : void {
    debugger;
    let item = this.orgs.find(item => item.id === itemId);

    console.info("Launch dialog");
    let dialogRef = this.dialog.open(OrganisationComponent, {
      data: {
        item: item
      },
      height: '900px',
      width: '1200px',
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      // this.animal = result;
    });
  }
}


