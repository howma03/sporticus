import { NO_ERRORS_SCHEMA }          from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {DashboardComponent, DashboardWidgetComponent} from '@ux-aspects/ux-aspects'

import { HomeComponent } from './home.component';
import { LogService } from './log.service';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  let logServiceStub = {
    logMessage: function(message){
      //NO-OP
    }
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HomeComponent],
      schemas:      [ NO_ERRORS_SCHEMA ],
      providers: [{provide: LogService, useValue: logServiceStub}]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
