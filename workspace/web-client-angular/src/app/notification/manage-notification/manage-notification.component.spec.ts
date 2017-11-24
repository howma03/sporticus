import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageNotificationComponent } from './manage-notification.component';

describe('ManageNotificationComponent', () => {
  let component: ManageNotificationComponent;
  let fixture: ComponentFixture<ManageNotificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageNotificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
