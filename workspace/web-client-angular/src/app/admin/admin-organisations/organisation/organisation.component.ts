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

  creationDescription: String = "To create a new organisation, please enter a few details";

  editMode: boolean = false;
  submitText: string = "CREATE";

  ngOnInit() {

    if(this.data.item !== undefined) {
      this.organisationDetails.name = this.data.item.name;
      this.organisationDetails.address = this.data.item.address;
      this.organisationDetails.domain = this.data.item.domain;
      this.organisationDetails.urlFragment = this.data.item.urlFragment;
      this.organisationDetails.postcode = this.data.item.postcode;
      this.organisationDetails.id = this.data.item.id;

      this.editMode = true;
      this.submitText = "SAVE";
      this.creationDescription = "This allows the user admin to modify the details for the organisation"
    }
  }

  organisationDetails = {
    name: '',
    address: '',
    domain: '',
    urlFragment: '',
    postcode: '',
    id: -1
  };

  makeOrganisationChange() {
    let organisation : Organisation = {
      name: this.organisationDetails.name,
      address: this.organisationDetails.address,
      domain: this.organisationDetails.domain,
      urlFragment: this.organisationDetails.urlFragment,
      postcode: this.organisationDetails.postcode,
      id: this.organisationDetails.id
    };

    if(this.editMode === true) {
      this.editOrganisation(organisation)
    } else {
      this.addOrganisation(organisation)
    }
  }

  addOrganisation(organisation) {
    this.organisationService.createOne(organisation)
      .subscribe(success => {
        if (success) {
          this.closeWindow(true);
        }
      }, err => {
        this.errorHandlingService.handleError(err);
      });
  }


  editOrganisation(organisation) {
    this.organisationService.updateOne(organisation.id, organisation)
      .subscribe(success => {
        if (success) {
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
