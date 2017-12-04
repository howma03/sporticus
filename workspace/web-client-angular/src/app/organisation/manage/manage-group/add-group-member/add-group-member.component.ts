import {Component, Input, OnInit} from '@angular/core';
import {GroupMemberService} from '../../../../services/group-member.service';
import {OrganisationUsersService} from '../../manage-members/organisation-users.service';
import {User} from '../../../../services/users.service';

@Component({
  selector: 'app-add-group-member',
  templateUrl: './add-group-member.component.html',
  styleUrls: ['./add-group-member.component.css']
})
export class AddGroupMemberComponent implements OnInit {

  @Input()
  organisationId: number;

  @Input()
  groupId: number;

  @Input()
  members: User[] = [];

  orgMembers: User[] = [];


  constructor(private membersService: OrganisationUsersService,
              private groupMemberService: GroupMemberService) {
  }

  ngOnInit() {
    this.membersService.retrieveAll(this.organisationId)
      .map(list => list.data)
      .map(orgMembers => orgMembers.filter(orgMember => {
        return !this.members.some(groupMember => groupMember.id === orgMember.id);
      }))
      .subscribe(orgMembers => this.orgMembers = orgMembers);
  }

  addToGroup(user: User) {
    this.groupMemberService.addOne(this.groupId, {...user}).subscribe(addedUser => {
      this.members.push(addedUser);
      this.orgMembers = this.orgMembers.filter(orgMember => orgMember.id !== addedUser.id);
    });
  }

}
