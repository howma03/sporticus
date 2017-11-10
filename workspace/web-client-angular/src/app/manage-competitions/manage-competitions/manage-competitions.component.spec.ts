import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageCompetitionsComponent } from './manage-competitions.component';

describe('ManageCompetitionsComponent', () => {
  let component: ManageCompetitionsComponent;
  let fixture: ComponentFixture<ManageCompetitionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageCompetitionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageCompetitionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
