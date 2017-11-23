import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-manage-main',
  templateUrl: './manage-main.component.html',
  styleUrls: ['./manage-main.component.css'],
})
export class ManageMainComponent implements OnInit {

  constructor(private route: ActivatedRoute) {
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
