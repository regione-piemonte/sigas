import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { SoggettiComponent } from './component/soggetti/soggetti.component';
import { DettaglioSoggettoComponent } from './component/dettaglio-soggetto/dettaglio-soggetto.component';
import { InserimentoSoggettoComponent } from './component/inserimento-soggetto/inserimento-soggetto.component';
import { OrdinativiIncassoComponent } from './component/ordinativi-incasso/ordinativi-incasso.component'; 
import { AssociaOrdincSoggettoComponent } from './component/associa-ordinc-soggetto/associa-ordinc-soggetto.component';

import { AssociaOrdincVersamentoComponent } from './component/associa-ordinc-versamento/associa-ordinc-versamento.component';
import { StoricoAnagraficaComponent } from './component/storico-anagrafica/storico-anagrafica.component';

const routes: Routes = [
  {
      path: 'home',
      children: [
          { path: '', redirectTo: 'home', pathMatch: 'full' },
          { path: 'storico', component: StoricoAnagraficaComponent, data: { breadcrumb: "Storico Anagrafica" }},
          { path: 'soggetti', component: SoggettiComponent, data: { breadcrumb: "Anagrafica Soggetti" }},          
          {
            path: 'soggetti', children:[
              { path: 'dettaglioSoggetto', component: DettaglioSoggettoComponent, data: { breadcrumb: "Dettaglio Soggetto" }},
              { path: 'inserimentoSoggetto', component: InserimentoSoggettoComponent, data: { breadcrumb: "Inserimento" }}
            ],
            data: { breadcrumb: "Anagrafica Soggetti" }
          },
          { path: 'ordinativiIncasso', component: OrdinativiIncassoComponent, data: { breadcrumb: "Ordinativi Incasso" }},
          { path: 'ordinativiIncasso', children:[
                                                 { path: 'associaSoggetto', component: AssociaOrdincSoggettoComponent, data: { breadcrumb: "Associa Soggetto" }},
                                                 { path: 'associaVersamento', component: AssociaOrdincVersamentoComponent, data: { breadcrumb: "Associa Versamento" }}
                                                 ],
                                                 data: {breadcrumb: "Ordinativi Incasso" }
          }
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
export class AnagraficaSoggettiRoutingModule { }
