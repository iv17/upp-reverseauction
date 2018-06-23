import { Component, OnInit } from '@angular/core';
import { RegistrationService } from '../registration.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-company-category-page',
  templateUrl: './company-category-page.component.html',
  styleUrls: ['./company-category-page.component.css']
})
export class CompanyCategoryPageComponent implements OnInit {

  taskId: number;
  formProperties: any[];
  isDoneLoading = false;
  categories: any[];
  companyDetails: any;
  chosenCategories: any[] = [];

  constructor(private registrationService: RegistrationService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.taskId = +params["taskId"];
      this.registrationService.getTaskData(this.taskId).subscribe(response => {
        this.formProperties = response;
        this.formCompanyDetails();
        this.registrationService.getCategories().subscribe(response => {
          this.categories = response;
          this.isDoneLoading = true;
        })
      })
    });
  }

  addCategory(catId: number) {
    let category = this.categories.find(c => c.id === catId);
    this.chosenCategories.push(category);
  }

  removeCategory(catId: number) {
    let idx = this.chosenCategories.findIndex(c => c.id === catId);
    this.chosenCategories.splice(idx, 1);
  }

  confirm() {
    this.updateCompanyDetails();
    this.registrationService.confirm(this.companyDetails, this.taskId).subscribe(response => {
      console.log(response);
    });
  }

  formCompanyDetails() {
    this.companyDetails = {};
    for(let prop of this.formProperties) {
      this.companyDetails[prop.id] = prop.value;
    }
  }

  updateCompanyDetails() {
    for(let category of this.chosenCategories) {
      this.companyDetails[category.nameLowercase] = category.id;
    }
  }

}
