import {Component, Input, OnInit} from '@angular/core';
import {Group, OrganisationGroupsService} from "../../../../services/organisation-groups.service";

@Component({
  selector: 'app-manage-group-main',
  templateUrl: './manage-group-main.component.html',
  styleUrls: ['./manage-group-main.component.css']
})
export class ManageGroupMainComponent implements OnInit {

  @Input()
  public organisationId: number;

  selectedGroup;

  groups: Group[] = [];
  groupId: number = null;


  constructor(
    private organisationGroupsService: OrganisationGroupsService
  ) {
  }

  ngOnInit() {
    this.organisationGroupsService.retrieveAll(this.organisationId).subscribe(groups => {
      this.groups = groups.data;
      if (this.groupId === null) {
        this.groupId = this.groups[0].id;
        this.selectedGroup = this.groups[0];
      }
    });
  }

  onGroupSelect(group) {
    this.selectedGroup = group;
  }

}
