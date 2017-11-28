import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {Event, EventService} from '../../services/event.service';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-unavailable-form',
  templateUrl: './unavailable-form.component.html',
  styleUrls: ['./unavailable-form.component.css'],
})
export class UnavailableFormComponent implements OnInit {

  constructor(private fb: FormBuilder,
              private eventService: EventService,
              private datePipe : DatePipe) {}

  @Input()
  date: Date;

  @Output()
  done = new EventEmitter<Event>();

  unavailableForm: FormGroup;

  isNew: boolean = true;

  get entireDays() {
    return this.unavailableForm.get('entireDays');
  }

  get fromEventDate() {
   return this.unavailableForm.get('fromDate').get('fromEventDate');
  }

  get fromEventTime() {
   return this.unavailableForm.get('fromDate').get('fromEventTime');
  }

  get toEventDate() {
    return this.unavailableForm.get('toDate').get('toEventDate');
  }

  get toEventTime() {
   return this.unavailableForm.get('toDate').get('toEventTime');
  }

  ngOnInit() {

    let toDateOnly : string = null;
    let toTimeOnly : string = null;

    let fromDateOnly : string = null;
    let fromTimeOnly : string = null;

    this.unavailableForm = this.fb.group({
      entireDays: false,
      fromDate: this.fb.group({
        fromEventDate: [fromDateOnly, [Validators.required]], // TODO: We're waiting upon the Material datetime picker,
        fromEventTime: [fromTimeOnly, [Validators.required]], // to enter date and time as a single field,
      }),
      toDate: this.fb.group({
        toEventDate: [toDateOnly, [Validators.required]], // TODO: We're waiting upon the Material datetime picker,
        toEventTime: [toTimeOnly, [Validators.required]], // to enter date and time as a single field,
      }),
    });
    this.unavailableForm.get('entireDays').valueChanges.subscribe(value => this.requireTime(value));
  }

  /**
   * Make the times required only if we are not looking for whole days
   * @param {boolean} entireDays
   */
  requireTime(entireDays : boolean) {

    let validator : ValidatorFn = entireDays ? null : Validators.required;
    this.unavailableForm.get('fromDate').get('fromEventTime').setValidators(validator);
    this.unavailableForm.get('toDate').get('toEventTime').setValidators(validator);
    this.unavailableForm.get('fromDate').get('fromEventTime').updateValueAndValidity();
    this.unavailableForm.get('toDate').get('toEventTime').updateValueAndValidity();
  }

  onSave() {
    let fromDateOnly = this.unavailableForm.get('fromDate').get('fromEventDate').value;
    let toDateOnly = this.unavailableForm.get('toDate').get('toEventDate').value;

    let fromDateTime = new Date(fromDateOnly);

    if (!this.unavailableForm.get('entireDays')) {
      let fromTimeOnly = this.unavailableForm.get('fromDate').get('fromEventTime').value;
      let splitTime = fromTimeOnly.split(":");
      fromDateTime.setHours(+splitTime[0]);
      fromDateTime.setMinutes(+splitTime[1]);
      fromDateTime.setHours(+splitTime[0]);
    }

    let toDateTime = new Date(toDateOnly);

    if (!this.unavailableForm.get('entireDays')) {
      let toTimeOnly = this.unavailableForm.get('toDate').get('toEventTime').value;
      let splitTime = toTimeOnly.split(":");
      toDateTime.setHours(+splitTime[0]);
      toDateTime.setMinutes(+splitTime[1]);
      toDateTime.setHours(+splitTime[0]);
    }

    const toSave: Event = {
      id: null,
      name: 'holiday',
      created: new Date(),
      dateTime: fromDateTime,
      dateTimeString: this.formatDate(fromDateTime),
      dateTimeEnd: toDateTime,
      dateTimeEndString: this.formatDate(toDateTime),
      type: 'BUSY',
      status: 'ACCEPTED'
    };

    if (this.isNew) {
      this.eventService.createOne(toSave)
        .subscribe(challenge => {
          this.onDone();
        })
    } else {
      this.eventService.updateOne(toSave.id, toSave)
        .subscribe(challenge => {
          this.onDone();
        })
    }
  }

  private formatDate(date : Date) {
    return this.unavailableForm.get('entireDays').value
      ? this.datePipe.transform(date, 'yyyy-MM-dd')
      : date.toISOString();
  }

  onCancel() {
    this.onDone();
  }

  onDone() {
    this.done.emit();
  }
}
