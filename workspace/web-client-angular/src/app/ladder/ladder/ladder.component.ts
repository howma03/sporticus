import {Component, Input, OnInit} from '@angular/core';
import {Ladder, LadderService, LadderUser} from "../../services/ladder.service";
import {DataSource} from "@angular/cdk/collections";
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'app-ladder',
  templateUrl: './ladder.component.html',
  styleUrls: ['./ladder.component.css'],
})
export class LadderComponent implements OnInit {

  constructor(private ladderService: LadderService) {
  }

  @Input()
  set ladder(ladder: Ladder) {
    if (ladder) {
      this.dataSource = new MyDataSource(this.ladderService, ladder)
    }

  }

  dataSource = null;
  displayedColumns = ['name', 'position'];

  ngOnInit() {
  }
}


class MyDataSource extends DataSource<LadderUser> {
  constructor(private ladderService: LadderService, private ladder: Ladder) {
    super();
  }

  connect(): Observable<LadderUser[]> {
    return this.ladderService.getLadderUsers(this.ladder.id).map(list => list.data);
  }

  disconnect() {
  }
}
