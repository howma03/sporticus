import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {LadderDialogComponent} from './ladder-dialog.component';

describe('LadderDialogComponent', () => {
  let component: LadderDialogComponent;
  let fixture: ComponentFixture<LadderDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [LadderDialogComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LadderDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
