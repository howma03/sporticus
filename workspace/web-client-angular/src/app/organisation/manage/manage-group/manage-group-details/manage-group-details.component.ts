import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {Group, OrganisationGroupsService} from "../../../../services/organisation-groups.service";
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-manage-group-details',
  templateUrl: './manage-group-details.component.html',
  styleUrls: ['./manage-group-details.component.css']
})
export class ManageGroupDetailsComponent implements OnInit, OnChanges {

  get group(): Group {
    return this._group;
  }

  @Input()
  set group(value: Group) {
    this._group = value;
    this.loadGroup();
  }

  private _group: Group;

  groupForm: FormGroup;

  @Output()
  done = new EventEmitter<Group>();

  constructor(private groupService: OrganisationGroupsService, private fb: FormBuilder) {
    this.createForm();
  }

  get name() {
    return this.groupForm.get('name');
  }
  get description() {
    return this.groupForm.get('description');
  }

  ngOnInit() {
   this.createForm();
  }

  ngOnChanges() {
    this.groupForm.reset({...this._group});
  }

  createForm() {
    this.groupForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]]
    });
  }

  loadGroup() {
    if (this._group != null) {
      this.groupService.retrieveOne(this.group.id, this.group.ownerOrganisationId)
        .subscribe(group => {
          this._group = group;
          this.ngOnChanges();
        });
    }
  }

  onSave() {
    const formModel = this.groupForm.value;
    const toSave: Group = {
      id: this.group.id,
      ownerOrganisationId: this.group.ownerOrganisationId,
      ...formModel
    };

    const sub = this.groupService.updateOne(this.group.id, this.group.ownerOrganisationId, toSave)
      .subscribe(group => {
        this._group = group;
        this.onDone();
      });
  }

  onCancel() {
    this.onDone();
  }

  onDone() {
    this.ngOnChanges();
    this.done.emit(this.group);
  }
}
