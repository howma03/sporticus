import {Component} from '@angular/core';
import {DataSource} from '@angular/cdk/collections';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';


@Component({
  selector: 'app-future-matches-table',
  templateUrl: './future-matches-table.component.html',
  styleUrls: ['./future-matches-table.component.css']
})
export class FutureMatchesTableComponent {
  displayedColumns = ['sport', 'date', 'name'];
  dataSource = new FutureMatchesDataSource();
}

export interface FutureMatch {
  date: Date;
  sport: string;
  name: string;
}

const data: FutureMatch[] = [
  {sport: 'Badminton Doubles', name: 'Nurse & Hardwick', date: new Date()},
  {sport: 'Squash', name: 'Thomas Hardwick', date: new Date()},
  {sport: 'Badminton', name: 'Mark Howell', date: new Date()}
];

/**
 * Data source to provide what data should be rendered in the table. The observable provided
 * in connect should emit exactly the data that should be rendered by the table. If the data is
 * altered, the observable should emit that new set of data on the stream. In our case here,
 * we return a stream that contains only one set of data that doesn't change.
 */
export class FutureMatchesDataSource extends DataSource<any> {
  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<FutureMatch[]> {
    return Observable.of(data);
  }

  disconnect() {}
}
