import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-manage-group-main',
  templateUrl: './manage-group-main.component.html',
  styleUrls: ['./manage-group-main.component.css']
})
export class ManageGroupMainComponent implements OnInit {

  @Input()
  public organisationId: number;

  constructor() {
  }

  ngOnInit() {
  }

}
