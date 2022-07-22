import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class WarehouseInfoService {

    // Service used for mantaining the current amount of space of consumed by the containers

    currentSpace: number = 0
    totalSpace: number = 0
    recall: boolean

    constructor() { 
        this.recall = false
    }

    updateTotal() {
        this.recall = true
    }
}
