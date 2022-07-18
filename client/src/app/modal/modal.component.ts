import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
    selector: 'app-modal',
    templateUrl: './modal.component.html',
    styleUrls: ['./modal.component.css']
})
export class ModalComponent implements OnInit {

    @Input() data: any = ''
    @Output() deleteEvent = new EventEmitter<number>()
    @Output() closeEvent = new EventEmitter()
    @Output() sumbitEvent = new EventEmitter<any>()

    constructor() { }

    ngOnInit(): void {
    }

    getType() {
        console.log(this.data)
    }

    closeModal(): void {
        this.closeEvent.emit()
    }

    deleteData(id: number): void {
        this.deleteEvent.emit(id)
    }

    addData(formData: any) {
        this.sumbitEvent.emit(formData)
    }
}
