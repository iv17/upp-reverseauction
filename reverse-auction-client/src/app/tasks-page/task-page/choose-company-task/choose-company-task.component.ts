import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { NotificationsService } from 'angular2-notifications';

@Component({
  selector: 'app-choose-company-task',
  templateUrl: './choose-company-task.component.html',
  styleUrls: ['./choose-company-task.component.css']
})
export class ChooseCompanyTaskComponent implements OnInit {

  taskDetails: any;
  chosenCompanyName: string = '';

  @Input()
  offers: any[];

  @Output()
  onTaskCompleted: EventEmitter<any> = new EventEmitter();

  constructor() {
    this.taskDetails = { chosenCompanyId: -1 }
  }

  ngOnInit() {
  }

  completeTask() {
    this.onTaskCompleted.emit(this.taskDetails);
  }

  selectCompany(index) {
    this.taskDetails.chosenCompanyId = this.offers[index].owner.id;
    this.chosenCompanyName = this.offers[index].owner.agent.name;
  }

}
