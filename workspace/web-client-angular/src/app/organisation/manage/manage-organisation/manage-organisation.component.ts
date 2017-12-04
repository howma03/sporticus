import {Component, EventEmitter, Input, OnChanges, OnInit, Output} from '@angular/core';
import {Organisation, OrganisationService} from '../../../services/organisation.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-manage-organisation',
  templateUrl: './manage-organisation.component.html',
  styleUrls: ['./manage-organisation.component.css'],
})
export class ManageOrganisationComponent implements OnInit, OnChanges {

  constructor(private organisationService: OrganisationService, private fb: FormBuilder) {
    this.createForm();
  }

  @Input()
  set organisationId(value: number) {
    this._organisationId = value;
    this.loadOrganisation();
  }

  @Output()
  done = new EventEmitter<Organisation>();

  private _organisationId: number;

  organisation: Organisation;
  organisationForm: FormGroup;

  get name() {
    return this.organisationForm.get('name');
  }

  get urlFragment() {
    return this.organisationForm.get('urlFragment');
  }

  get address() {
    return this.organisationForm.get('address');
  }

  ngOnInit() {
    this.organisationForm = this.fb.group({
      name: ['', [Validators.required]],
      urlFragment: ['', [Validators.required]],
      address: ['']
    });
  }

  ngOnChanges() {
    this.organisationForm.reset({...this.organisation});
  }

  createForm() {
    this.organisationForm = this.fb.group({
      name: ['', [Validators.required]],
      urlFragment: ['', [Validators.required]],
      address: ['']
    });
  }

  loadOrganisation() {
    if (this._organisationId != null) {
      this.organisationService.retrieveOne(this._organisationId)
        .subscribe(org => {
          this.organisation = org;
          this.ngOnChanges();
        });
    }

  }

  onSave() {
    const formModel = this.organisationForm.value;
    const toSave: Organisation = {
      id: this.organisation.id,
      ...formModel
    };


    this.organisationService.updateOne(this._organisationId, toSave)
      .subscribe(organisation => {
        this.organisation = organisation;
        this.onDone();
      });
  }

  onCancel() {
    this.onDone();
  }

  onDone() {
    this.ngOnChanges();
    this.done.emit(this.organisation);
  }

}
