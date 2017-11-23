import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CalenderMainComponent} from './calender-main.component';

describe('CalenderMainComponent', () => {
  let component: CalenderMainComponent;
  let fixture: ComponentFixture<CalenderMainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CalenderMainComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CalenderMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
