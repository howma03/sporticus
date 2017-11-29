import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-manage-group-members',
  templateUrl: './manage-group-members.component.html',
  styleUrls: ['./manage-group-members.component.css']
})
export class ManageGroupMembersComponent implements OnInit {

  @Input()
  public group;

  constructor() {
  }

  ngOnInit() {
  }

}