import { TestBed, inject } from '@angular/core/testing';

import { GroupMemberService } from './group-member.service';

describe('GroupMemberService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GroupMemberService]
    });
  });

  it('should be created', inject([GroupMemberService], (service: GroupMemberService) => {
    expect(service).toBeTruthy();
  }));
});
