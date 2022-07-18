import { Component, OnInit } from '@angular/core';
import { ContainerApiService } from '../services/container-api.service';
import { ActivatedRoute } from '@angular/router';
import { Router } from "@angular/router";

@Component({
    selector: 'app-single-container',
    templateUrl: './single-container.component.html',
    styleUrls: ['./single-container.component.css']
})
export class SingleContainerComponent implements OnInit {

    service: ContainerApiService
    id: any
    container: any = {}
    edit: boolean = false
    showModal: any = {state: false, id: 0, data: {}}
    itemEntry: any = {}

    constructor(service: ContainerApiService, private route: ActivatedRoute, private router: Router) { 
        this.service = service
        //
    }

    ngOnInit(): void {
        this.id = this.route.snapshot.paramMap.get('id')
        this.service.findById(this.id)
            .subscribe({
                next: resp => {this.container = resp},
                error: err => {this.router.navigate([''])}
            })
        
    }

    showEdit(): void {
        this.edit = !this.edit
    }

    displayModal(data: any): void {
        console.log("Open modal")
        this.showModal = {state: !this.showModal.state, id: 0, data}
        console.log(this.showModal)
    }

    addItem(data: any): void {
        console.log(data)
        this.itemEntry = data
    }

}
