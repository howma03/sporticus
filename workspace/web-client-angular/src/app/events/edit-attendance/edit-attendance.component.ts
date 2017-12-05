import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Attendance} from "../../services/event-attendance.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

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

  constructor(private fb: FormBuilder) {
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

  }

  onCancel() {
    this.onDone();
  }

  onDone() {
    this.ngOnChanges();
    this.done.emit();
  }

}
