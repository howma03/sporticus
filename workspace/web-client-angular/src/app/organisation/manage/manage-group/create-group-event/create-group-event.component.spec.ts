import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateGroupEventComponent } from './create-group-event.component';

describe('CreateGroupEventComponent', () => {
  let component: CreateGroupEventComponent;
  let fixture: ComponentFixture<CreateGroupEventComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateGroupEventComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateGroupEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
