import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-calendar-main',
  templateUrl: './calendar-main.component.html',
  styleUrls: ['./calendar-main.component.css']
})
export class CalendarMainComponent implements OnInit {

  selectedIndex = 0;

  constructor() {
  }

  ngOnInit() {
  }
}
