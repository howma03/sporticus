import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CreateGroupScheduleComponent} from './create-group-schedule.component';

describe('CreateGroupScheduleComponent', () => {
  let component: CreateGroupScheduleComponent;
  let fixture: ComponentFixture<CreateGroupScheduleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CreateGroupScheduleComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateGroupScheduleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
