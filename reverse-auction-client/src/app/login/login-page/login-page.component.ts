import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';
import { LoginDetails } from '../loginDetails.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  constructor(private loginService: LoginService, private router: Router) { }

  ngOnInit() {
  }

  login(loginDetails: LoginDetails) {
    this.loginService.login(loginDetails).subscribe(response => {
      console.log(response);
      localStorage.setItem('auction-user', JSON.stringify({
        'id': response.id,
        'token': response.token,
      }));
      localStorage.setItem('isPrivateUser', JSON.stringify(response.privateUser));
      localStorage.setItem('username', JSON.stringify(response.username));
      this.router.navigate(['/main']);
    });
  }

}
