import {Component} from '@angular/core';
import {DataSource} from '@angular/cdk/collections';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';


@Component({
  selector: 'app-available-challenges-table',
  templateUrl: './available-challenges-table.component.html',
  styleUrls: ['./available-challenges-table.component.css']
})
export class AvailableChallengesTableComponent {
  displayedColumns = ['sport', 'date', 'name'];
  dataSource = new AvailableChallengesDataSource();
}

export interface AvailableChallenge {
  name: string;
  sport: string;
  date: Date;
}

const data: AvailableChallenge[] = [
  {sport: 'Badminton', name: 'James Nurse', date: new Date()},
  {sport: 'Badminton', name: 'Thomas Hardwick', date: new Date()},
  {sport: 'Badminton', name: 'Mark Howell', date: new Date()}
];

/**
 * Data source to provide what data should be rendered in the table. The observable provided
 * in connect should emit exactly the data that should be rendered by the table. If the data is
 * altered, the observable should emit that new set of data on the stream. In our case here,
 * we return a stream that contains only one set of data that doesn't change.
 */
export class AvailableChallengesDataSource extends DataSource<any> {
  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<AvailableChallenge[]> {
    return Observable.of(data);
  }

  disconnect() {}
}


/**  Copyright 2017 Google Inc. All Rights Reserved.
 Use of this source code is governed by an MIT-style license that
 can be found in the LICENSE file at http://angular.io/license */
