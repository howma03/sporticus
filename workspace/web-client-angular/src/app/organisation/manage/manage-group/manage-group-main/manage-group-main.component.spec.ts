import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ManageGroupMainComponent} from './manage-group-main.component';

describe('ManageGroupMainComponent', () => {
  let component: ManageGroupMainComponent;
  let fixture: ComponentFixture<ManageGroupMainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ManageGroupMainComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageGroupMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
