import { Component, OnInit } from '@angular/core';
import {DataSource} from '@angular/cdk/collections';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import {Organisation, OrganisationService} from "../../../services/organisation.service";

@Component({
  selector: 'organisation-table',
  templateUrl: './organisation-table.component.html',
  styleUrls: ['./organisation-table.component.css']
})
export class OrganisationTableComponent implements OnInit {

  constructor(private organisationService: OrganisationService) { }

  ngOnInit() {
  }

  displayedColumns = ['name', 'address', 'domain'];
  dataSource = new MyOrganisationsDataSource(this.organisationService);
}


export class MyOrganisationsDataSource extends DataSource<any> {
  constructor(private organisationService: OrganisationService) {
    super();
  }
  connect(): Observable<Organisation[]> {
    return this.organisationService.retrieveAll().map(list=> list.data);
  }
  disconnect() {}
}

