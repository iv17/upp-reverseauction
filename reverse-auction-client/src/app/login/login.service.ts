import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoginDetails } from './loginDetails.model';

@Injectable()
export class LoginService {

    private baseUrl = 'http://localhost:8080/api/login';

    constructor(private http: HttpClient) { }

    login(loginDetails: LoginDetails) {
        return this.http.post<any>(this.baseUrl, loginDetails);
    }
}
