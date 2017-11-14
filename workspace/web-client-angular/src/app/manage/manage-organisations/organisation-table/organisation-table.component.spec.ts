import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganisationTableComponent } from './organisation-table.component';

describe('OrganisationTableComponent', () => {
  let component: OrganisationTableComponent;
  let fixture: ComponentFixture<OrganisationTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrganisationTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrganisationTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
