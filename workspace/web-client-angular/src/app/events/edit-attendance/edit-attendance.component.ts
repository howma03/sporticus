import {Component, Input, OnInit} from '@angular/core';
import {Attendance} from "../../services/event-attendance.service";

@Component({
  selector: 'app-edit-attendance',
  templateUrl: './edit-attendance.component.html',
  styleUrls: ['./edit-attendance.component.css']
})
export class EditAttendanceComponent implements OnInit {

  @Input()
  attendance: Attendance;

  @Input()
  event: Event;

  constructor() { }

  ngOnInit() {
  }

}
