import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { RegistrationDetails } from '../regsitrationDetails.model';
import { CompanyDetails } from '../companyDetails';

@Component({
  selector: 'app-registration-form',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.css']
})
export class RegistrationFormComponent implements OnInit {

  @Input()
  formProperties: any[];

  @Output()
  registrationExecuted: EventEmitter<RegistrationDetails> = new EventEmitter();

  registrationDetails: RegistrationDetails;

  constructor() {
    this.registrationDetails = new RegistrationDetails();
  }

  ngOnInit() {
  }

  register() {
    this.registrationExecuted.emit(this.registrationDetails);
  }

}
