import { Component, OnInit } from '@angular/core';
import {EventAttendanceService} from "../../services/event-attendance.service";

@Component({
  selector: 'app-event-attendance-management',
  templateUrl: './event-attendance-management.component.html',
  styleUrls: ['./event-attendance-management.component.css']
})
export class EventAttendanceManagementComponent implements OnInit {

  constructor(
    private eventAttendanceService: EventAttendanceService
  ) { }

  ngOnInit() {
  }

}
