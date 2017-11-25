import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Initial2Component } from './initial-2.component';

describe('Initial2Component', () => {
  let component: Initial2Component;
  let fixture: ComponentFixture<Initial2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Initial2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Initial2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
