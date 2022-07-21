import { Component, OnInit } from '@angular/core';
import { WarehouseApiService } from '../services/warehouse-api-service';

@Component({
    selector: 'app-warehouse-header',
    templateUrl: './warehouse-header.component.html',
    styleUrls: ['./warehouse-header.component.css']
})
export class WarehouseHeaderComponent implements OnInit {

    service: WarehouseApiService
    warehouse: any = {}
    currentSpace: number = 0

    constructor(service: WarehouseApiService) { 
        this.service = service
    }

    ngOnInit(): void {
        this.service.findById(1).subscribe(resp => this.warehouse = resp)
        this.service.getCurrentSpace(1).subscribe(resp => this.currentSpace = resp)
    }

}
