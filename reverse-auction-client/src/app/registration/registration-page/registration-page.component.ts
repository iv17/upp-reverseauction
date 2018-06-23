import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RegistrationService } from '../registration.service';
import { RegistrationDetails } from '../regsitrationDetails.model';
import { TempDataService } from '../tempData.service';
import { CompanyDetails } from '../companyDetails';
import { } from '@types/googlemaps';

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.css']
})
export class RegistrationPageComponent implements OnInit {

  private formProperties: any[];
  private task: any;
  geocoder = new google.maps.Geocoder();

  constructor(private router: Router, private route: ActivatedRoute, private registrationService: RegistrationService,
    private tempDataService: TempDataService) { }

  ngOnInit() {
    this.registrationService.startProcess().subscribe(response => {
      this.formProperties = response;
    });
  }

  register(registration: RegistrationDetails) {
    this.tempDataService.tempData = registration;
    this.setLatLng(registration).then((updatedReg) => {
      this.registrationService.register(updatedReg).subscribe(response => {
        this.registrationService.getNextTask(updatedReg.username).subscribe(response => {
          this.router.navigate([`/${response.formKey}/${response.id}`]);
        });
      });
    }).catch(error => {
      console.log(error);
    })
  }

  setLatLng(registration: RegistrationDetails): Promise<any> {
    const promise = new Promise((resolve, reject) => {
      const address = `${registration.address}, ${registration.place}, Serbia, ${registration.zipCode}`;
      this.geocoder.geocode({address: address}, (results, status) => {
        if (status === google.maps.GeocoderStatus.OK) {
          const latlong = results[0].geometry.location;
          registration.lat = `${latlong.lat()}`;
          registration.lng = `${latlong.lng()}`;
          resolve(registration);
        } else {
          reject("something went wrong");
        }
      });
    });
    return promise;
  }

}
