import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AddGroupMemberDialogComponent} from './add-group-member-dialog.component';

describe('AddGroupMemberDialogComponent', () => {
  let component: AddGroupMemberDialogComponent;
  let fixture: ComponentFixture<AddGroupMemberDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AddGroupMemberDialogComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddGroupMemberDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
