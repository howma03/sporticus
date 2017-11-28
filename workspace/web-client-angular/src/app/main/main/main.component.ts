import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Breadcrumb, PageHeaderIconMenu, PageHeaderNavigationItem} from '@ux-aspects/ux-aspects';
import {AuthService} from '../../auth/auth.service';
import {ProfileDialogComponent} from '../../profile/profile-dialog/profile-dialog.component';
import {MatDialog} from '@angular/material';
import {PushService} from '../../services/push.service';
import {NotificationService} from '../../notification/notification.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  notifications = [];

  notificationCount = 0;

  iconMenus: PageHeaderIconMenu[] = [
    {
      icon: 'hpe-notification',
      badge: this.notificationCount,
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

  title = 'Sporticus';

  condensed = true;

  items: PageHeaderNavigationItem[] = [
    {
      icon: 'hpe-home',
      title: 'Home',
      select: () => this.router.navigate(['main/home'])
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
          select: () => this.router.navigate(['main/manage/organisation'])
        },
        {
          title: 'Members',
          select: () => this.router.navigate(['main/manage/members'])
        },
        {
          title: 'Competitions',
          select: () => this.router.navigate(['main/manage/competitions'])
        }

      ]
    }
  ];

  constructor(
    private router: Router,
    private authService: AuthService,
    private dialog: MatDialog,
    private pushService: PushService,
    private notificationService: NotificationService,
  ) {
  }

  ngOnInit() {

    if (this.authService.currentUser.admin) {
      this.items.push({
        icon: 'hpe-support',
        title: 'Administration',
        children: [
          {
            title: 'Users',
            select: () => this.router.navigate(['main/admin/users'])
          },
          {
            title: 'Organisations',
            select: () => this.router.navigate(['main/admin/organisations'])
          }
        ]
      });
    }

    this.updateNotificationDetails();

    this.pushService.registerForEvents().subscribe((data) => {
      // Subscribe for notification changes.
      this.updateNotificationDetails();
    });

  }

  updateNotificationDetails() {
    this.notificationService.retrieveAll()
      .map(list => list.data)
      .subscribe((data) => {
        this.notifications = data;
        this.notificationCount = this.notifications.length;
      });
  }

  /**
   * Create a menu showing notifications received but not yet looked at.
   * @param {PageHeaderIconMenu} menu The parent menu to attach to
   */
  createNotificationsMenu(menu: PageHeaderIconMenu) {


    let broadcastCount = 0;
    let ladderCount = 0;

    if (this.notifications.length === 0) {
      menu.dropdown = [
        {
          icon: 'hpe-chat',
          title: 'Loading...',
          divider: true,
          select: () => {
            this.gotoNotification();
          }
        }];
      return;
    }

    let i;

    const menuArray = [];

    if (this.notifications.length === 0) {
      menu.dropdown = [
        {
          icon: 'hpe-chat',
          title: 'You have no new notifications',
        }];
    }

    for (i = 0; i < this.notifications.length; i++) {
     if (this.notifications[i].title === 'Broadcast') {
       broadcastCount++;
     }
      if (this.notifications[i].title === 'Ladder Challenge') {
        ladderCount++;
      }
    }

    if (broadcastCount > 0) {
      menuArray.push({
        icon: 'fa fa-bullhorn',
        title: 'You have ' + broadcastCount + ' broadcasts',
        select: () => {
        this.gotoNotification();
      }
      });
    }

    if (ladderCount > 0) {
      menuArray.push({
        icon: 'hpe-icon hpe-target',
        title: 'You have received ' + ladderCount + ' ladder challenge',
        select: () => {
          this.gotoNotification();
        }
      });
    }

      menu.dropdown = menuArray;
  }

  getUserName(): string {
    return this.authService.currentUser.email;
  }

  goHome() {
    this.router.navigate(['main/home']);
  }

  gotoNotification() {
    this.router.navigate(['main/notification']);
  }

  doLogout() {
    this.router.navigate(['landing/login']);
  }

  openProfile() {
    const dialogRef = this.dialog.open(ProfileDialogComponent, {
      disableClose: true,
      data: {
        user: this.authService.currentUser
      }
    });

    dialogRef.afterClosed().subscribe(user => {
      if (user) {
        this.authService.currentUser = user;
      }
    });
  }
}
