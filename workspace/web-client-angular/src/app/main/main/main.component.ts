import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Breadcrumb, PageHeaderIconMenu, PageHeaderNavigationItem} from "@ux-aspects/ux-aspects";
import {AuthService} from "../../login/auth.service";
import {ProfileDialogComponent} from "../../profile/profile-dialog/profile-dialog.component";
import {MatDialog} from "@angular/material";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private router: Router,
              private authService: AuthService,
              private dialog: MatDialog) {
  }

  ngOnInit() {

    if(this.authService.currentUser.admin){
      this.items.push({
        icon: 'hpe-support',
        title: 'Administration',
        children: [
          {
            title: 'Users',
            select: () => this.router.navigate(['main/manage-users'])
          },
          {
            title: 'Organisations',
            select: () => this.router.navigate(['main/manage-organisations'])
          }
        ]
      });
    }
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
          title: this.getUserName(),
          divider: true
        },
        {
          icon: 'hpe-user-settings',
          title: 'Edit Profile',
          select: () => this.openProfile()
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

  getUserName() : string {
    return this.authService.currentUser.email;
  }

  goHome() {
    this.router.navigate(['main/home']);
  }

  doLogout() {
    this.router.navigate(['landing/login']);
  }

  openProfile() {
    let dialogRef = this.dialog.open(ProfileDialogComponent, {
      disableClose: true,
      data: {
        user: this.authService.currentUser
      }
    });

    dialogRef.afterClosed().subscribe(user => {
      if (user) {
        this.authService.currentUser = user
      }
    });
  }
}
