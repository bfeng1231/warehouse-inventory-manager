import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
    selector: 'app-search-bar',
    templateUrl: './search-bar.component.html',
    styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit {

    @Output() searchEvent = new EventEmitter<any>()
    search: any = {type: 'container_id', input: ''}

    constructor() { }

    ngOnInit(): void {
    }

    searchTerm(value: any) {
        this.search.input = value
        console.log(this.search)
        
        this.searchEvent.emit(this.search) 
    }
}
