import {Component, Inject, OnInit} from '@angular/core';
import {Organisation, OrganisationService} from "../../../services/organisation.service";
import {ErrorHandlingService} from "../../../services/error-handling.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-organisation',
  templateUrl: './organisation.component.html',
  styleUrls: ['./organisation.component.css']
})
export class OrganisationComponent implements OnInit {

  constructor(
    private organisationService: OrganisationService,
    private errorHandlingService: ErrorHandlingService,
    public dialogRef: MatDialogRef<OrganisationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {

    if(this.data.item !== undefined) {
      this.organisationDetails = this.data.item;
    }
  }

  organisationDetails = {
    name: '',
    address: '',
    domain: '',
    urlFragment: '',
    postcode: ''
  };

  addOrganisation() {
    let newOrganisation : Organisation = {
      name: this.organisationDetails.name,
      address: this.organisationDetails.address,
      domain: this.organisationDetails.domain,
      urlFragment: this.organisationDetails.urlFragment,
      postcode: this.organisationDetails.postcode
    };

    this.organisationService.createOne(newOrganisation)
      .subscribe(success => {
        if (success) {
         alert("Organisation " + newOrganisation.name +  " successfully created.");
         this.closeWindow();
        }
      }, err => {
          this.errorHandlingService.handleError(err);
      });
  }

  closeWindow() {
    this.dialogRef.close();
  }
}
