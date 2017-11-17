import { Component, OnInit } from '@angular/core';
import {Organisation, OrganisationService} from "../../../services/organisation.service";
import {ErrorHandlingService} from "../../../services/error-handling.service";

@Component({
  selector: 'app-organisation',
  templateUrl: './organisation.component.html',
  styleUrls: ['./organisation.component.css']
})
export class OrganisationComponent implements OnInit {

  constructor(
    private organisationService: OrganisationService,
    private errorHandlingService: ErrorHandlingService
  ) { }

  ngOnInit() {
  }

  organisationDetails = {
    name: '',
    address: '',
    domain: ''
  };

  addOrganisation() {
    let newOrganisation : Organisation = {
      name: this.organisationDetails.name,
      address: this.organisationDetails.address,
      domain: this.organisationDetails.domain
    };

    this.organisationService.createOne(newOrganisation)
      .subscribe(success => {
        if (success) {
         alert("Organisation" + newOrganisation.name +  " successfully created.");
        }
      }, err => {
          this.errorHandlingService.handleError(err);
      });
  }

  cancel() {
    alert("Todo");
  }
}
