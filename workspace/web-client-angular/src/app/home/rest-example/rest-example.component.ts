import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {RestService} from './rest.service';

@Component({
  selector: 'app-rest-example',
  templateUrl: './rest-example.component.html',
  styleUrls: ['./rest-example.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RestExampleComponent implements OnInit {

  constructor(private restService: RestService) { }

  ngOnInit() {
  }

  getItem(id) {
    this.restService.getItem(id);
  }

}
