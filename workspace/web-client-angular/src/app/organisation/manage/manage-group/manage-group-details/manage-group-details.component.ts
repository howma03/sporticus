import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-manage-group-details',
  templateUrl: './manage-group-details.component.html',
  styleUrls: ['./manage-group-details.component.css']
})
export class ManageGroupDetailsComponent implements OnInit {

  @Input()
  public group;

  constructor() {
  }

  ngOnInit() {
  }

}
