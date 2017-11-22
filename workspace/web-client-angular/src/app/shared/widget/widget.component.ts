import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-widget',
  templateUrl: './widget.component.html',
  styleUrls: ['./widget.component.css'],
})
export class WidgetComponent implements OnInit {

  constructor() {
  }

  @Input()
  loading: boolean = true;

  ngOnInit() {
  }

}
