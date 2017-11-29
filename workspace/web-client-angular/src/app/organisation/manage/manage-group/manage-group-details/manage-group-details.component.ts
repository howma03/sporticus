import {Component, Input, OnInit} from '@angular/core';
import {Group, OrganisationGroupsService} from "../../../../services/organisation-groups.service";

@Component({
  selector: 'app-manage-group-details',
  templateUrl: './manage-group-details.component.html',
  styleUrls: ['./manage-group-details.component.css']
})
export class ManageGroupDetailsComponent implements OnInit {

  get group(): Group {
    return this._group;
  }

  @Input()
  set group(value: Group) {
    this._group = value;
  }

  private _group: Group;

  constructor() {
  }

  ngOnInit() {
  }

}
