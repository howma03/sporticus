import {Component, Input, OnInit} from '@angular/core';
import {Groups, OrganisationGroupsService} from "../../../../services/organisation-groups.service";

@Component({
  selector: 'app-manage-group-main',
  templateUrl: './manage-group-main.component.html',
  styleUrls: ['./manage-group-main.component.css']
})
export class ManageGroupMainComponent implements OnInit {

  @Input()
  public organisationId: number;

  groups: Groups[] = [];
  groupId: number = null;


  constructor(
    private organisationGroupsService: OrganisationGroupsService
  ) {
  }

  ngOnInit() {
    /*this.organisationGroupsService.retrieveAll().subscribe(groups => {
      this.groups = groups.data;
      if (this.groupId === null) {
        this.groupId = this.groups[0].id;
      }
    });*/
  }

  onGroupSelect(a) {
    var test = 'cat';
  }

}
