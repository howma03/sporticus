import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-calender-main',
  templateUrl: './calender-main.component.html',
  styleUrls: ['./calender-main.component.css']
})
export class CalenderMainComponent implements OnInit {

  selectedIndex = 0;

  constructor() {
  }

  ngOnInit() {
  }
}
