import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganisationHomeComponent } from './organisation-home.component';

describe('OrganisationHomeComponent', () => {
  let component: OrganisationHomeComponent;
  let fixture: ComponentFixture<OrganisationHomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrganisationHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrganisationHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
