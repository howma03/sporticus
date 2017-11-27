import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {SendInviteDialogComponent} from './send-invite-dialog.component';

describe('SendInviteDialogComponent', () => {
  let component: SendInviteDialogComponent;
  let fixture: ComponentFixture<SendInviteDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SendInviteDialogComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SendInviteDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
