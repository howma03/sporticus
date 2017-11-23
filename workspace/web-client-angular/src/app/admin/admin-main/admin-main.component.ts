import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-admin-main',
  templateUrl: './admin-main.component.html',
  styleUrls: ['./admin-main.component.css']
})
export class AdminMainComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.route.data.subscribe(data => {
      this.initData(data['selectedIndex']);
    });
  }

  selectedIndex = 0;

  initData(selectedIndex) {
    this.selectedIndex = selectedIndex;
  }

}
