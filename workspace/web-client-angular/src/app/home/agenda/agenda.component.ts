import {Component, OnInit} from '@angular/core';
import {AgendaService, CalenderEvent} from "../../services/agenda.service";

@Component({
  selector: 'app-agenda',
  templateUrl: './agenda.component.html',
  styleUrls: ['./agenda.component.css'],
})
export class AgendaComponent implements OnInit {

  events: CalenderEvent[] = [];

  loading: boolean = true;

  width: number = 100;
  layout: string = 'card';
  layouts = {
    grid: {
      group: 'grid'
    },
    card: {
      group: 'card',
      minWidth: 187 * 4
    },
    cardstack: {
      group: 'card',
      //maxWidth:  187*4
    }
  };

  constructor(private agendaService: AgendaService) {
  }

  ngOnInit() {
    this.agendaService.getAgenda()
      .map(list => list.data)
      .subscribe((events: CalenderEvent[]) => {
        this.loading = false;
        this.events = events.sort((eventa, eventb) => {
          return new Date(eventb.dateTime).getTime() - new Date(eventa.dateTime).getTime();
        });
      });
  }

  onLayoutChange(value) {
    this.layout = value
  }
}
