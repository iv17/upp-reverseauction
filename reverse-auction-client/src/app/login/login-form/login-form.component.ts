import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { LoginDetails } from '../loginDetails.model';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  loginDetails: LoginDetails;

  @Output()
  loginExecuted: EventEmitter<LoginDetails> = new EventEmitter();

  constructor() { 
    this.loginDetails = new LoginDetails();
  }

  ngOnInit() {
  }

  login() {
    this.loginExecuted.emit(this.loginDetails);
  }

}
