import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DocumentazioneComponent } from './component/documentazione/documentazione.component';
import { ElencoDocumentazioneComponent } from './component/elenco-documentazione/elenco-documentazione.component';
import { SigasSuccessComponent } from '../shared/component/sigas-success/sigas-success.component';

const routes: Routes = [ 
  {
    path: 'documentazione',
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'consultaDocumentazione', component: ElencoDocumentazioneComponent, data: { breadcrumb: 'Elenco Documentazione' } },
      { path: 'inoltraDocumentazione', component: DocumentazioneComponent, data: { breadcrumb: 'Invia Documentazione' } },
      { path: 'successInserimento', component: SigasSuccessComponent, data: { breadcrumb: "Documentazione Success" } }
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
export class DocumentazioneFoRoutingModule { }
