import { Component, OnInit } from '@angular/core';
import { ContainerApiService } from '../services/container-api.service';
import { ActivatedRoute } from '@angular/router';
import { Router } from "@angular/router";
import { ItemApiService } from '../services/item-api.service';
import { WarehouseInfoService } from '../services/warehouse-info.service';

@Component({
    selector: 'app-single-container',
    templateUrl: './single-container.component.html',
    styleUrls: ['./single-container.component.css']
})
export class SingleContainerComponent implements OnInit {

    service: ContainerApiService
    itemService: ItemApiService
    warehouseData: WarehouseInfoService
    id: any
    wh_id: any
    container: any = {}
    edit: boolean = false
    showModal: any = {state: false, data: {}}
    itemEntry: any = {}
    currentSpace: number = 0
    itemChecklist: Array<number> = []

    constructor(service: ContainerApiService, itemService: ItemApiService, private route: ActivatedRoute, private router: Router, warehouseData: WarehouseInfoService) { 
        this.service = service
        this.itemService = itemService
        this.warehouseData = warehouseData
    }

    ngOnInit(): void {
        this.getData()
    }

    getData() {
        this.id = this.route.snapshot.paramMap.get('container-id')
        this.wh_id = this.route.snapshot.paramMap.get('warehouse-id')
        this.service.findByTerm(this.wh_id, this.id)
            .subscribe({
                next: resp => {this.container = resp[0]
                console.log(resp)},
                error: err => {this.router.navigate([''])}
            })
        this.itemService.getTotalSpaceById(this.id).subscribe(resp => this.currentSpace = resp)
    }

    showEdit(): void {
        this.edit = !this.edit
        this.itemChecklist = []
        this.showModal = {state: false, data: {}}
    }

    displayModal(data: any): void {
        console.log("Open modal")
        this.showModal = {state: !this.showModal.state, data}
        console.log(this.showModal)
        console.log("It's a me")
    }

    addItem(data: any): void {
        console.log(data)      
        if (data.hasOwnProperty("item_id")) {
            console.log('Exisiting item')
            let currentItem
            this.itemService.findById(data.item_id).subscribe(resp => {
                let newSpace = 0
                currentItem = resp
                newSpace = this.currentSpace - (currentItem.units * currentItem.size)
                if ((data.size * data.amount) + newSpace > this.container.transport_size) {                                
                    return window.alert("Unable to apply changes to item due to insufficant amount of space")        
                }
                this.itemService.update(data, this.id)
                    .subscribe({
                        next: resp => {
                            console.log(resp)
                            this.currentSpace = newSpace + (data.size * data.amount)
                            this.showModal = {state: false, data: {}}
                            this.itemEntry = resp
                        },
                        error: err => console.log(err)
                    })

            })                    
        }
        else {
            if ((data.size * data.amount) + this.currentSpace > this.container.transport_size)
                return window.alert("Unable to add item due to insufficant amount of space")
            this.currentSpace += (data.size * data.amount)
            this.itemService.save(data, this.id).subscribe(resp => {
                console.log(resp)
                this.itemEntry = resp
                this.showModal = {state: false, data: {}}
            })
        }
        
    }

    updateEditList(data: Array<number>): void {
        this.itemChecklist = data
        console.log(this.itemChecklist)
    }

    deleteItems(data: any): void {
        this.itemService.delete(data).subscribe(resp => {
            console.log(resp)
            this.itemService.recall = true
            this.itemChecklist = []
            this.showModal = {state: false, data: {}}
        })
    }

    editContainer(data: any): void {
        data = {...data, warehouse_id: this.wh_id}
        let size = data.transport
        switch (size) {
            case 1:
                size = 50
                break
            case 2:
                size = 200
                break
            case 3:
                size = 1000
                break 
        }
        if (this.currentSpace > size)
            return window.alert("Cannot set a smaller transport type than the current space")
        
        let newSpace = this.warehouseData.currentSpace - this.container.transport_size
        if (newSpace + size > this.warehouseData.totalSpace)
            return window.alert("Cannot edit transport type to make it larger than the current warehouse space")

        this.service.update(data, this.id).subscribe(resp => {
            console.log(resp)
            this.showModal = {state: false, data: {}}
            this.getData()
            this.warehouseData.updateTotal()
        })
           
    }
}
