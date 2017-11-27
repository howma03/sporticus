import {Component, OnInit} from '@angular/core';
import {Organisation, OrganisationService} from '../../../services/organisation.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {isNumeric} from 'rxjs/util/isNumeric';

@Component({
  selector: 'app-manage-main',
  templateUrl: './manage-main.component.html',
  styleUrls: ['./manage-main.component.css'],
})
export class ManageMainComponent implements OnInit {

  constructor(private organisationService: OrganisationService, private router: Router, private route: ActivatedRoute) {
  }


  organisations: Organisation[] = [];
  private organisationId: number = null;

  get organisation(): Organisation {
    return this.organisations.find(org => org.id === this.organisationId);
  }

  subComponent = 'organisation';
  tabs: string[] = ['organisation', 'members', 'competitions'];

  get selectedIndex(): number {
    return this.tabs.indexOf(this.subComponent);
  }

  set selectedIndex(index: number) {
    this.subComponent = this.tabs[index];
    this.updateRouter();
  }


  ngOnInit() {
    this.organisationService.retrieveAll().subscribe(organisations => {
      this.organisations = organisations.data;
      if (this.organisationId === null) {
        this.organisationId = this.organisations[0].id;
        this.updateRouter();
      }
    });
    this.route.paramMap.subscribe((params: ParamMap) => {
      this.subComponent = params.get('tab') || this.subComponent;
      const id = params.get('id');
      if (isNumeric(id)) {
        this.organisationId = parseInt(id, 10);
      } else {
        if (this.organisations.length > 0) {
          this.organisationId = this.organisations[0].id;
          this.updateRouter();
        }
      }
    });
  }


  updateRouter() {
    if (this.organisationId != null && this.subComponent != null) {
      this.router.navigate(['./', this.organisationId, this.subComponent], {relativeTo: this.route.parent});

    }
  }

  updateOrganisation(organisation: Organisation) {
    this.organisations[this.organisations.findIndex(org => organisation.id === org.id)] = organisation;
  }
}
