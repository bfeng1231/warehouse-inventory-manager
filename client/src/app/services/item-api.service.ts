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
  
    findAll(id: number, sort: string, order: string): Observable<any> {
        return this.http.get(environment.apiUrl + `items/${id}?sort=${sort}&order=${order}`)
    }

    findById(id: number): Observable<any> {
        return this.http.get(environment.apiUrl + "items/" + id)
    }

    getTotalSpaceById(id: number): Observable<any> {
        return this.http.get(environment.apiUrl + "items/" + id + "/total")
    }

    
    save(data: any, id: number): Observable<any> {
        let body = {name: data.name, size: data.size, units: data.amount, container_id: id}
        return this.http.post(environment.apiUrl + "items/", body)
    }

    delete(data: Array<number>): Observable<any> {
        let options = {
            body: {
                ids: data
            }
        }
        return this.http.delete(environment.apiUrl + "items/", options)
    }

    update(data: any, id: number): Observable<any> {
        let body = {
            item_id: data.item_id,
            name: data.name,
            size: data.size,
            units: data.amount,
            container_id: id
        }
        return this.http.put(environment.apiUrl + "items/", body)
    }
}
