import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventAttendanceManagementComponent } from './event-attendance-management.component';

describe('EventAttendanceManagementComponent', () => {
  let component: EventAttendanceManagementComponent;
  let fixture: ComponentFixture<EventAttendanceManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventAttendanceManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventAttendanceManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
