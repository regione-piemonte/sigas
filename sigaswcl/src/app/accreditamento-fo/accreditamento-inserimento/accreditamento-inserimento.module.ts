import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule } from '@angular/forms';
import { SharedModule } from "../../shared/shared.module";
import { RouterModule } from "@angular/router";
import { InserimentoDichiaranteComponent } from "./components/inserimento-dichiarante/inserimento-dichiarante.component";
import { InserimentoLegaleRappresentante } from "./components/inserimento-legale-rappresentante/inserimento-legale-rappresentante.component";
import { InserimentoOperatoreServizi } from "./components/inserimento-operatore-servizi/inserimento-operatore-servizi.component";
import { InserisciAccreditamentoService } from "./service/inserisci-accreditamento.service";
import { AccreditamentoInserimentoRoutingModule } from "./accreditamento-inserimento.routing.module";
import { AccreditamentoSharedModule } from  "../accreditamento-shared/accreditamento-shared.module";
import { ElencoPraticheComponent } from './components/elenco-pratiche/elenco-pratiche.component';
import { DataTablesModule } from "angular-datatables";
import { VisualizzaPraticaAccreditamentoComponent } from './components/visualizza-pratica-accreditamento/visualizza-pratica-accreditamento.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        SharedModule,
        AccreditamentoSharedModule,
        DataTablesModule,
        AccreditamentoInserimentoRoutingModule
        ],
    exports: [
        InserimentoDichiaranteComponent,
        InserimentoLegaleRappresentante,
        InserimentoOperatoreServizi,ElencoPraticheComponent,VisualizzaPraticaAccreditamentoComponent],
    declarations: [
        InserimentoDichiaranteComponent,
        InserimentoLegaleRappresentante,
        InserimentoOperatoreServizi,ElencoPraticheComponent,VisualizzaPraticaAccreditamentoComponent],
    providers: [InserisciAccreditamentoService]
})
export class AccreditamentoInserimentoModule { }