import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UnavailableDialogComponent } from './unavailable-dialog.component';

describe('UnavailableDialogComponent', () => {
  let component: UnavailableDialogComponent;
  let fixture: ComponentFixture<UnavailableDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UnavailableDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnavailableDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
