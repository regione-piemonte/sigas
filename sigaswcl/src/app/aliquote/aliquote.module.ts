import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharedModule} from '../shared/shared.module';
import { AliquoteComponent } from './component/aliquote/aliquote.component';
import { AliquoteRoutingModule } from './aliquote-routing.module';
import { DataTablesModule } from 'angular-datatables';
import {NgbDatepickerModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    DataTablesModule,
    AliquoteRoutingModule,
    NgbDatepickerModule
  ],
  exports: [
    AliquoteComponent
  ],
  declarations: [AliquoteComponent]
})
export class AliquoteModule { }
