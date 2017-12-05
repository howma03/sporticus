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

  groups: Group[] = [];
  groupId: number = null;
  selectedGroup: Group = null;


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

  onGroupSelect(event) {
    this.groupId = event.value;
    this.selectedGroup = this.groups.find(group => group.id === this.groupId);
  }

  showCreateGroupDialog() {
    this.dialog.open(CreateGroupDialogComponent, {
      data: {
        organisationId: this.organisationId
      }
    }).afterClosed().subscribe(result => {
      if (result) {
        this.groups.push(result);
        this.groupId = result.id;
        this.selectedGroup = result;
      }
    });
  }

}
