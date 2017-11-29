import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ManageGroupDetailsComponent} from './manage-group-details.component';

describe('ManageGroupDetailsComponent', () => {
  let component: ManageGroupDetailsComponent;
  let fixture: ComponentFixture<ManageGroupDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ManageGroupDetailsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageGroupDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
