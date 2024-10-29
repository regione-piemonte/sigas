import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DocumentazioneComponent } from './component/documentazione/documentazione.component';
import { ElencoDocumentazioneComponent } from './component/elenco-documentazione/elenco-documentazione.component';
import { SigasSuccessComponent } from '../shared/component/sigas-success/sigas-success.component';
import { DocumentazioneTemplateLetteraGestContAmministrativoComponent } from "./component/documentazione-template-lettera/documentazione-template-lettera.component";

const routes: Routes = [ 
  {
    path: 'documentazione',
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'documentazioneInArrivo', component: ElencoDocumentazioneComponent, data: { breadcrumb: 'Elenco Gestione Documentazione' }},
          { path: 'documentazioneInArrivo', children:[
                                                      { path: 'dettaglioDocumentazione', component: DocumentazioneComponent, data: { breadcrumb: "Gestione Pratica Documentazione" }},
                                                      { path: 'template-lettera-risposta/:id', component: DocumentazioneTemplateLetteraGestContAmministrativoComponent },
                                                      ],
                                                      data: {breadcrumb: "Gestione Documentazione" }   }
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
export class DocumentazioneRoutingModule { }
