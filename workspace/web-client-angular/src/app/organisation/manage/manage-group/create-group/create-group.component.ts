import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {OrganisationGroupsService} from '../../../../services/organisation-groups.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.component.html',
  styleUrls: ['./create-group.component.css']
})
export class CreateGroupComponent implements OnInit, OnChanges {

  @Input()
  public organisationId: number;

  @Output()
  public done = new EventEmitter<any>();

  get name() {
    return this.groupForm.get('name');
  }

  groupForm: FormGroup;

  constructor(private organisationGroupsService: OrganisationGroupsService, private fb: FormBuilder) {
    this.groupForm = this.fb.group({
      name: ['', [Validators.required]]
    });
  }

  ngOnInit() {
  }

  ngOnChanges() {
    this.groupForm.reset({
      name: ''
    });
  }

  onCancel() {
    this.onDone();
  }

  onSave() {
    this.organisationGroupsService.createOne(this.organisationId, this.groupForm.value)
      .subscribe(group => this.onDone());
  }

  onDone() {
    this.ngOnChanges();
    this.done.emit();
  }


}
