import {Component, OnInit} from '@angular/core';
import {Ladder, LadderService} from "../../services/ladder.service";
import {MatDialog} from "@angular/material";
import {Observable} from "rxjs/Observable";
import {DataSource} from "@angular/cdk/collections";
import {LadderDialogComponent} from "../ladder-dialog/ladder-dialog.component";

@Component({
  selector: 'app-my-ladders',
  templateUrl: './my-ladders.component.html',
  styleUrls: ['./my-ladders.component.css'],
})
export class MyLaddersComponent implements OnInit {

  constructor(private ladderService: LadderService,
              private dialog: MatDialog) {
  }

  dataSource = new MyDataSource(this.ladderService);
  displayedColumns = ['name', 'started'];

  ngOnInit() {
  }

  handleRowClick(ladder) {
    this.dialog.open(LadderDialogComponent, {
      height: '700px',
      width: '900px',
      data: {
        ladder
      }
    });
  }
}

class MyDataSource extends DataSource<Ladder> {
  constructor(private ladderService: LadderService) {
    super();
  }

  connect(): Observable<Ladder[]> {
    return this.ladderService.retrieveAll().map(list => list.data);
  }

  disconnect() {
  }
}
