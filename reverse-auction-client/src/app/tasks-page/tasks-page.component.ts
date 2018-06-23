import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TaskService } from './task.service';

@Component({
  selector: 'app-tasks-page',
  templateUrl: './tasks-page.component.html',
  styleUrls: ['./tasks-page.component.css']
})
export class TasksPageComponent implements OnInit {

  tasks: any[];

  constructor(private route: ActivatedRoute, private taskService: TaskService) { }

  ngOnInit() {
    this.taskService.getTasks().subscribe((response) => {
      this.tasks = response;
    });
  }

}
