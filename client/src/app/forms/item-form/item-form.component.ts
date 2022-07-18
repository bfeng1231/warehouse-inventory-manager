import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
    selector: 'app-item-form',
    templateUrl: './item-form.component.html',
    styleUrls: ['./item-form.component.css']
})
export class ItemFormComponent implements OnInit {

    itemFormData: any = {}
    message: string = ''
    @Input() id: number = 0
    @Output() submitEvent = new EventEmitter<any>()

    constructor() { }

    ngOnInit(): void {
    }

    onSubmit(data: any): void {
        if (Object.keys(data).length != 3) {
            this.message = 'Please enter all fields'
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
        this.itemFormData = {}
    }
}
