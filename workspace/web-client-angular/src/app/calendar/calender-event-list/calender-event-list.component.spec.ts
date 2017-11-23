import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CalenderEventListComponent} from './calender-event-list.component';

describe('CalenderEventListComponent', () => {
  let component: CalenderEventListComponent;
  let fixture: ComponentFixture<CalenderEventListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CalenderEventListComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CalenderEventListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
