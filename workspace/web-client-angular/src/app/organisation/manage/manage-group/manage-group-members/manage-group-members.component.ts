import {Component, Input, OnInit} from '@angular/core';
import {GroupMemberService} from '../../../../services/group-member.service';

@Component({
  selector: 'app-manage-group-members',
  templateUrl: './manage-group-members.component.html',
  styleUrls: ['./manage-group-members.component.css']
})
export class ManageGroupMembersComponent implements OnInit {
  get group() {
    return this._group;
  }

  @Input()
  set group(value) {
    this._group = value;
    this.getGroupMembers();
  }

  private _group;

  members = [];

  groupMembersCount = 0;

  constructor(
    public groupMemberService: GroupMemberService
  ) {
  }

  ngOnInit() {
  }

  getGroupMembers() {
    this.groupMemberService.retrieveAll(this._group.id).subscribe(members => {
      this.members = members.data;
      this.groupMembersCount = this.members.length;
    });
  }

}
