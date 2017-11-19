import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChallengeFormComponent } from './challenge-form.component';

describe('ChallengeFormComponent', () => {
  let component: ChallengeFormComponent;
  let fixture: ComponentFixture<ChallengeFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChallengeFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChallengeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
