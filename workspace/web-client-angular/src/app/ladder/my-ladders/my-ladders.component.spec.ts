import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MyLaddersComponent} from './my-ladders.component';

describe('MyLaddersComponent', () => {
  let component: MyLaddersComponent;
  let fixture: ComponentFixture<MyLaddersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MyLaddersComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyLaddersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
