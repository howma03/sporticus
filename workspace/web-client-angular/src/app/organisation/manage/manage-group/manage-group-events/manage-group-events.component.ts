import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-manage-group-events',
  templateUrl: './manage-group-events.component.html',
  styleUrls: ['./manage-group-events.component.css']
})
export class ManageGroupEventsComponent implements OnInit {

  @Input()
  public group;

  constructor() {
  }

  ngOnInit() {
  }

}