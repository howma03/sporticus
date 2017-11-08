import { Component } from '@angular/core';
import {Breadcrumb, PageHeaderIconMenu, PageHeaderNavigationItem} from '@ux-aspects/ux-aspects';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Sporticus';

  condensed: boolean = false;

  crumbs: Breadcrumb[] = [{
    title: 'Archive',
    routerLink: 'home',
  }];

  items: PageHeaderNavigationItem[] = [
    {
      icon: 'hpe-home',
      title: 'Home',
      select: () => this.doHome()
    },
    {
      icon: 'hpe-analytics',
      title: 'Analytics',
      children: [
        {
          title: 'Bar Charts'
        },
        {
          title: 'Pie Charts',
          children: [
            {
              title: 'Daily View'
            },
            {
              title: 'Weekly View'
            },
            {
              title: 'Monthly View'
            }
          ]
        }
      ]
    }
  ];

  iconMenus: PageHeaderIconMenu[] = [
    {
      icon: 'hpe-notification',
      badge: 3,
      dropdown: [
        {
          icon: 'hpe-chat',
          title: 'You have 16 messages',
          subtitle: '4 minutes ago',
          divider: true
        },
        {
          icon: 'hpe-social-twitter',
          title: '3 New Followers',
          subtitle: '12 minutes ago',
          divider: true
        },
        {
          icon: 'hpe-cloud',
          title: 'Server Rebooted',
          subtitle: '22 minutes ago'
        }
      ]
    },
    {
      icon: 'hpe-actions',
      dropdown: [
        {
          header: true,
          title: 'John Doe',
          divider: true
        },
        {
          icon: 'hpe-user-settings',
          title: 'Settings'
        },
        {
          icon: 'hpe-logout',
          title: 'Log Out',
          select: () => this.doLogout()
        },
        {
          title: 'Show Tips'
        }
      ]
    }
  ];




  sessionId = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  doLogin(userName, password) {
    this.sessionId = true;
  }

  doHome() {
    debugger;
    this.router.navigate(['/home']);
  }

  doLogout() {
    this.sessionId = false;
  }

}
