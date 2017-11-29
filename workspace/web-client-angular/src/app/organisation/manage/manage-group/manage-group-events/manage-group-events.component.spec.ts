import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ManageGroupEventsComponent} from './manage-group-events.component';

describe('ManageGroupEventsComponent', () => {
  let component: ManageGroupEventsComponent;
  let fixture: ComponentFixture<ManageGroupEventsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ManageGroupEventsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageGroupEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
