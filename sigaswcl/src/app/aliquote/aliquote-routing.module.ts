import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AliquoteComponent } from './component/aliquote/aliquote.component';

const routes: Routes = [
  {
    path: 'impostazioni',
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'aliquote', component: AliquoteComponent, data: { breadcrumb: 'Aliquote' } }
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
export class AliquoteRoutingModule { }
