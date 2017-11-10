import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Breadcrumb, PageHeaderIconMenu, PageHeaderNavigationItem} from "@ux-aspects/ux-aspects";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  ngOnInit() {
  }

  title = 'Sporticus';

  condensed: boolean = true;

  crumbs: Breadcrumb[] = [{
    title: 'Archive',
    routerLink: 'landing',
  }];

  items: PageHeaderNavigationItem[] = [
    {
      icon: 'hpe-home',
      title: 'Home',
      select: () => this.goHome()
    },
    {
      title: 'About',
      select: () => this.goAbout()
    },
    {
      title: 'Contact',
      select: () => this.goContact()
    },
    {
      icon: 'hpe-analytics',
      title: 'Prices',
      children: [
        {
          title: 'Sports',
          select: () => this.goPrices()
        },
        {
          title: 'Teams',
          select: () => this.goPrices()
        },
        {
          title: 'Competitions',
          select: () => this.goPrices()
        }
      ]
    }
  ];

  iconMenus: PageHeaderIconMenu[] = [
    {
      icon: 'hpe-notification',
      badge: 1,
      dropdown: [
        {
          icon: 'hpe-chat',
          title: 'Welcome to Sporticus',
          subtitle: '4 minutes ago',
          divider: true
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

  goAbout() {
    this.router.navigate(['landing/about']);
  }

  goContact() {
    this.router.navigate(['landing/contact']);
  }

  goLegal() {
    this.router.navigate(['landing/legal']);
  }

  goPrices() {
    this.router.navigate(['landing/prices']);
  }




  goHome() {
    this.router.navigate(['main/home']);
  }

  doLogout() {
    this.router.navigate(['login']);
  }
}
