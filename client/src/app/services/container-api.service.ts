import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class ContainerApiService {

    /* Telling Angular to create a singleton service. */
    http: HttpClient

    constructor(http: HttpClient) { 
        this.http = http
    }

    findAll(sort: string, order: string): Observable<any> {
        return this.http.get(environment.apiUrl + `containers/?sort=${sort}&order=${order}`)
    }
    
    findById(id: number): Observable<any> {
        return this.http.get(environment.apiUrl + "containers/" + id)
    }

    delete(id: number): Observable<any> {
        return this.http.delete(environment.apiUrl + "containers/" + id)
    }

    save(data: any): Observable<any> {
        let body = {
            transport_id: data.transport,
            warehouse_id: 1,
            location: data.location
        }
        return this.http.post(environment.apiUrl + "containers/", body)
    }

    update(data: any, id: number): Observable<any> {
        let body = {
            id,
            transport_id: data.transport,
            warehouse_id: 1,
            location: data.location
        }
        return this.http.put(environment.apiUrl + "containers/", body)
    }
}
