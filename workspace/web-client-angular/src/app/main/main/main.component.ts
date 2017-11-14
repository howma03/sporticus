import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Breadcrumb, PageHeaderIconMenu, PageHeaderNavigationItem} from "@ux-aspects/ux-aspects";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  ngOnInit() {
  }

  title = 'Sporticus';

  condensed: boolean = true;

  crumbs: Breadcrumb[] = [{
    title: 'Archive',
    routerLink: 'home',
  }];

  items: PageHeaderNavigationItem[] = [
    {
      icon: 'hpe-home',
      title: 'Home',
      select: () => this.goHome()
    },
    {
      title: 'Track Competitions',
      select: () => this.router.navigate(['main/track-competitions'])
    },
    {
      icon: 'hpe-analytics',
      title: 'Manage',
      children: [
        {
          title: 'Sports',
          select: () => this.router.navigate(['main/manage-sports'])
        },
        {
          title: 'Teams',
          select: () => this.router.navigate(['main/manage-teams'])
        },
        {
          title: 'Competitions',
          select: () => this.router.navigate(['main/manage-competitions'])
        }
      ]
    },
    {
      icon: 'hpe-support',
      title: 'Administration',
      children: [
        {
          title: 'Users',
          select: () => this.router.navigate(['main/manage'])
        },
        {
          title: 'Organisations',
          select: () => this.router.navigate(['main/manage'])
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

  constructor(private route: ActivatedRoute,
              private router: Router) {
  }

  goHome() {
    this.router.navigate(['main/home']);
  }

  doLogout() {
    this.router.navigate(['landing/login']);
  }


}
