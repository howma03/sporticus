import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import {MyChallengesTableComponent, } from './my-challenges-table.component';

describe('AvailableChallengesTableComponent', () => {
  let component: MyChallengesTableComponent;
  let fixture: ComponentFixture<MyChallengesTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyChallengesTableComponent ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyChallengesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
