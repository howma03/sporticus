import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RestExampleComponent } from './rest-example.component';

describe('RestExampleComponent', () => {
  let component: RestExampleComponent;
  let fixture: ComponentFixture<RestExampleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RestExampleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RestExampleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
