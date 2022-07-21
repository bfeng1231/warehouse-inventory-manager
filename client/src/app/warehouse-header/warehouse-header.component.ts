import { Component, OnInit } from '@angular/core';
import { WarehouseApiService } from '../services/warehouse-api-service';
import { WarehouseInfoService } from '../services/warehouse-info.service';

@Component({
    selector: 'app-warehouse-header',
    templateUrl: './warehouse-header.component.html',
    styleUrls: ['./warehouse-header.component.css']
})
export class WarehouseHeaderComponent implements OnInit {

    service: WarehouseApiService
    warehouse: any = {}
    currentSpace: number = 0
    warehouseData: WarehouseInfoService

    constructor(service: WarehouseApiService, warehouseData: WarehouseInfoService) { 
        this.service = service
        this.warehouseData = warehouseData
    }

    ngOnInit(): void {    
        this.service.findById(1).subscribe(resp => {
            this.warehouse = resp
            this.warehouseData.totalSpace = resp.warehouse_size
        })
        this.service.getCurrentSpace(1).subscribe(resp => {
            this.currentSpace = resp
            this.warehouseData.currentSpace = resp
        })

        setInterval(() => {
            if (this.warehouseData.recall)
                this.service.getCurrentSpace(1).subscribe(resp => {
                    this.currentSpace = resp
                    this.warehouseData.currentSpace = resp
                    this.warehouseData.recall = false
                })
        }, 2000)
    }

}
