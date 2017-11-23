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
            select: () => this.router.navigate(['main/admin-users'])
          },
          {
            title: 'Organisations',
            select: () => this.router.navigate(['main/admin-organisations'])
          }
        ]
      });
    }
  }

  title = 'Sporticus';

  condensed: boolean = true;

  crumbs: Breadcrumb[] = [{
    title: 'Sporticus',
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
      title: 'Calendar',
      select: () => this.router.navigate(['main/calendar'])
    },
    {
      icon: 'hpe-analytics',
      title: 'Manage',
      children: [
        {
          title: 'Organisation',
          select: () => this.router.navigate(['main/manage-organisation'])
        },
        {
          title: 'Members',
          select: () => this.router.navigate(['main/manage-members'])
        },
        {
          title: 'Competitions',
          select: () => this.router.navigate(['main/manage-competition'])
        }
      ]
    }

  ];

  iconMenus: PageHeaderIconMenu[] = [
    {
      icon: 'hpe-notification',
      badge: 3,
      select: (menu) => this.createNotificationsMenu(menu)
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

  /**
   * Create a menu showing notifications received but not yet looked at.
   * @param {PageHeaderIconMenu} menu The parent menu to attach to
   */
  createNotificationsMenu(menu : PageHeaderIconMenu) {
    menu.dropdown = [
      {
        icon: 'hpe-chat',
        title: 'You have 45 messages',
        subtitle: '100 minutes ago',
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
    ];
  }

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
