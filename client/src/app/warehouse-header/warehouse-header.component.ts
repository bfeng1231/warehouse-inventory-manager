import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
    wh_id: any

    constructor(service: WarehouseApiService, warehouseData: WarehouseInfoService, private route: ActivatedRoute) { 
        this.service = service
        this.warehouseData = warehouseData
    }

    ngOnInit(): void {
        this.wh_id = this.route.snapshot.paramMap.get('warehouse-id')    
        this.service.findById(this.wh_id).subscribe(resp => {
            this.warehouse = resp
            this.warehouseData.totalSpace = resp.warehouse_size
        })
        this.service.getCurrentSpace(this.wh_id).subscribe(resp => {
            this.currentSpace = resp
            this.warehouseData.currentSpace = resp
        })

        setInterval(() => {
            if (this.warehouseData.recall) {
                this.service.findById(this.wh_id).subscribe(resp => {
                    this.warehouse = resp
                    this.warehouseData.totalSpace = resp.warehouse_size
                })
                this.service.getCurrentSpace(this.wh_id).subscribe(resp => {
                    this.currentSpace = resp
                    this.warehouseData.currentSpace = resp
                    this.warehouseData.recall = false
                })
            }   
        }, 2000)
    }

}
