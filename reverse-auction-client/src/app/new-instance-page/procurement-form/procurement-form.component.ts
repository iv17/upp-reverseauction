import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ProcurementDetails } from './procurement.model';

@Component({
  selector: 'app-procurement-form',
  templateUrl: './procurement-form.component.html',
  styleUrls: ['./procurement-form.component.css']
})
export class ProcurementFormComponent implements OnInit {

  @Input()
  formProperties: any[];

  @Input()
  categories: any[];

  @Output()
  newInstanceExecuted: EventEmitter<ProcurementDetails> = new EventEmitter();

  procurementDetails: any;

  constructor() {
    this.procurementDetails = {};
   }

  ngOnInit() {
  }

  startNewInstance() {
    this.newInstanceExecuted.emit(this.procurementDetails);
  }
}
