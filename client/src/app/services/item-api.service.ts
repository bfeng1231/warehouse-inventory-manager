import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class ItemApiService {

    http: HttpClient

    constructor(http: HttpClient) { 
        this.http = http
    }
  
    findById(id: number, sort: string, order: string): Observable<any> {
        return this.http.get(environment.apiUrl + `items/${id}?sort=${sort}&order=${order}`)
    }

    getTotalSpaceById(id: number): Observable<any> {
        return this.http.get(environment.apiUrl + "items/" + id + "/total")
    }
    
}
