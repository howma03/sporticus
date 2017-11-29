import {Component, Input, OnInit} from '@angular/core';
import {Group, OrganisationGroupsService} from "../../../../services/organisation-groups.service";

@Component({
  selector: 'app-manage-group-details',
  templateUrl: './manage-group-details.component.html',
  styleUrls: ['./manage-group-details.component.css']
})
export class ManageGroupDetailsComponent implements OnInit {

  @Input()
  public group: Group;

  constructor() {
  }

  ngOnInit() {
  }

}
