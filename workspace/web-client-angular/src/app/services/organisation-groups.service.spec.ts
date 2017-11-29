import { TestBed, inject } from '@angular/core/testing';

import { OrganisationGroupsService } from './organisation-groups.service';

describe('OrganisationGroupsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [OrganisationGroupsService]
    });
  });

  it('should be created', inject([OrganisationGroupsService], (service: OrganisationGroupsService) => {
    expect(service).toBeTruthy();
  }));
});
