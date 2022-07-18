import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
    selector: 'app-container-form',
    templateUrl: './container-form.component.html',
    styleUrls: ['./container-form.component.css']
})
export class ContainerFormComponent implements OnInit {

    error: boolean = false
    message: string = ''
    containerFormData: any = {transport: 0, location: ''}
    @Output() submitEvent = new EventEmitter<any>()
    @Input() data: any = {}

    constructor() { }

    ngOnInit(): void {
        if (this.data.hasOwnProperty("container"))
            this.containerFormData = {
                transport: this.data.container.transport_id, 
                location: this.data.container.location
            }
    }

    onSubmit(container: any) {
        console.log(container)
        if (container.transport == 0 || container.location == '') {
            this.error = true
            this.message = 'Please enter all the fields'
            return
        } else if (container.location.length > 4) {
            this.error = true
            this.message = 'The location can only be 4 characters long'
            return
        } else if (container.location.search('^[A-z]') == -1) {
            this.error = true
            this.message = 'The first character must be a letter'
            return
        } else if (container.location.search('[0-9]{3}') == -1) {
            this.error = true
            this.message = 'The last 3 characters must be numbers'
            return
        }

        this.submitEvent.emit(this.containerFormData)
    }

}
