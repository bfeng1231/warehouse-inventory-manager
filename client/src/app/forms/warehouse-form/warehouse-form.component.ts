import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
    selector: 'app-warehouse-form',
    templateUrl: './warehouse-form.component.html',
    styleUrls: ['./warehouse-form.component.css']
})
export class WarehouseFormComponent implements OnInit {

    @Input() data: any
    @Output() submitEvent = new EventEmitter<any>()
    warehouseFormData: any = {}
    message: string = ''
    error: boolean = false
    id: number = 0

    constructor() { }

    ngOnInit(): void {
        // Found existing data so we editing a warehouse, populate the fields
        if (this.data.hasOwnProperty('warehouse')) {
            this.warehouseFormData = {
                warehouse_id: this.data.warehouse.warehouse_id,
                warehouse_size: this.data.warehouse.warehouse_size,
                address: this.data.warehouse.address,
                zipcode: this.data.warehouse.zipcode
            }
            this.id = this.data.warehouse.warehouse_id
        }
    }

    onSubmit(warehouse: any) {
        if (Object.keys(warehouse).length < 3) {
            this.error = true
            this.message = 'Please enter all fields'
            return
        }
        if (warehouse.warehouse_size >= 999999999) {
            this.error = true
            this.message = 'That warehouse is suspiciously too big'
            return
        }
        if (warehouse.warehouse_size <= 0) {
            this.error = true
            this.message = 'The warehouse size can not be zero or less'
            return
        }

        if (warehouse.address.length > 50) {
            this.error = true
            this.message = 'The address is too long'
            return
        }

        if (warehouse.zipcode < 10000 || warehouse.zipcode > 99999) {
            this.error = true
            this.message = 'A zipcode must have 5 numbers'
            return
        }

        this.submitEvent.emit(warehouse)
    }
}
