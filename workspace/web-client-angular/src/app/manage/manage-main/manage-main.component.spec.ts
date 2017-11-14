import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageMainComponent } from './manage-main.component';

describe('ManageMainComponent', () => {
  let component: ManageMainComponent;
  let fixture: ComponentFixture<ManageMainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageMainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
