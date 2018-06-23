import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class TaskService {

    private baseUrl = 'http://localhost:8080/api/process';

    constructor(private http: HttpClient) { }

    getTasks() {
        return this.http.get<any>(`${this.baseUrl}/tasks`);
    };

    getTask(id: string) {
        return this.http.get<any>(`${this.baseUrl}/tasks/${id}`);
    };

    executeTask(id: string, params: any) {
        return this.http.post<any>(`${this.baseUrl}/tasks/${id}`, params);
    };

    claimTask(id: string) {
        return this.http.get<any>(`${this.baseUrl}/tasks/${id}/claim`);
    };
}