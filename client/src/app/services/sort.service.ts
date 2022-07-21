import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class SortService {

    id_asc = true
    name_asc = true
    size_asc = true
    amount_asc = true
    date_asc = true

    constructor() { 
  
    }

    resetOrder() {
        this.id_asc = true
        this.name_asc = true
        this.size_asc = true
        this.amount_asc = true
        this.date_asc = true
    }

    switchStates(type: string) {
        let current
        switch (type) {
            case 'item_id':
                this.id_asc = !this.id_asc 
                current = this.id_asc
                this.resetOrder()
                this.id_asc = current
                break;
            case 'name':
                this.name_asc = !this.name_asc 
                current = this.name_asc
                this.resetOrder()
                this.name_asc = current
                break;
            case 'size':
                this.size_asc = !this.size_asc 
                current = this.size_asc
                this.resetOrder()
                this.size_asc = current
                break;
            case 'units':
                this.amount_asc = !this.amount_asc 
                current = this.amount_asc
                this.resetOrder()
                this.amount_asc = current
                break;
            case 'datetime':
                this.date_asc = !this.date_asc 
                current = this.date_asc
                this.resetOrder()
                this.date_asc = current
                break;
        }
    }    
}
