import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class ProcessService {

    private baseUrl = 'http://localhost:8080/api/process';

    constructor(private http: HttpClient) { }

    getNewInstance() {
        return this.http.get<any>(`${this.baseUrl}`);
    }

    startNewInstance(params: any) {
        return this.http.post<any>(`${this.baseUrl}`, params);
    }
}
