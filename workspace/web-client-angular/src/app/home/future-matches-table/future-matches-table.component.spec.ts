import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {MatTable, MatHeaderCell, MatCell, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef} from '@angular/material/table';

import { FutureMatchesTableComponent } from './future-matches-table.component';

describe('FutureMatchesTableComponent', () => {
  let component: FutureMatchesTableComponent;
  let fixture: ComponentFixture<FutureMatchesTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FutureMatchesTableComponent,
        MatTable, MatHeaderCell, MatCell, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FutureMatchesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
