import { Component, OnInit } from '@angular/core';
import { WarehouseApiService } from '../services/warehouse-api-service';

@Component({
    selector: 'app-warehouse-page',
    templateUrl: './warehouse-page.component.html',
    styleUrls: ['./warehouse-page.component.css']
})
export class WarehousePageComponent implements OnInit {

    service: WarehouseApiService
    warehouses: Array<any> = []
    showModal: any = {state: false, data: {}}

    constructor(service: WarehouseApiService) {
        this.service = service
     }

    ngOnInit(): void {
        this.getData()
    }

    getData() {
        this.service.findAll().subscribe(resp => this.warehouses = resp)
    }

    displayModal(data: any): void {
        console.log("Open modal")
        this.showModal = {state: !this.showModal.state, data}
        console.log(this.showModal)
    }

    // Deletes the specified warehouse from the warehouse array
    deleteWarehouse(id: number): void {
        this.service.delete(id).subscribe(resp => console.log(resp))
        let index = this.warehouses.findIndex(elem => elem.warehouse_id == id)
        this.warehouses.splice(index, 1)
        this.showModal = {state: !this.showModal.state, data: {}}
    }

    // Adds the warehouse we created to the array of warehouses
    addWarehouse(data: any) {
        this.service.save(data).subscribe(resp => {
                        console.log(resp)
                        this.warehouses.push(resp)
                        this.getData()
                    })
        this.showModal = {state: false, data: {}}
    }
}
