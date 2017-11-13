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
      icon: 'hpe-help',
      title: 'About',
      select: () => this.goAbout()
    },
    {
      icon: 'hpe-link',
      title: 'Clubs and Leagues',
      select: () => this.goClubs()
    },
    {
      icon: 'hpe-calculator',
      title: 'Prices',
      select: () => this.goPrices()
    },
    {
      icon: 'hpe-lock',
      title: 'Sign In',
      select: () => this.goSignIn()
    },
    {
      icon: 'hpe-action',
      title: 'Sign Up',
      select: () => this.goSignUp()
    }
  ];

  iconMenus: PageHeaderIconMenu[] = [];

  constructor(private route: ActivatedRoute,
              private router: Router) {
  }

  goAbout() {
    this.router.navigate(['landing/about']);
  }

  goClubs() {
    this.router.navigate(['landing/clubs']);
  }

  goPrices() {
    this.router.navigate(['landing/prices']);
  }

  goSignIn() {
    this.router.navigate(['landing/login']);
  }

  goSignUp() {
    this.router.navigate(['landing/register']);
  }


  goContact() {
    this.router.navigate(['landing/contact']);
  }

  goLegal() {
    this.router.navigate(['landing/legal']);
  }

  goHome() {
    this.router.navigate(['main/home']);
  }

  doLogout() {
    this.router.navigate(['login']);
  }
}
