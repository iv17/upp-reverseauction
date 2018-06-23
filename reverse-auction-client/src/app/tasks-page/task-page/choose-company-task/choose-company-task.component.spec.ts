import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseCompanyTaskComponent } from './choose-company-task.component';

describe('ChooseCompanyTaskComponent', () => {
  let component: ChooseCompanyTaskComponent;
  let fixture: ComponentFixture<ChooseCompanyTaskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChooseCompanyTaskComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseCompanyTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
