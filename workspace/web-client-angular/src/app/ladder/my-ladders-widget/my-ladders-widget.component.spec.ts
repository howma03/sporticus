import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MyLaddersWidgetComponent} from './my-ladders-widget.component';

describe('MyLaddersWidgetComponent', () => {
  let component: MyLaddersWidgetComponent;
  let fixture: ComponentFixture<MyLaddersWidgetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MyLaddersWidgetComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyLaddersWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
