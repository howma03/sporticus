import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ManageOrganisationComponent} from './manage-organisation.component';

describe('ManageOrganisationComponent', () => {
  let component: ManageOrganisationComponent;
  let fixture: ComponentFixture<ManageOrganisationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ManageOrganisationComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageOrganisationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
