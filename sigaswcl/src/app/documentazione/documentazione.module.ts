import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharedModule} from '../shared/shared.module';
import { DocumentazioneComponent } from './component/documentazione/documentazione.component';
import { DocumentazioneRoutingModule } from './documentazione-routing.module';
import { DataTablesModule } from 'angular-datatables';
import {NgbDatepickerModule} from '@ng-bootstrap/ng-bootstrap';
import { ElencoDocumentazioneComponent } from './component/elenco-documentazione/elenco-documentazione.component';
import { DocumentazioneTemplateLetteraGestContAmministrativoComponent } from "./component/documentazione-template-lettera/documentazione-template-lettera.component";
import { TemplateModule } from "../template/template.module";

@NgModule({
  imports: [
    CommonModule,
    TemplateModule,
    FormsModule,
    SharedModule,
    DataTablesModule,
    DocumentazioneRoutingModule,
    NgbDatepickerModule
  ],
  exports: [
    DocumentazioneComponent,
    ElencoDocumentazioneComponent,
    DocumentazioneTemplateLetteraGestContAmministrativoComponent
  ],
  declarations: [DocumentazioneComponent, ElencoDocumentazioneComponent, DocumentazioneTemplateLetteraGestContAmministrativoComponent]
})
export class DocumentazioneModule { }
