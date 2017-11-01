import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FutureMatchesTableComponent } from './future-matches-table.component';

describe('FutureMatchesTableComponent', () => {
  let component: FutureMatchesTableComponent;
  let fixture: ComponentFixture<FutureMatchesTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FutureMatchesTableComponent ]
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
