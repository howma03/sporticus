import {Component} from '@angular/core';
import {DataSource} from '@angular/cdk/collections';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';


@Component({
  selector: 'app-my-teams-table',
  templateUrl: './my-teams-table.component.html',
  styleUrls: ['./my-teams-table.component.css']
})
export class MyTeamsTableComponent {
  displayedColumns = ['name', 'sport'];
  dataSource = new MyTeamsDataSource();
}

export interface MyTeam {
  name: String;
  sport: string;
}

const data: MyTeam[] = [
  {sport: 'Badminton Doubles', name: 'Tarling & Kelly'},
  {sport: 'Football', name: 'Tilehurst Wanderers'}
];

/**
 * Data source to provide what data should be rendered in the table. The observable provided
 * in connect should emit exactly the data that should be rendered by the table. If the data is
 * altered, the observable should emit that new set of data on the stream. In our case here,
 * we return a stream that contains only one set of data that doesn't change.
 */
export class MyTeamsDataSource extends DataSource<any> {
  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<MyTeam[]> {
    return Observable.of(data);
  }

  disconnect() {}
}


/**  Copyright 2017 Google Inc. All Rights Reserved.
 Use of this source code is governed by an MIT-style license that
 can be found in the LICENSE file at http://angular.io/license */
