import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DevelopmentTeamComponent } from './development-team.component';

describe('DevelopmentTeamComponent', () => {
  let component: DevelopmentTeamComponent;
  let fixture: ComponentFixture<DevelopmentTeamComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DevelopmentTeamComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DevelopmentTeamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
