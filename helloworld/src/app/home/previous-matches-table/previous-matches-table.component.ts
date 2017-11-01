import {Component} from '@angular/core';
import {DataSource} from '@angular/cdk/collections';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';


@Component({
  selector: 'app-previous-matches-table',
  templateUrl: './previous-matches-table.component.html',
  styleUrls: ['./previous-matches-table.component.css']
})
export class PreviousMatchesTableComponent {
  displayedColumns = ['name', 'date', 'score', 'opponentStore'];
  dataSource = new PreviousMatchesDataSource();
}

export interface PreviousMatch {
  name: string;
  date: Date;
  score: number;
  opponentScore: number;
}

const data: PreviousMatch[] = [
  {name: 'Mark Howell', date: new Date(), score: 1, opponentScore: 3},
  {name: 'Thomas Hardwick', date: new Date(), score: 2, opponentScore: 3},
  {name: 'James Nurse', date: new Date(), score: 2, opponentScore: 3},
  {name: 'Bob Tarling', date: new Date(), score: 2, opponentScore: 3},
];

/**
 * Data source to provide what data should be rendered in the table. The observable provided
 * in connect should emit exactly the data that should be rendered by the table. If the data is
 * altered, the observable should emit that new set of data on the stream. In our case here,
 * we return a stream that contains only one set of data that doesn't change.
 */
export class PreviousMatchesDataSource extends DataSource<any> {
  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<PreviousMatch[]> {
    return Observable.of(data);
  }

  disconnect() {}
}


/**  Copyright 2017 Google Inc. All Rights Reserved.
 Use of this source code is governed by an MIT-style license that
 can be found in the LICENSE file at http://angular.io/license */
