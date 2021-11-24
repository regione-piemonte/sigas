import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule } from '@angular/forms';
import { SharedModule } from "../../shared/shared.module";
import { RouterModule } from "@angular/router";
import { ModificaAccreditamentoService } from "./service/modifica-accreditamento.service";
import { ElencoPraticheAccreditamentoComponent } from './components/elenco-pratiche-accreditamento/elenco-pratiche-accreditamento.component';
import { ModificaPraticaAccreditamentoComponent } from './components/modifica-pratica-accreditamento/modifica-pratica-accreditamento.component';

import { AccreditamentoModificaRoutingModule } from "./accreditamento-modifica.routing.module";
import { AccreditamentoSharedModule } from "../accreditamento-shared/accreditamento-shared.module";
import { DataTablesModule } from 'angular-datatables';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        SharedModule,
        DataTablesModule,
        AccreditamentoSharedModule,
        AccreditamentoModificaRoutingModule],
    exports: [
        ElencoPraticheAccreditamentoComponent,
        ModificaPraticaAccreditamentoComponent],
    declarations: [
        ElencoPraticheAccreditamentoComponent,
        ModificaPraticaAccreditamentoComponent],
    providers: [ModificaAccreditamentoService]
})
export class AccreditamentoModificaModule { }