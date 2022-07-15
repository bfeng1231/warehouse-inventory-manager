import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContainerPageComponent } from './container-page/container-page.component';
import { ItemListComponent } from './item-list/item-list.component';
import { SingleContainerComponent } from './single-container/single-container.component';
import { ContainerFormComponent } from './forms/container-form/container-form.component';
import { ModalComponent } from './modal/modal.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    ContainerPageComponent,
    ItemListComponent,
    SingleContainerComponent,
    ContainerFormComponent,
    ModalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
