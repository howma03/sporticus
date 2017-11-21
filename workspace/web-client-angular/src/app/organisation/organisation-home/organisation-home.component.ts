import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {OrganisationService} from "../../services/organisation.service";

@Component({
  selector: 'app-organisation-home',
  templateUrl: './organisation-home.component.html',
  styleUrls: ['./organisation-home.component.css']
})
export class OrganisationHomeComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private organisationService: OrganisationService,) { }

  ngOnInit() {
    if (this.route.snapshot.params['fragmentUrl'] !== undefined) {
      this.organisation.fragmentUrl = this.route.snapshot.params['fragmentUrl'];
      this.organisation.name = "Organisation page for fragment" + this.organisation.fragmentUrl;

      this.organisationService.getOrganisationByUrlFragment(this.organisation.fragmentUrl).subscribe(data=> {
        this.loading = false;
        this.organisation.name = data.name;


      })
    }
  }

  loading = true;

  organisation = {
    name: '',
    fragmentUrl: ''
  }

}
