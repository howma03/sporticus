import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-manage-competitions',
  templateUrl: './manage-competitions.component.html',
  styleUrls: ['./manage-competitions.component.css'],
})
export class ManageCompetitionsComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  @Input()
  organisationId: number;

}
