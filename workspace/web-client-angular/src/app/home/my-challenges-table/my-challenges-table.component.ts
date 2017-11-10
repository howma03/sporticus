import {Component} from '@angular/core';
import {DataSource} from '@angular/cdk/collections';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';


@Component({
  selector: 'app-my-challenges-table',
  templateUrl: './my-challenges-table.component.html',
  styleUrls: ['./my-challenges-table.component.css']
})
export class MyChallengesTableComponent {
  displayedColumns = ['sport', 'date'];
  dataSource = new MyChallengesDataSource();
}

export interface MyChallenge {
  sport: string;
  date: Date;
}

const data: MyChallenge[] = [
  {sport: 'Squash', date: new Date()},
  {sport: 'Badminton', date: new Date()},
  {sport: 'Badminton', date: new Date()}
];

/**
 * Data source to provide what data should be rendered in the table. The observable provided
 * in connect should emit exactly the data that should be rendered by the table. If the data is
 * altered, the observable should emit that new set of data on the stream. In our case here,
 * we return a stream that contains only one set of data that doesn't change.
 */
export class MyChallengesDataSource extends DataSource<any> {
  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<MyChallenge[]> {
    return Observable.of(data);
  }

  disconnect() {}
}


/**  Copyright 2017 Google Inc. All Rights Reserved.
 Use of this source code is governed by an MIT-style license that
 can be found in the LICENSE file at http://angular.io/license */
