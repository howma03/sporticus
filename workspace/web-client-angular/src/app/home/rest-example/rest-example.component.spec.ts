import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RestExampleComponent } from './rest-example.component';
import { RestService } from './rest.service';

describe('RestExampleComponent', () => {
  let component: RestExampleComponent;
  let fixture: ComponentFixture<RestExampleComponent>;
  let restServiceStub = {

  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RestExampleComponent ],
      providers: [{provide: RestService, useValue: restServiceStub}]
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
