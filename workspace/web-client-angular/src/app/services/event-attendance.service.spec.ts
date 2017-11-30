import { TestBed, inject } from '@angular/core/testing';

import { EventAttendanceService } from './event-attendance.service';

describe('EventAttendanceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [EventAttendanceService]
    });
  });

  it('should be created', inject([EventAttendanceService], (service: EventAttendanceService) => {
    expect(service).toBeTruthy();
  }));
});
