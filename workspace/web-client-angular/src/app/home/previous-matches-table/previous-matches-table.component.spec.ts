import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviousMatchesTableComponent } from './previous-matches-table.component';

describe('FutureMatchesTableComponent', () => {
  let component: PreviousMatchesTableComponent;
  let fixture: ComponentFixture<PreviousMatchesTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviousMatchesTableComponent ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviousMatchesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
