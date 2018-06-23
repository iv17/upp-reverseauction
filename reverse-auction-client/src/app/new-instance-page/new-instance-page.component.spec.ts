import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewInstancePageComponent } from './new-instance-page.component';

describe('NewInstancePageComponent', () => {
  let component: NewInstancePageComponent;
  let fixture: ComponentFixture<NewInstancePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewInstancePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewInstancePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
