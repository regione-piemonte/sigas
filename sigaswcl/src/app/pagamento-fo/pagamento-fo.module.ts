import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from "@angular/forms";
import { SharedModule } from '../shared/shared.module';
import { DataTablesModule } from "angular-datatables";
import { SelezioneSoggettoDetailComponent } from './component/selezione-soggetto-detail/selezione_soggetto_detail.component';
import { PagamentoFoImporto } from './component/inserimento-importo/inserimento-fo-importo.component';

import { SuccessPagoPaComponent } from './component/success-pago-pa/success-pago-pa';
import { StartPagoPaComponent } from './component/start-pago-pa/start-pago-pa';

import { FailurePagoPaComponent } from './component/failure-pago-pa/failure-pago-pa';
import { CartPaymentComponent } from './component/cart-payments/cart-payments.component';
import { SuccessStampaAvvisoComponent } from './component/success-stampa-avviso/success-stampa-avviso';
import { SelezioneSoggetto } from './component/selezione-soggetto/selezione-soggetto.component';

import { SelezioneRicevutaComponent } from './component/seleziona-ricevuta/seleziona-ricevuta.component';
import { RicevutaDetailComponent } from './component/ricevuta-detail/ricevuta_detail.component';

import { RicercaPagamentoComponent } from './component/ricerca-pagamento/ricerca-pagamento.component';
import { SearchSubjectSelectComponent } from './component/ricerca-selezione-soggetto-detail/ricerca_selezione_soggetto_detail.component';
import { SearchDetailComponent } from './component/ricerca-detail/ricerca_detail.component';

import { RicercaPagamentoBOComponent } from './component/ricerca-pagamento-bo/ricerca-pagamento-bo.component';
import { SearchSubjectSelectBOComponent } from './component/ricerca-selezione-soggetto-detail-bo/ricerca_selezione_soggetto_detail.component';

import { PagamentoFoRoutingModule } from './pagamento-fo-routing.module';

import { NgbDatepickerModule } from '@ng-bootstrap/ng-bootstrap';

import { InserimentoPagamentoService } from "./service/inserisci-pagamento-service";


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    DataTablesModule, 
    SharedModule,
    PagamentoFoRoutingModule,
    NgbDatepickerModule
  ],
  exports: [
    PagamentoFoImporto
  ],
  providers: [
    InserimentoPagamentoService
  ],
  declarations: [
    SearchSubjectSelectBOComponent, 
    RicercaPagamentoBOComponent, 
    SearchSubjectSelectComponent, 
    SearchDetailComponent, 
    RicercaPagamentoComponent, 
    RicevutaDetailComponent, 
    SelezioneRicevutaComponent, 
    CartPaymentComponent, 
    SelezioneSoggettoDetailComponent, 
    PagamentoFoImporto, 
    SuccessPagoPaComponent, 
    StartPagoPaComponent,
    FailurePagoPaComponent, 
    SuccessStampaAvvisoComponent, 
    SelezioneSoggetto
  ]
})
export class PagamentoFoModule { }
