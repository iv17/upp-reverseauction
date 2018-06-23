import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  isPrivateUser: boolean;
  username: string;

  constructor(private router: Router) {
    this.isPrivateUser = JSON.parse(localStorage.getItem('isPrivateUser'));
    this.username = JSON.parse(localStorage.getItem('username'));
   }

  ngOnInit() {
  }

  logout() {
    localStorage.removeItem('auction-user');
    this.router.navigate(['login']);
  }

}
