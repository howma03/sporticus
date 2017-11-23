import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-manage-main',
  templateUrl: './manage-main.component.html',
  styleUrls: ['./manage-main.component.css'],
})
export class ManageMainComponent implements OnInit {

  constructor() {
  }

  ngOnInit() {
  }

  navLinks = [{
    path: 'organisation',
    label: 'Organisation'
  }, {
    path: 'members',
    label: 'Members'
  }, {
    path: 'competitions',
    label: 'Competitions'
  }];
}
