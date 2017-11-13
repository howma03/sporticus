import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {UsersService} from "../../services/users.service";

@Component({
  selector: 'app-rest-example',
  templateUrl: './rest-example.component.html',
  styleUrls: ['./rest-example.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RestExampleComponent implements OnInit {

  constructor(private usersService: UsersService) {
  }

  ngOnInit() {
  }

  getItem(id) {
    this.usersService.retrieveOne(id);
  }

}
