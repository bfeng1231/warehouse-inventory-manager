import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContainerPageComponent } from './container-page/container-page.component';
import { SingleContainerComponent } from './single-container/single-container.component'

const routes: Routes = [
    {
        path: '',
        component: ContainerPageComponent
    },
    {
        path: ':id', 
        component: SingleContainerComponent
    }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }