import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import {TassiComponent } from './component/tassi/tassi.component';

const routes: Routes = [
  {
      path: 'impostazioni',
      children: [
          { path: '', redirectTo: 'home', pathMatch: 'full' },
          { path: 'tassi', component: TassiComponent, data: { breadcrumb: "Anagrafica Tassi di Interesse" } }
      ]
      
  }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class TassiRoutingModule { }
