import { Component, OnInit } from '@angular/core';
import { RegistrationService } from '../registration.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-registration-error-page',
  templateUrl: './registration-error-page.component.html',
  styleUrls: ['./registration-error-page.component.css']
})
export class RegistrationErrorPageComponent implements OnInit {

  taskId: number;
  formProperties: any[];
  isDoneLoading = false;
  confirmationDetails: any;

  constructor(private registrationService: RegistrationService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.taskId = +params["taskId"];
      this.registrationService.getTaskData(this.taskId).subscribe(response => {
        this.formProperties = response;
        this.isDoneLoading = true;
        this.formConfirmationDetails();
      })
    })
  }

  confirm() {
    this.registrationService.confirm(this.confirmationDetails, this.taskId).subscribe(response => {
      console.log(response);
    });
  }

  formConfirmationDetails() {
    this.confirmationDetails = {};
    for (let prop of this.formProperties) {
      if (prop.writable) {
      this.confirmationDetails[prop.id] = prop.value;
      }
    }
  }

}
