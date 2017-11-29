import {inject, TestBed} from '@angular/core/testing';

import {GroupEventService} from './group-event.service';

describe('GroupEventService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GroupEventService]
    });
  });

  it('should be created', inject([GroupEventService], (service: GroupEventService) => {
    expect(service).toBeTruthy();
  }));
});
