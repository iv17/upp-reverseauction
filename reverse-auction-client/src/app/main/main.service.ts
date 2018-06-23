import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class MainService {

    private baseUrl = 'http://localhost:8080/api/process';

    constructor(private http: HttpClient) { }

    startNewProcurement() {
        return this.http.get<any>(this.baseUrl);
    }

    saveProcurement(procurement: any) {
        return this.http.post<any>(this.baseUrl, procurement);
    }

}
