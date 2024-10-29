import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from "@angular/forms";
import { SharedModule } from '../shared/shared.module';
import { DataTablesModule } from "angular-datatables";
import { TassiComponent } from './component/tassi/tassi.component';
import { TassiRoutingModule } from './tassi-routing.module';
import {NgbDatepickerModule} from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    DataTablesModule, 
    SharedModule,
    TassiRoutingModule,
    NgbDatepickerModule
  ],
  exports: [
    TassiComponent
  ],
  declarations: [TassiComponent]
})
export class TassiModule { }
