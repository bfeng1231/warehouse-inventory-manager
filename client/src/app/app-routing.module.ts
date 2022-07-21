import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContainerPageComponent } from './container-page/container-page.component';
import { SingleContainerComponent } from './single-container/single-container.component'
import { WarehousePageComponent } from './warehouse-page/warehouse-page.component';

const routes: Routes = [
    {
        path: '', 
        component: WarehousePageComponent
    },
    {
        path: ':warehouse-id',
        component: ContainerPageComponent
    },
    {
        path: ':warehouse-id/:container-id', 
        component: SingleContainerComponent
    }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }