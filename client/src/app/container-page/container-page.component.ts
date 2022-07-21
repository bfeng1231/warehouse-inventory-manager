import { Component, OnInit, SimpleChanges } from '@angular/core';
import { ContainerApiService } from '../services/container-api.service';
import { WarehouseInfoService } from '../services/warehouse-info.service';

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

    constructor(service: ContainerApiService, warehouseData: WarehouseInfoService) { 
        this.service = service
        this.warehouseData = warehouseData
    }

    ngOnInit(): void {
        this.service.findAll('container_id', 'asc').subscribe(resp => {
            console.log(resp)
            this.containers = resp})
    }

    updateView(container: any): void {
        this.containers.push(container)
        this.service.findAll('container_id', 'asc').subscribe(resp => {
            console.log(resp)
            this.containers = resp})
    }

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
                        this.updateView(resp)
                    },
                error: err => {window.alert("A container already exists on that location")}
            })
        this.showModal = {state: false, data: {}}
    }

}
