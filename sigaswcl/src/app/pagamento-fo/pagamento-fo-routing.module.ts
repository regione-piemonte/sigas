import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { SelezioneSoggettoDetailComponent } from './component/selezione-soggetto-detail/selezione_soggetto_detail.component';
import { PagamentoFoImporto } from './component/inserimento-importo/inserimento-fo-importo.component';

import { SuccessPagoPaComponent } from './component/success-pago-pa/success-pago-pa';
import { StartPagoPaComponent } from './component/start-pago-pa/start-pago-pa';

import { FailurePagoPaComponent } from './component/failure-pago-pa/failure-pago-pa';
import { SuccessStampaAvvisoComponent } from './component/success-stampa-avviso/success-stampa-avviso';
import { SelezioneSoggetto } from './component/selezione-soggetto/selezione-soggetto.component';
import { CartPaymentComponent } from './component/cart-payments/cart-payments.component';

import { SelezioneRicevutaComponent } from './component/seleziona-ricevuta/seleziona-ricevuta.component';
import { RicevutaDetailComponent } from './component/ricevuta-detail/ricevuta_detail.component';

import { RicercaPagamentoComponent } from './component/ricerca-pagamento/ricerca-pagamento.component';
import { SearchSubjectSelectComponent } from './component/ricerca-selezione-soggetto-detail/ricerca_selezione_soggetto_detail.component';
import { SearchDetailComponent } from './component/ricerca-detail/ricerca_detail.component';

import { RicercaPagamentoBOComponent } from './component/ricerca-pagamento-bo/ricerca-pagamento-bo.component';
import { SearchSubjectSelectBOComponent } from './component/ricerca-selezione-soggetto-detail-bo/ricerca_selezione_soggetto_detail.component';

const routes: Routes = [
  {
      path: 'home',
      children: [
          { path: '', redirectTo: 'home', pathMatch: 'full' },
          { path: 'selezione-soggetto-pagamento', component: SelezioneSoggetto, data: { breadcrumb: "Selezione soggetto" }},
          { path: 'selezione-soggetto-detail', component: SelezioneSoggettoDetailComponent, data: { breadcrumb: "Selezione soggetto dettaglio" }},
          { path: 'pagamento-fo-importo', component: PagamentoFoImporto, data: { breadcrumb: "Importo pagamento " }},
          { path: 'carrello-fo', component: CartPaymentComponent, data: { breadcrumb: "Carrello pagamenti " }},

          { path: 'seleziona-ricevuta', component: SelezioneRicevutaComponent, data: { breadcrumb: "Ricevuta IUV" }},
          { path: 'visualizza-ricevuta', component: RicevutaDetailComponent, data: { breadcrumb: "Ricevuta Telematica" }},

          { path: 'ricerca-pagamento', component: RicercaPagamentoComponent, data: { breadcrumb: "Ricerca pagamento" }},
          { path: 'ricerca-soggetto-pagamento', component: SearchSubjectSelectComponent, data: { breadcrumb: "Ricerca pagamento" }},
          { path: 'ricerca-dettaglio-pagamento', component: SearchDetailComponent, data: { breadcrumb: "Ricerca dettaglio pagamento" }},

          { path: 'ricerca-pagamento-bo', component: RicercaPagamentoBOComponent, data: { breadcrumb: "Ricerca pagamento" }},
          { path: 'ricerca-soggetto-pagamento-bo', component: SearchSubjectSelectBOComponent, data: { breadcrumb: "Ricerca pagamento" }},

          { path: 'start-ppay', component: StartPagoPaComponent, data: { breadcrumb: "Inizio pagamento" }},
          { path: 'ppayCallback', component: SuccessPagoPaComponent, data: { breadcrumb: "Conferma pagamento" }},
          
          { path: 'pmpFailureTransazione', component: FailurePagoPaComponent, data: { breadcrumb: "Transazione failure" } },
          { path: 'successTransazioneStampaAvviso/:id', component: SuccessStampaAvvisoComponent, data: { breadcrumb: "Transazione success" } }
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
export class PagamentoFoRoutingModule { }
