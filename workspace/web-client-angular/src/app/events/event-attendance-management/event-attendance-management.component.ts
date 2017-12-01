import { Component, OnInit } from '@angular/core';
import {Attendance, EventAttendanceService} from "../../services/event-attendance.service";

@Component({
  selector: 'app-event-attendance-management',
  templateUrl: './event-attendance-management.component.html',
  styleUrls: ['./event-attendance-management.component.css']
})
export class EventAttendanceManagementComponent implements OnInit {

  displayDialog: boolean;
  newAttendance: boolean;

  attendances: Attendance[];

  attendance: Attendance;

  selectedAttendance: Attendance = new PrimeAttendance();

  constructor(
    private eventAttendanceService: EventAttendanceService
  ) { }

  ngOnInit() {
    const tempEventId = 1;

    this.eventAttendanceService.retrieveAll(tempEventId, true).subscribe(attendance => {
      debugger;
      this.attendances = attendance.data;
    });
  }

  showDialogToAdd() {
    this.newAttendance = true;
    this.attendance = new PrimeAttendance();
    this.displayDialog = true;
  }

}

class PrimeAttendance implements Attendance {
  constructor(public id?, public name?) {}
}
