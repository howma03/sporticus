import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {SendInviteComponent} from './send-invite.component';

describe('SendInviteComponent', () => {
  let component: SendInviteComponent;
  let fixture: ComponentFixture<SendInviteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SendInviteComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SendInviteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
