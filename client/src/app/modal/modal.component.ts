import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
    selector: 'app-modal',
    templateUrl: './modal.component.html',
    styleUrls: ['./modal.component.css']
})
export class ModalComponent implements OnInit {

    @Input() data: any = ''
    @Output() deleteContainerEvent = new EventEmitter<number>()
    @Output() deleteItemEvent = new EventEmitter<number>()
    @Output() closeEvent = new EventEmitter()
    @Output() sumbitContainerEvent = new EventEmitter<any>()
    @Output() sumbitItemEvent = new EventEmitter<any>()

    constructor() { }

    ngOnInit(): void {
    }

    getType() {
        console.log(this.data)
    }

    closeModal(): void {
        this.closeEvent.emit()  
    }

    deleteData(data: any): void {
        switch(this.data.type) {
            case 'delete':
                this.deleteContainerEvent.emit(data.id)
                return
            case 'deleteItems':
                this.deleteItemEvent.emit(data.itemChecklist)
                return
            default:
                return
        }
    }

    addData(formData: any): void {
        switch(this.data.type) {
            case 'form':
                this.sumbitContainerEvent.emit(formData)
                return
            case 'itemForm':
                this.sumbitItemEvent.emit(formData)
                return
            default:
                return
        }
    }
}
