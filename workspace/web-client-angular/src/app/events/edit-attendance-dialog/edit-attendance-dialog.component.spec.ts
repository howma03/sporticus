import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAttendanceDialogComponent } from './edit-attendance-dialog.component';

describe('EditAttendanceDialogComponent', () => {
  let component: EditAttendanceDialogComponent;
  let fixture: ComponentFixture<EditAttendanceDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditAttendanceDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditAttendanceDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
