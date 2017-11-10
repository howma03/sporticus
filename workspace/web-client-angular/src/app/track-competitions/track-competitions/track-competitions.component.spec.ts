import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackCompetitionsComponent } from './track-competitions.component';

describe('TrackCompetitionsComponent', () => {
  let component: TrackCompetitionsComponent;
  let fixture: ComponentFixture<TrackCompetitionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrackCompetitionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrackCompetitionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
