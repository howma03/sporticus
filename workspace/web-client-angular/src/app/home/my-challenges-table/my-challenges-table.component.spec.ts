import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {MatTable, MatHeaderCell, MatCell, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef} from '@angular/material/table';

import {MyChallengesTableComponent, } from './my-challenges-table.component';

describe('AvailableChallengesTableComponent', () => {
  let component: MyChallengesTableComponent;
  let fixture: ComponentFixture<MyChallengesTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyChallengesTableComponent,
        MatTable, MatHeaderCell, MatCell, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef]
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
