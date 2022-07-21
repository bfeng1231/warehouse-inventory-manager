import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ContainerApiService } from '../services/container-api.service';
import { ActivatedRoute } from '@angular/router';
import { Router } from "@angular/router";
import { ItemApiService } from '../services/item-api.service';

@Component({
    selector: 'app-single-container',
    templateUrl: './single-container.component.html',
    styleUrls: ['./single-container.component.css']
})
export class SingleContainerComponent implements OnInit {

    service: ContainerApiService
    itemService: ItemApiService
    id: any
    container: any = {}
    edit: boolean = false
    showModal: any = {state: false, data: {}}
    itemEntry: any = {}
    currentSpace: number = 0
    itemChecklist: Array<number> = []

    constructor(service: ContainerApiService, itemService: ItemApiService, private route: ActivatedRoute, private router: Router, private cdf: ChangeDetectorRef) { 
        this.service = service
        this.itemService = itemService
    }

    ngOnInit(): void {
        this.getData()
    }

    getData() {
        this.id = this.route.snapshot.paramMap.get('id')
        this.service.findById(this.id)
            .subscribe({
                next: resp => {this.container = resp
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
                    return window.alert("Unable to add item due to insufficant amount of space")        
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
            this.showModal = {state: false, data: {}}
        })
    }

    editContainer(data: any): void {
        let size = data.transport
        switch (size) {
            case 1 :
                size = 50
                break
            case 2 :
                size = 200
                break
        }
        if (this.currentSpace > size)
            return window.alert("Cannot set a smaller transport type than the current space") 

        this.service.update(data, this.id).subscribe(resp => {
            console.log(resp)
            this.showModal = {state: false, data: {}}
            this.getData()
        })
           
    }
}
