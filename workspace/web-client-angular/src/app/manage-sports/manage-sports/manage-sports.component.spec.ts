import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageSportsComponent } from './manage-sports.component';

describe('ManageSportsComponent', () => {
  let component: ManageSportsComponent;
  let fixture: ComponentFixture<ManageSportsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageSportsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageSportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
