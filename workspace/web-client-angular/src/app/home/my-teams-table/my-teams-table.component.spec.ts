import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {MatTable, MatHeaderCell, MatCell, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef} from '@angular/material/table';

import { MyTeamsTableComponent } from './my-teams-table.component';

describe('AvailableChallengesTableComponent', () => {
  let component: MyTeamsTableComponent;
  let fixture: ComponentFixture<MyTeamsTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyTeamsTableComponent,
        MatTable, MatHeaderCell, MatCell, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyTeamsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
