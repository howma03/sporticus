import {ChangeDetectorRef, Component} from '@angular/core';
import { ColorService, DashboardOptions } from '@ux-aspects/ux-aspects';
import {LogService} from './log.service';
// import 'chance';

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

  constructor(
    private logService : LogService,
    private cd: ChangeDetectorRef)
  {
    this.logService.logMessage("Home Started"); // Just an example of a simple service
  }

  ngAfterViewInit() {
    this.cd.detectChanges();
  }
}
