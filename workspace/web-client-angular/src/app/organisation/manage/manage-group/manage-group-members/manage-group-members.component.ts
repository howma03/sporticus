import {Component, Input, OnInit} from '@angular/core';
import {GroupMemberService} from '../../../../services/group-member.service';

@Component({
  selector: 'app-manage-group-members',
  templateUrl: './manage-group-members.component.html',
  styleUrls: ['./manage-group-members.component.css']
})
export class ManageGroupMembersComponent implements OnInit {

  @Input()
  public group;

  members = [];

  constructor(
    public groupMemberService: GroupMemberService
  ) {
  }

  ngOnInit() {
    this.groupMemberService.retrieveAll(this.group.id).subscribe(members => {
      this.members = members.data;
    });
  }

}
