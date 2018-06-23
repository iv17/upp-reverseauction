import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TaskService } from '../task.service';
import { NotificationsService } from 'angular2-notifications';

@Component({
  selector: 'app-task-page',
  templateUrl: './task-page.component.html',
  styleUrls: ['./task-page.component.css']
})
export class TaskPageComponent implements OnInit {

  task: any;
  id: string;
  isFetched = false;
  taskDetails: any;

  constructor(private route: ActivatedRoute, private taskService: TaskService, private notificationService: NotificationsService) { 
    this.taskDetails = {};
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params["taskId"];
      this.taskService.getTask(this.id).subscribe(response => {
        this.task = response;
        this.formTaskDetails();
        this.isFetched = true;
      })
    })
  }

  completeTask(o?: any) {
    this.taskDetails = o || this.taskDetails;
    this.removeNonWriteableFields();
    this.taskService.executeTask(this.id, this.taskDetails).subscribe(response => {
      this.notificationService.success(
        response.message
      );
    }, error => {
      this.notificationService.error(
        error.error.message
      );
    });
  }

  removeNonWriteableFields() {
    const nonWriteableProps = this.task.props.filter(p => !p.writable);
    nonWriteableProps.forEach(p => delete this.taskDetails[p.id]); 
  }

  formTaskDetails() {
    this.task.props.forEach(t => {
      this.taskDetails[t.id] = t.value;
    });
  }

}
