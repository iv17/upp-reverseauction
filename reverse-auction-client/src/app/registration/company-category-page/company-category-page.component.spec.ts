import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyCategoryPageComponent } from './company-category-page.component';

describe('CompanyCategoryPageComponent', () => {
  let component: CompanyCategoryPageComponent;
  let fixture: ComponentFixture<CompanyCategoryPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompanyCategoryPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompanyCategoryPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
