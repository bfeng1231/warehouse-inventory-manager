import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class WarehouseApiService {

    http: HttpClient

    constructor(http: HttpClient) { 
        this.http = http
    }

    findAll(): Observable<any> {
        return this.http.get(environment.apiUrl + "warehouses/")
    }

    findById(id: number): Observable<any> {
        return this.http.get(environment.apiUrl + "warehouses/" + id)
    }

    getCurrentSpace(id: number): Observable<any> {
        return this.http.get(environment.apiUrl + `warehouses/${id}/total`)
    }
}
