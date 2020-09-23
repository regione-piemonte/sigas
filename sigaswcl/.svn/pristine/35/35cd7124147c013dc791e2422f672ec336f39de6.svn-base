import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { SoggettiComponent } from './component/soggetti/soggetti.component';
import { DettaglioSoggettoComponent } from './component/dettaglio-soggetto/dettaglio-soggetto.component';
import { InserimentoSoggettoComponent } from './component/inserimento-soggetto/inserimento-soggetto.component';

const routes: Routes = [
  {
      path: 'home',
      children: [
          { path: '', redirectTo: 'home', pathMatch: 'full' },
          { path: 'soggetti', component: SoggettiComponent, data: { breadcrumb: "Anagrafica Soggetti" }},
          {
            path: 'soggetti', children:[
              { path: 'dettaglioSoggetto', component: DettaglioSoggettoComponent, data: { breadcrumb: "Dettaglio Soggetto" }},
              { path: 'inserimentoSoggetto', component: InserimentoSoggettoComponent, data: { breadcrumb: "Inserimento" }}
            ],
            data: { breadcrumb: "Anagrafica Soggetti" }
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
