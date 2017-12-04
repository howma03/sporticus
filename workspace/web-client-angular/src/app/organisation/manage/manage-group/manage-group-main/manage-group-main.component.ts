import {Component, Input, OnInit} from '@angular/core';
import {Group, OrganisationGroupsService} from '../../../../services/organisation-groups.service';
import {MatDialog} from '@angular/material';
import {CreateGroupDialogComponent} from '../create-group-dialog/create-group-dialog.component';

@Component({
  selector: 'app-manage-group-main',
  templateUrl: './manage-group-main.component.html',
  styleUrls: ['./manage-group-main.component.css']
})
export class ManageGroupMainComponent implements OnInit {

  get organisationId(): number {
    return this._organisationId;
  }

  @Input()
  set organisationId(value: number) {
    this._organisationId = value;
    this.updateGroups();
  }

  private _organisationId: number;

  selectedGroup: Group = <Group> {
    id: -1
  };

  groups: Group[] = [];
  groupId: number = null;


  constructor(private dialog: MatDialog,
              private organisationGroupsService: OrganisationGroupsService
  ) {
  }

  ngOnInit() {
  }

  updateGroups() {
    this.organisationGroupsService.retrieveAll(this._organisationId).subscribe(groups => {
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

  showCreateGroupDialog() {
    this.dialog.open(CreateGroupDialogComponent, {
      data: {
        organisationId: this.organisationId
      }
    });
  }

}
