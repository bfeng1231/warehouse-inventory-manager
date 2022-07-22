import { Component, OnInit } from '@angular/core';
import { ContainerApiService } from '../services/container-api.service';
import { WarehouseInfoService } from '../services/warehouse-info.service';
import { ActivatedRoute } from '@angular/router';
import { WarehouseApiService } from '../services/warehouse-api-service';

@Component({
    selector: 'app-container-page',
    templateUrl: './container-page.component.html',
    styleUrls: ['./container-page.component.css']
})
export class ContainerPageComponent implements OnInit {

    service: ContainerApiService
    containers: Array<any> = []
    showModal: any = {state: false, data: {}}
    warehouseData: WarehouseInfoService
    id: any
    warehouse: any = {}
    warehouseService: WarehouseApiService

    constructor(service: ContainerApiService, warehouseData: WarehouseInfoService, private route: ActivatedRoute, warehouseService: WarehouseApiService) { 
        this.service = service
        this.warehouseData = warehouseData
        this.warehouseService = warehouseService
    }

    ngOnInit(): void {
        this.getData()
    }

    getData() {
        this.id = this.route.snapshot.paramMap.get('warehouse-id')
        this.warehouseService.findById(this.id).subscribe(resp => {this.warehouse = resp
        console.log(resp)})
        this.service.findAll(this.id,'container_id', 'asc').subscribe(resp => {
            console.log(resp)
            this.containers = resp})
    }

    // updateView(container: any): void {
    //     this.containers.push(container)
    //     this.getData()
    // }

    displayModal(data: any): void {
        console.log("Open modal")
        this.showModal = {state: !this.showModal.state, data}
        console.log(this.showModal)
    }
    
    deleteContainer(id: number): void {
        this.service.delete(id).subscribe(resp => {
            console.log(resp) 
            this.warehouseData.updateTotal()          
        })
        let index = this.containers.findIndex(elem => elem.id == id)
        this.containers.splice(index, 1)
        this.showModal = {state: !this.showModal.state, data: {}}
    }

    addContainer(formData: any) {
        formData = {...formData, warehouse_id: this.id}
        console.log(formData)
        let allocateSpace = 0
        switch(formData.transport) {
            case 1:
                allocateSpace = 50
                break
            case 2:
                allocateSpace = 200
                break
            case 3:
                allocateSpace = 1000
                break
        }
        if (this.warehouseData.currentSpace + allocateSpace > this.warehouseData.totalSpace)
            return window.alert("Unable to add due to insufficant amount of space in warehouse")

        this.warehouseData.updateTotal()
        this.service.save(formData)
            .subscribe({
                next: resp => {
                        console.log(resp)
                        this.containers.push(resp)
                        this.getData()
                    },
                error: err => {window.alert("A container already exists on that location")}
            })
        this.showModal = {state: false, data: {}}
    }

    searchContainers(data: any) {

        if (data.input == '')
             return this.getData()

        if (data.type == 'item') {
            this.service.findByItem(this.id, data.input).subscribe(resp => {
                console.log(resp)
                this.containers = resp
            })
        }
        else {
            this.service.findByTerm(this.id, data.input).subscribe(resp => {
                console.log(resp)
                this.containers = resp
            })
        }
    }

    editWarehouse(data: any) {
        console.log(data)
        if (this.warehouseData.currentSpace > data.warehouse_size)
            return window.alert("Cannot set warehouse size lower than the space it is currently taking up")
        
        this.warehouseService.update(data).subscribe(resp => {
            this.warehouseData.updateTotal()
            this.showModal = {state: false, data: {}}
        })
        
    }
}
