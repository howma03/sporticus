import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ManageMembershipsComponent} from './manage-memberships.component';

describe('ManageMembershipsComponent', () => {
  let component: ManageMembershipsComponent;
  let fixture: ComponentFixture<ManageMembershipsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ManageMembershipsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageMembershipsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
