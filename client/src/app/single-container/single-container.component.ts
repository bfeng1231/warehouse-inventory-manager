import { Component, OnInit } from '@angular/core';
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
    showModal: any = {state: false, id: 0, data: {}}
    itemEntry: any = {}
    currentSpace: number = 0
    itemChecklist: Array<number> = []

    constructor(service: ContainerApiService, itemService: ItemApiService, private route: ActivatedRoute, private router: Router) { 
        this.service = service
        this.itemService = itemService
    }

    ngOnInit(): void {
        this.id = this.route.snapshot.paramMap.get('id')
        this.service.findById(this.id)
            .subscribe({
                next: resp => {this.container = resp},
                error: err => {this.router.navigate([''])}
            })
        this.itemService.getTotalSpaceById(this.id).subscribe(resp => this.currentSpace = resp)
    }

    showEdit(): void {
        this.edit = !this.edit
        this.itemChecklist = []
    }

    displayModal(data: any): void {
        console.log("Open modal")
        this.showModal = {state: !this.showModal.state, id: 0, data}
        console.log(this.showModal)
    }

    addItem(data: any): void {
        console.log(data)
        if ((data.size * data.amount) + this.currentSpace > this.container.transport_size)
            return window.alert("Unable to add item due to insufficant amount of space")
        else {
            this.currentSpace += (data.size * data.amount)
            this.itemService.save(data, this.id).subscribe(resp => {
                console.log(resp)
                this.itemEntry = resp
            })
        }
        
    }

    updateEditList(data: Array<number>): void {
        this.itemChecklist = data
        console.log(this.itemChecklist)
    }

    deleteItems(): void {
        this.itemService.delete(this.itemChecklist).subscribe(resp => console.log(resp))
    }

    editContainer(data: any): void {
        this.service.update(data, this.id).subscribe(resp => console.log(resp))
    }
}
