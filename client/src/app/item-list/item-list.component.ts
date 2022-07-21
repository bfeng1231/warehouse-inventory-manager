import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ItemApiService } from '../services/item-api.service';

@Component({
    selector: 'app-item-list',
    templateUrl: './item-list.component.html',
    styleUrls: ['./item-list.component.css']
})
export class ItemListComponent implements OnInit {

    service: ItemApiService
    items: Array<any> = []
    @Input() id: number = 0
    @Input() edit: boolean = false
    @Input() set item(data: any) {
        let index = this.items.findIndex(elem => elem.item_id == data.item_id)
        index != -1 ? this.items[index] = {...data , datetime: Date.now()} : this.items.push({...data, datetime: Date.now()})          
    }
    @Output() onChangeEvent = new EventEmitter<Array<number>>()
    @Input() itemChecklist: Array<number> = []
    @Output() onClickEvent = new EventEmitter<any>()

    constructor(service: ItemApiService) { 
        this.service = service
    }

    ngOnInit(): void {
        console.log("Getting items for container", this.id)
        this.service.findAll(this.id, "item_id", "asc").subscribe(resp => {
            console.log(resp)
            this.items = resp})
           
    }

    itemChecklistHandler(id: number): void {
        let index = this.itemChecklist.indexOf(id)
        index == -1 ? this.itemChecklist.push(id) : this.itemChecklist.splice(index, 1)
        console.log(this.itemChecklist)
        this.onChangeEvent.emit(this.itemChecklist)
    }

    openModal(data: any): void {
        this.onClickEvent.emit(data)
    }

}
