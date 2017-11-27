import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UnavailableFormComponent } from './unavailable-form.component';

describe('UnavailableFormComponent', () => {
  let component: UnavailableFormComponent;
  let fixture: ComponentFixture<UnavailableFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UnavailableFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnavailableFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
