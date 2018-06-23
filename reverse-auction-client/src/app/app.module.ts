import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { CustomHttpInterceptor } from './http-interceptor';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { LoginFormComponent } from './login/login-form/login-form.component';
import { LoginPageComponent } from './login/login-page/login-page.component';
import { RegistrationFormComponent } from './registration/registration-form/registration-form.component';
import { RegistrationPageComponent } from './registration/registration-page/registration-page.component';

import { RegistrationService } from './registration/registration.service';
import { TempDataService } from './registration/tempData.service';
import { VerificationPageComponent } from './registration/verification-page/verification-page.component';
import { CompanyCategoryPageComponent } from './registration/company-category-page/company-category-page.component';
import { RegistrationErrorPageComponent } from './registration/registration-error-page/registration-error-page.component';
import { LoginService } from './login/login.service';
import { MainComponent } from './main/main.component';
import { ProcurementFormComponent } from './new-instance-page/procurement-form/procurement-form.component';
import { MainService } from './main/main.service';
import { NavComponent } from './main/nav/nav.component';
import { TasksPageComponent } from './tasks-page/tasks-page.component';
import { TaskPageComponent } from './tasks-page/task-page/task-page.component';
import { TaskService } from './tasks-page/task.service';
import { TasksListComponent } from './tasks-page/tasks-list/tasks-list.component';
import { NewInstancePageComponent } from './new-instance-page/new-instance-page.component';
import { ProcessService } from './new-instance-page/process.service';
import { SimpleNotificationsModule } from 'angular2-notifications';
import { ChooseCompanyTaskComponent } from './tasks-page/task-page/choose-company-task/choose-company-task.component';


const appRoutes: Routes = [
  { path: 'login', component: LoginPageComponent },
  { path: 'register', component: RegistrationPageComponent },
  { path: 'verify/:id', component: VerificationPageComponent },
  { path: 'register-error/:taskId', component: RegistrationErrorPageComponent },
  { path: 'company-category/:taskId', component: CompanyCategoryPageComponent },
  {
    path: '',
    component: MainComponent,
    children: [
      {
        path: 'tasks',
        component: TasksPageComponent,
        children: [
          { path: ':taskId', component: TaskPageComponent }
        ]
      },
      {
        path: 'new-instance',
        component: NewInstancePageComponent,
      },
      {
        path: '**', redirectTo: ''
      }
    ]
  }
];

@NgModule({
  declarations: [
    AppComponent,
    LoginFormComponent,
    LoginPageComponent,
    RegistrationFormComponent,
    RegistrationPageComponent,
    VerificationPageComponent,
    CompanyCategoryPageComponent,
    RegistrationErrorPageComponent,
    MainComponent,
    ProcurementFormComponent,
    NavComponent,
    TasksPageComponent,
    TaskPageComponent,
    TasksListComponent,
    NewInstancePageComponent,
    ChooseCompanyTaskComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(
      appRoutes,
      {
        enableTracing: true
      }),
    BrowserAnimationsModule,
    SimpleNotificationsModule.forRoot()
  ],
  providers: [
    RegistrationService,
    TempDataService,
    LoginService,
    TaskService,
    ProcessService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CustomHttpInterceptor,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
