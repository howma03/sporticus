import {Component, Input, OnInit} from '@angular/core';
import {Attendance, EventAttendanceService} from '../../services/event-attendance.service';
import {Group} from '../../services/organisation-groups.service';
import {Event} from '../../services/event.service';

@Component({
  selector: 'app-event-attendance-management',
  templateUrl: './event-attendance-management.component.html',
  styleUrls: ['./event-attendance-management.component.css']
})
export class EventAttendanceManagementComponent implements OnInit {

  @Input()
  group: Group;

  @Input()
  event: Event;

  displayDialog: boolean;
  newAttendance: boolean;

  attendances: Attendance[];

  attendance: Attendance;

  constructor(
    private eventAttendanceService: EventAttendanceService
  ) { }

  ngOnInit() {
    this.eventAttendanceService.retrieveAll(this.event.id, true).subscribe(attendance => {
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
