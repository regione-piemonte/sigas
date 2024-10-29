import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ImportazioneUTFComponent } from './component/importazione-utf/importazione-utf.component';

const routes: Routes = [
  {
    path: 'UTF',
    children: [
      { path: '', redirectTo: 'importazione', pathMatch: 'full' },
      { path: 'importazione', component: ImportazioneUTFComponent, data: { breadcrumb: "Importazione UTF" } }
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
export class ImportazioneUtfRoutingModule { }
