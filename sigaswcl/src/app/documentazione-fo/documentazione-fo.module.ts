import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharedModule} from '../shared/shared.module';
import { DocumentazioneComponent } from './component/documentazione/documentazione.component';
import { DocumentazioneFoRoutingModule } from './documentazione-fo-routing.module';
import { DataTablesModule } from 'angular-datatables';
import {NgbDatepickerModule} from '@ng-bootstrap/ng-bootstrap';
import { ElencoDocumentazioneComponent } from './component/elenco-documentazione/elenco-documentazione.component';
import { FileSelectDirective } from 'ng2-file-upload';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    DataTablesModule,
    DocumentazioneFoRoutingModule,
    NgbDatepickerModule
  ],
  exports: [
    DocumentazioneComponent,
    ElencoDocumentazioneComponent
  ],
  declarations: [DocumentazioneComponent, ElencoDocumentazioneComponent, FileSelectDirective]
})
export class DocumentazioneFoModule { }
