import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Attendance, EventAttendanceService} from "../../services/event-attendance.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Event} from "../../services/event.service";
import {ErrorHandlingService} from "../../services/error-handling.service";


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


  @Output()
  public done = new EventEmitter<any>();

  attendanceForm: FormGroup;

  get amount() {
    return this.attendanceForm.get('amount');
  }

  constructor(
    private eventAttendanceService: EventAttendanceService,
    private fb: FormBuilder,
    private errorHandlingService: ErrorHandlingService
  ) {
    this.attendanceForm = this.fb.group({
      amount: ['', [Validators.required]]
    });
  }

  ngOnInit() {
  }

  ngOnChanges() {
    this.attendanceForm.reset({
      amount: ''
    });
  }

  onSave() {
    debugger;
      this.eventAttendanceService.createOne(this.event.id, this.attendance).subscribe(success => {
        if (success) {
          this.onDone();
        }
      }, err => {
        this.errorHandlingService.handleError(err);
      });
  }

  onCancel() {
    this.onDone();
  }

  onDone() {
    this.ngOnChanges();
    this.done.emit();
  }

}
