import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ItemApiService } from '../services/item-api.service';
import { SortService } from '../services/sort.service';

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
        // Checks the item we recieved. If it exists, we edit it. Else we add it
        let index = this.items.findIndex(elem => elem.item_id == data.item_id)
        index != -1 ? this.items[index] = {...data , datetime: Date.now()} : this.items.push({...data, datetime: Date.now()})          
    }
    @Output() onChangeEvent = new EventEmitter<Array<number>>()
    @Input() itemChecklist: Array<number> = []
    @Output() onClickEvent = new EventEmitter<any>()
    sortBy: any = {sort: "item_id", order: "asc"}
    sortState: any

    constructor(service: ItemApiService, sortState: SortService) { 
        this.service = service
        this.sortState = sortState
    }

    ngOnInit(): void {
        console.log("Getting items for container", this.id)
        this.getData()
        // Check if we need to refesh the item list because a change occured
        setInterval(() => {
            if (this.service.recall)
               this.getData()
        }, 2000)
    }

    getData() {
        this.service.findAll(this.id, this.sortBy.sort, this.sortBy.order).subscribe(resp => {
            console.log(resp)
            this.items = resp
            this.service.recall = false
        })
    }

    // Adds or removes an item for a list of items that are to be deleted
    itemChecklistHandler(id: number): void {
        let index = this.itemChecklist.indexOf(id)
        index == -1 ? this.itemChecklist.push(id) : this.itemChecklist.splice(index, 1)
        console.log(this.itemChecklist)
        this.onChangeEvent.emit(this.itemChecklist)
    }

    openModal(data: any): void {
        this.onClickEvent.emit(data)
    }

    // Determine the sorting method on the items
    sort(data: any) {
        this.sortBy = {...data}
        this.sortState.switchStates(data.sort)
        this.getData()
    }
}
