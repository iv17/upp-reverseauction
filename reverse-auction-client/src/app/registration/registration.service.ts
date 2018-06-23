import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RegistrationDetails } from './regsitrationDetails.model';

@Injectable()
export class RegistrationService {

    private baseUrl = 'http://localhost:8080/api/register';

    constructor(private http: HttpClient) { }

    startProcess() {
        return this.http.get<any[]>(`${this.baseUrl}`);
    }

    register(registration: RegistrationDetails): Observable<any> {
        const options = {
            headers: new HttpHeaders({ 'Content-Type': 'application/json' })
        }
        return this.http.post<any>(`${this.baseUrl}`, registration, options);
    }

    getNextTask(username: string) {
        return this.http.get<any>(`${this.baseUrl}/task?username=${username}`);
    }

    getTaskData(taskId: number) {
        return this.http.get<any>(`${this.baseUrl}/task/${taskId}`);
    }

    caluclateCoordinates() {

    }

    verify(id: number) {
        return this.http.put<any>(`${this.baseUrl}/verify/${id}`, {});
    }

    confirm(formData, taskId) {
        return this.http.put<any>(`${this.baseUrl}/task/execute/${taskId}`, formData);
    }

    getCategories() {
        return this.http.get<any[]>(`${this.baseUrl}/categories`);
    }
}
