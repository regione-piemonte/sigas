import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharedModule} from '../shared/shared.module';
import { AccreditamentoComponent } from './component/accreditamento/accreditamento.component';
import { AccreditamentoRoutingModule } from './accreditamento-routing.module';
import { DataTablesModule } from 'angular-datatables';
import {NgbDatepickerModule} from '@ng-bootstrap/ng-bootstrap';
import { ElencoAccreditamentoComponent } from './component/elenco-accreditamento/elenco-accreditamento.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    DataTablesModule,
    AccreditamentoRoutingModule,
    NgbDatepickerModule
  ],
  exports: [
    AccreditamentoComponent,
    ElencoAccreditamentoComponent
  ],
  declarations: [AccreditamentoComponent, ElencoAccreditamentoComponent]
})
export class AccreditamentoModule { }
