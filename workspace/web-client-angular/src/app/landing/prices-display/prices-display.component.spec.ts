import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PricesDisplayComponent } from './prices-display.component';

describe('PricesDisplayComponent', () => {
  let component: PricesDisplayComponent;
  let fixture: ComponentFixture<PricesDisplayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PricesDisplayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PricesDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
