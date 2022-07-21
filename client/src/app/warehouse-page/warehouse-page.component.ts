import { Component, OnInit } from '@angular/core';
import { WarehouseApiService } from '../services/warehouse-api-service';

@Component({
    selector: 'app-warehouse-page',
    templateUrl: './warehouse-page.component.html',
    styleUrls: ['./warehouse-page.component.css']
})
export class WarehousePageComponent implements OnInit {

    service: WarehouseApiService
    warehoueses: Array<any> = []

    constructor(service: WarehouseApiService) {
        this.service = service
     }

    ngOnInit(): void {
        this.service.findAll().subscribe(resp => this.warehoueses = resp)
    }

}
