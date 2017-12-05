import {Component, Input, OnInit} from '@angular/core';
import {Attendance, EventAttendanceService} from '../../services/event-attendance.service';
import {Group} from '../../services/organisation-groups.service';
import {Event} from '../../services/event.service';
import {EditAttendanceDialogComponent} from "../edit-attendance-dialog/edit-attendance-dialog.component";
import {MatDialog} from "@angular/material";

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

  attendances: Attendance[];

  attendance: Attendance;

  constructor(
    private eventAttendanceService: EventAttendanceService,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
   this.updateAttendances();
  }

  addAttendance(attendance) {
    this.openAttendanceModal(attendance);
  }

  public openAttendanceModal(attendance): void {

    let dialogRef = this.dialog.open(EditAttendanceDialogComponent, {
      data: {
        attendance: attendance,
        event: this.event
      },
      height: '500px',
      width: '1200px',
    });

    dialogRef.afterClosed().subscribe((updateRequired) => {
      if (updateRequired) {
        this.updateAttendances();
      }
    });
  }

  editAttendance(attendance) {
  }

  removeAttendance(attendance) {
  }


  private updateAttendances() {
    this.eventAttendanceService.retrieveAll(this.event.id, true).subscribe(attendance => {
      this.attendances = attendance.data;
      this.attendances.forEach(attendance => {
        if(attendance.id === undefined) {
          attendance.attended = false;
        } else {
          attendance.attended = true;
        }
      });
    });
  }
}
