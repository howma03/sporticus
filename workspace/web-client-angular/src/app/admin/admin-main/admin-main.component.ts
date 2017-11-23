import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-admin-main',
  templateUrl: './admin-main.component.html',
  styleUrls: ['./admin-main.component.css']
})
export class AdminMainComponent implements OnInit {

  constructor() {
  }

  ngOnInit() {
  }

  navLinks = [{
    path: 'users',
    label: 'Users'
  }, {
    path: 'organisations',
    label: 'Organisations'
  }];

}
