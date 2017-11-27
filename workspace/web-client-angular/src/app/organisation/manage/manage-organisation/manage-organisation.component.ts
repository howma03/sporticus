import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-manage-organisation',
  templateUrl: './manage-organisation.component.html',
  styleUrls: ['./manage-organisation.component.css'],
})
export class ManageOrganisationComponent implements OnInit {

  constructor() {
  }

  @Input()
  organisationId: number;

  ngOnInit() {
  }

}
