import {inject, TestBed} from '@angular/core/testing';

import {OrganisationUsersService} from './organisation-users.service';

describe('OrganisationUsersService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [OrganisationUsersService]
    });
  });

  it('should be created', inject([OrganisationUsersService], (service: OrganisationUsersService) => {
    expect(service).toBeTruthy();
  }));
});
