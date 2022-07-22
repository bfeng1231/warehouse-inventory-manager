import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
    selector: 'app-item-form',
    templateUrl: './item-form.component.html',
    styleUrls: ['./item-form.component.css']
})
export class ItemFormComponent implements OnInit {

    itemFormData: any = {}
    message: string = ''
    id: number = 0
    @Output() submitEvent = new EventEmitter<any>()
    @Input() data: any = {}

    constructor() { }

    ngOnInit(): void {
        // Found existing data so we editing an item, populate the fields
        if (this.data.hasOwnProperty('item')) {
            this.itemFormData = {
                item_id: this.data.item.item_id,
                name: this.data.item.name,
                size: this.data.item.size,
                amount: this.data.item.units
            }
            this.id = this.data.item.item_id
        }
    }

    onSubmit(data: any): void {
        if (Object.keys(data).length < 3) {
            this.message = 'Please enter all fields'
            return
        }
        if (data.name.length >= 30) {
            this.message = 'The item name can only be 30 characters or less'
            return
        }
        if (data.size <= 0) {
            this.message = 'The item size can not be zero or less'
            return
        }
        if (data.amount <= 0) {
            this.message = 'The amount of items can not be zero or less'
            return
        }

        this.submitEvent.emit(data)
    }
}
