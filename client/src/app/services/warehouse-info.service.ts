import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class WarehouseInfoService {

    currentSpace: number = 0
    totalSpace: number = 0
    recall: boolean = false

    constructor() { }

    updateTotal() {
        this.recall = true
    }
}
