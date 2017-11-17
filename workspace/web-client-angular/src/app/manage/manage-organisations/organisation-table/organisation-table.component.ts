import {Component, OnDestroy, OnInit} from '@angular/core';
import {Organisation, OrganisationService} from "../../../services/organisation.service";
import {Subscription} from "rxjs/Subscription";
import 'rxjs/add/observable/of';
import {OrganisationComponent} from "../organisation/organisation.component";
import {MatDialog} from "@angular/material";

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
    this.openModal();
  }
  public delete(itemId) {
    console.info("delete - id="+itemId);
    this.organisationService.deleteOne(itemId).subscribe();
  }

  public openModal() : void {
    console.info("Launch dialog");
    let dialogRef = this.dialog.open(OrganisationComponent, {
      height: '900px',
      width: '1200px',
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      // this.animal = result;
    });
  }
}


