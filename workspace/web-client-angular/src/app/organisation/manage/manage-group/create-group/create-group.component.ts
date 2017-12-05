import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {Group, OrganisationGroupsService} from '../../../../services/organisation-groups.service';
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
  public done = new EventEmitter<Group>();

  get name() {
    return this.groupForm.get('name');
  }

  get description() {
    return this.groupForm.get('description');
  }


  groupForm: FormGroup;

  constructor(private organisationGroupsService: OrganisationGroupsService, private fb: FormBuilder) {
    this.groupForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]]
    });
  }

  ngOnInit() {
  }

  ngOnChanges() {
    this.groupForm.reset({
      name: '',
      description: ''
    });
  }

  onCancel() {
    this.onDone();
  }

  onSave() {
    this.organisationGroupsService.createOne(this.organisationId, {
      ownerOrganisationId: this.organisationId,
      isEnabled: true,
      type: 'Members',

      ...this.groupForm.value
    })
      .subscribe(group => this.onDone(group));
  }

  onDone(group?) {
    this.ngOnChanges();
    this.done.emit(group);
  }


}
