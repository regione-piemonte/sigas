import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AccreditamentoComponent } from './component/accreditamento/accreditamento.component';
import { ElencoAccreditamentoComponent } from './component/elenco-accreditamento/elenco-accreditamento.component';
import { SigasSuccessComponent } from '../shared/component/sigas-success/sigas-success.component';

const routes: Routes = [
  {
    path: 'accreditamento',
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'elencoRichieste', component: ElencoAccreditamentoComponent, data: { breadcrumb: 'Elenco Richieste' } },
      { path: 'gestioneAccreditamento', component: AccreditamentoComponent, data: { breadcrumb: 'Gestione Accreditamento' } },
      { path: 'successInserimento', component: SigasSuccessComponent, data: { breadcrumb: "Accreditamento Success" } }
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
export class AccreditamentoRoutingModule { }
