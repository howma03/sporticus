import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {LoginOverlayComponent} from './login-overlay.component';

describe('LoginOverlayComponent', () => {
  let component: LoginOverlayComponent;
  let fixture: ComponentFixture<LoginOverlayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [LoginOverlayComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginOverlayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
