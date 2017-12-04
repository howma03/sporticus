import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventAttendanceDialogComponent } from './event-attendance-dialog.component';

describe('EventAttendanceDialogComponent', () => {
  let component: EventAttendanceDialogComponent;
  let fixture: ComponentFixture<EventAttendanceDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventAttendanceDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventAttendanceDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
