import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LegalDisclosurePolicyComponent } from './legal-disclosure-policy.component';

describe('LegalDisclosurePolicyComponent', () => {
  let component: LegalDisclosurePolicyComponent;
  let fixture: ComponentFixture<LegalDisclosurePolicyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LegalDisclosurePolicyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LegalDisclosurePolicyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
