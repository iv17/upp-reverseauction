import { Component, OnInit } from '@angular/core';
import { ProcessService } from './process.service';
import { RegistrationService } from '../registration/registration.service';
import { NotificationsService } from 'angular2-notifications';

@Component({
  selector: 'app-new-instance-page',
  templateUrl: './new-instance-page.component.html',
  styleUrls: ['./new-instance-page.component.css']
})
export class NewInstancePageComponent implements OnInit {

  formProperties: any[];
  categories: any[];
  constructor(private processService: ProcessService, private registrationService: RegistrationService,
    private notificationService: NotificationsService) { }

  ngOnInit() {
    this.processService.getNewInstance()
      .subscribe(response => {
        this.formProperties = response;
        this.registrationService.getCategories().subscribe((response) => {
          this.categories = response;
        });
      });
  }

  startNewInstance(procurement: any) {
    this.processService.startNewInstance(procurement).subscribe(response => {
      this.notificationService.success(
        response.message
      );
    }, error => {
      console.log(error);
      this.notificationService.error(
        error.error.message
      );
    });
  }

}
