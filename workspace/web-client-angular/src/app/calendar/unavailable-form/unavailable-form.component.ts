import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from '../../auth/auth.service';
import {Event, EventService} from '../../services/event.service';

@Component({
  selector: 'app-unavailable-form',
  templateUrl: './unavailable-form.component.html',
  styleUrls: ['./unavailable-form.component.css'],
})
export class UnavailableFormComponent implements OnInit {

  constructor(private fb: FormBuilder,
              private eventService: EventService,
              private authService: AuthService) {}

  @Input()
  date: Date;

  @Output()
  done = new EventEmitter<Event>();

  unavailableForm: FormGroup;

  isNew: boolean = false;

  get fromEventDate() {
    return this.unavailableForm.get('fromDate').get('fromEventDate')
  }

  get fromEventTime() {
    return this.unavailableForm.get('fromDate').get('fromEventTime')
  }

  get toEventDate() {
    return this.unavailableForm.get('toDate').get('toEventDate')
  }

  get toEventTime() {
    return this.unavailableForm.get('toDate').get('toEventTime')
  }

  ngOnInit() {

    // if (!this.rung.challenger) {
    //   // this is a new challenge from a challenger
    //   this.isNew = true;
    //
    //   let newDate =  new Date();
    //   let newEndDate =  new Date();


    //   this.rung.challenger = {
    //     id: null,
    //     name: 'New Challenge',
    //     created: newDate,
    //     status: 'PROPOSED',
    //     dateTime: newDate,
    //     dateTimeEnd: new Date(newDate.getTime() + (60*60*1000)),
    //     challengerId: this.authService.getCurrentUser().id,
    //     challengedId: this.rung.userId,
    //     scoreChallenger: null,
    //     scoreChallenged: null,
    //   }
    // }

    let dateOnly : string = null;
    let timeOnly : string = null;
    // if (this.rung.challenger.dateTime) {
    //   let datetime : Date = new Date(this.rung.challenger.dateTime);
    //   dateOnly = datetime.toISOString().substring(0, 10);
    //   timeOnly = ('0' + datetime.getHours()).slice(-2) + ":" + ('0' + datetime.getMinutes()).slice(-2);
    // }

    this.unavailableForm = this.fb.group({
      fromDate: this.fb.group({
        eventDate: [dateOnly, [Validators.required]], // TODO: We're waiting upon the Material datetime picker,
        eventTime: [timeOnly, [Validators.required]], // to enter date and time as a single field,
      }),
      toDate: this.fb.group({
        eventDate: [dateOnly, [Validators.required]], // TODO: We're waiting upon the Material datetime picker,
        eventTime: [timeOnly, [Validators.required]], // to enter date and time as a single field,
      }),
    });
  }

  onSave() {
    let dateOnly = this.unavailableForm.get('date').get('eventDate').value;
    let timeOnly = this.unavailableForm.get('date').get('eventTime').value;

    let dateTime = new Date(dateOnly);
    let splitTime = timeOnly.split(":");
    dateTime.setHours(+splitTime[0]);
    dateTime.setMinutes(+splitTime[1]);

    dateTime.setHours(+splitTime[0]);
    let dateTimeEnd = new Date(dateTime.getTime() + (60*60*1000));

    const toSave: Event = {
      id: null,
      name: 'holiday',
      created: new Date(),
      dateTime: dateTime,
      dateTimeEnd: dateTimeEnd,
      type: 'UNAVAILABLE',
      status: 'ACCEPTED'
    };

    if (this.isNew) {
      this.eventService.createOne(toSave)
        .subscribe(challenge => {
//          this.rung.challenger = challenge;
          this.onDone();
        })
    } else {
      this.eventService.updateOne(toSave.id, toSave)
        .subscribe(challenge => {
//          this.rung.challenger = challenge;
          this.onDone();
        })
    }
  }

  onCancel() {
    this.onDone();
  }

  onDone() {
    this.done.emit();
  }
}
