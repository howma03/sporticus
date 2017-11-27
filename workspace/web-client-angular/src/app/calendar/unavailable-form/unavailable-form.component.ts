import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
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

  isNew: boolean = true;


  get showTime() {
    return this.unavailableForm.get('showTime');
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

    let toDateOnly : string = null;
    let toTimeOnly : string = null;

    let fromDateOnly : string = null;
    let fromTimeOnly : string = null;

    // if (this.rung.challenger.dateTime) {
    //   let datetime : Date = new Date(this.rung.challenger.dateTime);
    //   dateOnly = datetime.toISOString().substring(0, 10);
    //   timeOnly = ('0' + datetime.getHours()).slice(-2) + ":" + ('0' + datetime.getMinutes()).slice(-2);
    // }

    this.unavailableForm = this.fb.group({
      showTime: false,
      fromDate: this.fb.group({
        fromEventDate: [fromDateOnly, [Validators.required]], // TODO: We're waiting upon the Material datetime picker,
        fromEventTime: [fromTimeOnly, [Validators.required]], // to enter date and time as a single field,
      }),
      toDate: this.fb.group({
        toEventDate: [toDateOnly, [Validators.required]], // TODO: We're waiting upon the Material datetime picker,
        toEventTime: [toTimeOnly, [Validators.required]], // to enter date and time as a single field,
      }),
    });
  }

  onSave() {
    let dateOnly = this.unavailableForm.get('fromDate').get('fromEventDate').value;
    let timeOnly = this.unavailableForm.get('fromDate').get('fromEventTime').value;

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

  onCancel() {
    this.onDone();
  }

  onDone() {
    this.done.emit();
  }
}
