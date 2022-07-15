import { Component, OnInit } from '@angular/core';
import { ContainerApiService } from '../services/container-api.service';

@Component({
    selector: 'app-container-page',
    templateUrl: './container-page.component.html',
    styleUrls: ['./container-page.component.css']
})
export class ContainerPageComponent implements OnInit {

    service: ContainerApiService
    containers: Array<any> = []
    showModal: any = {state: false, id: 0, data: {}}

    constructor(service: ContainerApiService) { 
        this.service = service
    }

    ngOnInit(): void {
        this.service.findAll('container_id', 'asc').subscribe(resp => {
            console.log(resp)
            this.containers = resp})
    }

    displayModal(data: any): void {
        console.log("Open modal")
        this.showModal = {state: !this.showModal.state, id: 0, data}
        console.log(this.showModal)
    }
    
    deleteContainer(id: number): void {
        // this.service.delete(id).subscribe(resp => {
        //     console.log(resp)           
        // })
        let index = this.containers.findIndex(elem => elem.id == id)
        this.containers.splice(index, 1)
        this.showModal = {state: !this.showModal.state, id: 0}
    }

    addContainer(formData: any) {
        this.service.save(formData).subscribe(resp => {
            console.log(resp)
            this.ngOnInit()
        })
    }

}
