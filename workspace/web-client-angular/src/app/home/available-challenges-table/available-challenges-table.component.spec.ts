import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {MatTable, MatHeaderCell, MatCell, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef} from '@angular/material/table';

import {AvailableChallengesTableComponent} from './available-challenges-table.component';

describe('AvailableChallengesTableComponent', () => {
  let component: AvailableChallengesTableComponent;
  let fixture: ComponentFixture<AvailableChallengesTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AvailableChallengesTableComponent,
        MatTable, MatHeaderCell, MatCell, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AvailableChallengesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
