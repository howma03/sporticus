import {ChangeDetectorRef, Component} from '@angular/core';
import {DashboardOptions} from '@ux-aspects/ux-aspects';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  options: DashboardOptions = {
    columns: 4,
    padding: 10,
    rowHeight: 220,
    emptyRow: false,
    minWidth: 187
  };

  constructor(private cd: ChangeDetectorRef) {
  }

  ngAfterViewInit() {
    this.cd.detectChanges();
  }
}
